/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import Cache from '../../lib/Cache';
import logger from '../../lib/Logger';
import JiraAuth from './JiraAuth';

const {
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
} = Bun.env;

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
    cacheInstance,
});

class Jira extends JiraAuth {
	public async getIssue(ticket: string, userId: string): Promise<any> {
		const { cloudId, token } = await this.getTokenAndCloudId(userId);

		const response = await fetch(
			`${JIRA_API_BASE_URL}/ex/jira/${cloudId}/rest/api/latest/issue/${ticket}`,
			{
				headers: {
					Authorization: `Bearer ${token}`,
					"Content-Type": "application/json",
				},
			},
		);

		if (!response.ok) {
			const error = new FetcherError(
				"An error occurred while fetching the data.",
			);

			error.info = await response.json();
			error.status = response.status;

			throw error;
		}

		return response.json();
	}

	public async getIssues(issues: string[], userId: string): Promise<any> {
		const jiraIssues: {
			[key: string]: any;
		} = {};

		const _issues = await Promise.allSettled(
			issues.map((issue) => this.getIssue(issue, userId)),
		);

		_issues.forEach((issue, index) => {
			const issueKey = issues[index];

			const _issue = (issue as any)?.value;

			jiraIssues[issueKey] =
				issue.status === "rejected" || issue.value?.errorMessages
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

    public async updateIssue(ticket: string, body: unknown, userId: string) {
        const { cloudId, token } = await this.getTokenAndCloudId(userId);

        const response = await fetch(
            `${JIRA_API_BASE_URL}/ex/jira/${cloudId}/rest/api/latest/issue/${ticket}`,
            {
                body: JSON.stringify(body),
                headers: {
                    "Authorization": `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                method: 'PUT',
            }
        );

        return response.json();
    }
}

export default Jira;
