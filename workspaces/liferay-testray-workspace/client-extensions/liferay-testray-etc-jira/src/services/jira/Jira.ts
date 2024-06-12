/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from 'dotenv';

import Cache from '../../lib/Cache';
import logger from '../../lib/Logger';
import {HttpContext} from '../../lib/Types';
import {
	JIRA_API_BASE_URL,
	JIRA_APP_NAME,
	JIRA_AUTH_BASE_URL,
	JIRA_AUTH_CLIENT_ID,
	JIRA_AUTH_CLIENT_SECRET,
	JIRA_AUTH_GRANT_TYPE,
	JIRA_AUTH_GRANT_TYPE_REFRESH_TOKEN,
	JIRA_AUTH_REDIRECT_URI,
	JIRA_AUTH_SCOPES,
	JIRA_AUTH_STATE_PREFIX,
	LIFERAY_TESTRAY_REDIRECT_AUTHORIZATION,
} from '../../utils/env';
import JiraAuth from './JiraAuth';

config();

const cacheInstance = Cache.getInstance();

logger.debug({
	JIRA_API_BASE_URL,
	JIRA_APP_NAME,
	JIRA_AUTH_BASE_URL,
	JIRA_AUTH_CLIENT_ID,
	JIRA_AUTH_CLIENT_SECRET,
	JIRA_AUTH_GRANT_TYPE,
	JIRA_AUTH_GRANT_TYPE_REFRESH_TOKEN,
	JIRA_AUTH_REDIRECT_URI,
	JIRA_AUTH_SCOPES,
	JIRA_AUTH_STATE_PREFIX,
	LIFERAY_TESTRAY_REDIRECT_AUTHORIZATION,
	cacheInstance,
});

class FetcherError extends Error {
	public info: any;
	public status?: number;

	constructor(message: string) {
		super(message);
	}
}

class Jira extends JiraAuth {
	public async getIssue(
		ticket: string,
		httpContext: HttpContext
	): Promise<any> {
		const {cloudId, token} = await this.getTokenAndCloudId(httpContext);

		const response = await fetch(
			`${JIRA_API_BASE_URL}/ex/jira/${cloudId}/rest/api/latest/issue/${ticket}`,
			{
				headers: {

					// eslint-disable-next-line quote-props
					'Authorization': `Bearer ${token}`,
					'Content-Type': 'application/json',
				},
			}
		);

		if (!response.ok) {
			const error = new FetcherError(
				'An error occurred while fetching the data.'
			);

			error.info = await response.json();
			error.status = response.status;

			throw error;
		}

		return response.json();
	}

	public async getIssues(
		issues: string[],
		httpContext: HttpContext
	): Promise<any> {
		const jiraIssues: {
			[key: string]: any;
		} = {};

		const _issues = await Promise.allSettled(
			issues.map((issue) => this.getIssue(issue, httpContext))
		);

		_issues.forEach((issue, index) => {
			const issueKey = issues[index];

			const _issue = (issue as any)?.value;

			jiraIssues[issueKey] =
				issue.status === 'rejected' || issue.value?.errorMessages
					? null
					: {
							description: _issue.fields.description,
							jiraComponents: _issue.fields.labels,
							key: _issue.key,
							summary: _issue.fields.summary,
						};
		});

		return jiraIssues;
	}

	public async updateIssue(
		ticket: string,
		body: unknown,
		httpContext: HttpContext
	) {
		const {cloudId, token} = await this.getTokenAndCloudId(httpContext);

		await fetch(
			`${JIRA_API_BASE_URL}/ex/jira/${cloudId}/rest/api/latest/issue/${ticket}`,
			{
				body: JSON.stringify(body),
				headers: {

					// eslint-disable-next-line quote-props
					'Authorization': `Bearer ${token}`,
					'Content-Type': 'application/json',
				},
				method: 'PUT',
			}
		);
	}
}

export default Jira;
