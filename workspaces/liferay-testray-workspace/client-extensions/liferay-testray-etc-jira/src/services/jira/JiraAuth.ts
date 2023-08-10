/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Cache from '../../lib/Cache';
import {baseFetcher} from '../../lib/Fetch';
import logger from '../../lib/Logger';
import {
	HttpContext,
	JiraAccessibleResponse,
	JiraAuthorizeCallback,
	JiraAuthorizePayload,
} from '../../lib/Types';
import {
	JIRA_APP_NAME,
	JIRA_AUTH_BASE_URL,
	JIRA_AUTH_CLIENT_ID,
	JIRA_AUTH_CLIENT_SECRET,
	JIRA_AUTH_GRANT_TYPE,
	JIRA_AUTH_GRANT_TYPE_REFRESH_TOKEN,
	JIRA_AUTH_REDIRECT_URI,
	JIRA_AUTH_SCOPES,
	JIRA_AUTH_STATE_PREFIX,
} from '../../utils/env';
import Testray from '../liferay/Testray';

const cacheInstance = Cache.getInstance();

export type RequestSet = {
	headers: Record<string, string>;
	redirect?: string;
	status?: number;
};

class JiraAuth {
	private AUTHORIZE_PARAMS = {
		audience: 'api.atlassian.com',
		client_id: JIRA_AUTH_CLIENT_ID,
		prompt: 'consent',
		redirect_uri: JIRA_AUTH_REDIRECT_URI,
		response_type: 'code',
		scope: JIRA_AUTH_SCOPES,
		state: JIRA_AUTH_STATE_PREFIX,
	};

	private fetcher = baseFetcher(`${JIRA_AUTH_BASE_URL}/oauth/token`, {
		headers: {
			'Content-Type': 'application/json',
		},
	});

	private testray = new Testray();

	public authorize(userId: string) {
		const searchParams = new URLSearchParams();
		const authorizeParamsCopy = {
			...this.AUTHORIZE_PARAMS,
			state: this.AUTHORIZE_PARAMS.state + userId,
		};

		for (const param in authorizeParamsCopy) {
			searchParams.set(param, (authorizeParamsCopy as any)[param]);
		}

		return `${JIRA_AUTH_BASE_URL}/authorize?${decodeURIComponent(
			searchParams.toString()
		)}`;
	}

	public async exchangeAuthorization(state: string, body: Object) {
		const data: JiraAuthorizePayload = await this.fetcher('', {
			body: JSON.stringify({
				client_id: JIRA_AUTH_CLIENT_ID,
				client_secret: JIRA_AUTH_CLIENT_SECRET,
				...body,
			}),
			method: 'POST',
		});

		data.expires_in = Date.now() + data.expires_in * 1000;

		cacheInstance.set(state, data);

		return data;
	}

	public async exchangeAuthorizationCode({
		code,
		state,
	}: JiraAuthorizeCallback) {
		return this.exchangeAuthorization(state, {
			code,
			grant_type: JIRA_AUTH_GRANT_TYPE,
			redirect_uri: JIRA_AUTH_REDIRECT_URI,
		});
	}

	public async exchangeAuthorizeRefreshToken(
		state: string,
		refresh_token: string
	) {
		return this.exchangeAuthorization(state, {
			grant_type: JIRA_AUTH_GRANT_TYPE_REFRESH_TOKEN,
			refresh_token,
		});
	}

	public async getCloudId(token: string) {
		const data: JiraAccessibleResponse = await this.fetcher(
			'/accessible-resources',
			{
				headers: {
					Authorization: `Bearer ${token}`,
				},
			}
		);

		const assessibleResource = data.find(
			({name}) => name === JIRA_APP_NAME
		);

		if (!assessibleResource) {
			const errorMessage = `Jira Cloud: ${JIRA_APP_NAME} not found`;

			logger.error(errorMessage);

			throw new Error(errorMessage);
		}

		cacheInstance.set(Cache.KEYS.JIRA_APP_ID_KEY, assessibleResource.id);

		return assessibleResource.id;
	}

	public async getTokenAndCloudId({authorization, userId}: HttpContext) {
		const state = this.AUTHORIZE_PARAMS.state + userId;

		let cachedCloudID = cacheInstance.get<string>(
			Cache.KEYS.JIRA_APP_ID_KEY
		);
		let cachedUserState = cacheInstance.get<JiraAuthorizePayload>(state);

		if (!cachedUserState) {
			const testrayOAuthJira = await this.testray.getOAuthJira(
				userId,
				authorization as string
			);

			cachedUserState = {
				access_token: testrayOAuthJira.accessToken,
				expires_in: testrayOAuthJira.expiresIn,
				refresh_token: testrayOAuthJira.refreshToken,
				scope: '',
			};

			cacheInstance.set(state, cachedUserState);
		}

		const isTokenExpired = cachedUserState.expires_in < Date.now();

		if (isTokenExpired) {
			logger.warning(
				`The token ${state} is expired, generating a new one`
			);

			cachedUserState = await this.exchangeAuthorizeRefreshToken(
				state,
				cachedUserState.refresh_token
			);

			await this.testray.setTestrayOAuthJiraCode(cachedUserState, state);

			cacheInstance.set(state, cachedUserState);
		}

		if (!cachedCloudID) {
			cachedCloudID = await this.getCloudId(cachedUserState.access_token);
		}

		return {
			cloudId: cachedCloudID,
			token: cachedUserState.access_token,
		};
	}
}

export default JiraAuth;
