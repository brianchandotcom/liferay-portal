/* eslint-disable quote-props */

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Cache from '../../lib/Cache';
import SearchBuilder from '../../lib/SearchBuilder';
import {getSearchParams} from '../../lib/SearchParams';
import {
	APIResponse,
	HttpContext,
	JiraAuthorizePayload,
	TestrayIssue,
	TestrayJiraOAuth,
} from '../../lib/Types';
import {JIRA_AUTH_STATE_PREFIX, LIFERAY_AUTH_TOKEN} from '../../utils/env';
import LiferayAuth from './LiferayAuth';

const cacheInstance = Cache.getInstance();

class Testray extends LiferayAuth {
	public async getOAuthJira(
		userId: string,
		liferayToken = LIFERAY_AUTH_TOKEN
	) {
		const searchParams = getSearchParams({
			filter: SearchBuilder.eq('r_testrayJiraOAuth_userId', userId),
		});

		const response: APIResponse<TestrayJiraOAuth> = await this.fetcher(
			`/o/c/testrayjiraoauthses?${searchParams}`,
			{
				headers: {
					'Authorization': liferayToken as string,
					'Content-Type': 'application/json',
				},
			}
		);

		if (response.totalCount) {
			return response.items[0];
		}

		throw new Error(`No Jira accessToken for ${userId}`);
	}

	public async createRequirement(requirement: any, httpContext: HttpContext) {
		return this.fetcher('/o/c/requirements', {
			body: JSON.stringify(requirement),
			headers: {
				'Authorization': httpContext.authorization as string,
				'Content-Type': 'application/json',
			},
			method: 'POST',
		});
	}

	public async setTestrayOAuthJiraCode(
		{access_token, expires_in, refresh_token}: JiraAuthorizePayload,
		state: string
	) {
		const [, userId] = state.split(JIRA_AUTH_STATE_PREFIX as string);

		const liferayToken = cacheInstance.get(`preauthorize-${userId}`);

		try {
			const response = await this.getOAuthJira(userId, liferayToken);

			await this.fetcher(`/o/c/testrayjiraoauthses/${response.id}`, {
				body: JSON.stringify({
					accessToken: access_token,
					expiresIn: expires_in,
					r_testrayJiraOAuth_userId: userId,
				}),
				headers: {
					'Authorization': liferayToken as string,
					'Content-Type': 'application/json',
				},
				method: 'PUT',
			});

			await this.fetcher(
				`/o/headless-admin-user/v1.0/user-accounts/${userId}`,
				{
					body: JSON.stringify({
						jiraAuthorization: true,
					}),
					headers: {
						'Authorization': liferayToken as string,
						'Content-Type': 'application/json',
					},
					method: 'PATCH',
				}
			);
		}
		catch {
			await this.fetcher('/o/c/testrayjiraoauthses', {
				body: JSON.stringify({
					accessToken: access_token,
					expiresIn: expires_in,
					r_testrayJiraOAuth_userId: userId,
					refreshToken: refresh_token,
				}),
				headers: {
					'Authorization': liferayToken as string,
					'Content-Type': 'application/json',
				},
				method: 'POST',
			});
		}
	}

	public async getIssues(issues: string[], httpContext: HttpContext) {
		const searchParams = getSearchParams({
			fields: 'id,name,issueToCaseResultsIssues.r_caseResultToCaseResultsIssues_c_caseResult.r_caseToCaseResult_c_case.name,issueToCaseResultsIssues.r_caseResultToCaseResultsIssues_c_caseResult.r_caseToCaseResult_c_case.priority',
			filter: SearchBuilder.in('name', issues),
			nestedFields:
				'issueToCaseResultsIssues,r_caseResultToCaseResultsIssues_c_caseResult',
			nestedFieldsDepth: 3,
			pageSize: 100,
		});

		const response: APIResponse<TestrayIssue> = await this.fetcher(
			decodeURIComponent(`/o/c/issues?${searchParams}`),
			{
				headers: {
					'Authorization':
						httpContext.authorization ||
						(LIFERAY_AUTH_TOKEN as string),
					'Content-Type': 'application/json',
				},
			}
		);

		return response.items;
	}

	public async resyncWithJira(
		issue: any,
		{authorization, requirementId}: HttpContext & {requirementId: string}
	) {
		const {
			fields: {description, labels, summary},
		} = issue;

		await this.fetcher(`/o/c/requirements/${requirementId}`, {
			body: JSON.stringify({
				components: labels.join(', '),
				description,
				summary,
			}),
			headers: {
				'Authorization': authorization as string,
				'Content-Type': 'application/json',
			},
			method: 'PATCH',
		});
	}
}

export default Testray;
