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

import SearchBuilder from '../../lib/SearchBuilder';
import { getSearchParams } from '../../lib/SearchParams';
import {
    APIResponse,
    JiraAuthorizePayload,
    TestrayIssue,
    TestrayJiraOAuth,
} from '../../lib/Types';
import LiferayAuth from './LiferayAuth';

const { JIRA_AUTH_STATE_PREFIX } = Bun.env;

class Testray extends LiferayAuth {
    public async getOAuthJira(userId: string) {
        const searchParams = getSearchParams({
            filter: SearchBuilder.eq('r_testrayJiraOAuth_userId', userId),
        });

        const response: APIResponse<TestrayJiraOAuth> = await this.fetcher(
            `/o/c/testrayjiraoauths?${searchParams}`
        );

        if (response.totalCount) {
            return response.items[0];
        }

        throw new Error(`No Jira accessToken for ${userId}`);
    }

    public async setTestrayOAuthJiraCode(
        { access_token, expires_in, refresh_token }: JiraAuthorizePayload,
        state: string
    ) {
        const [, userId] = state.split(JIRA_AUTH_STATE_PREFIX as string);

        try {
            const response = await this.getOAuthJira(userId);

            await this.fetcher(`/o/c/testrayjiraoauths/${response.id}`, {
                body: JSON.stringify({
                    accessToken: access_token,
                    expiresIn: expires_in,
                    r_testrayJiraOAuth_userId: userId,
                }),
                method: 'PUT',
            });
        } catch {
            await this.fetcher('/o/c/testrayjiraoauths', {
                body: JSON.stringify({
                    accessToken: access_token,
                    expiresIn: expires_in,
                    r_testrayJiraOAuth_userId: userId,
                    refreshToken: refresh_token,
                }),
                method: 'POST',
            });
        }
    }

    public async getIssues(issues: string[]) {
        const searchParams = getSearchParams({
            fields: 'id,name,issueToCaseResultsIssues.r_caseResultToCaseResultsIssues_c_caseResult.r_caseToCaseResult_c_case.name,issueToCaseResultsIssues.r_caseResultToCaseResultsIssues_c_caseResult.r_caseToCaseResult_c_case.priority',
            filter: SearchBuilder.in('name', issues),
            nestedFields:
                'issueToCaseResultsIssues,r_caseResultToCaseResultsIssues_c_caseResult',
            nestedFieldsDepth: 3,
            pageSize: 100,
        });

        const response: APIResponse<TestrayIssue> = await this.fetcher(
            decodeURIComponent(`/o/c/issues?${searchParams}`)
        );

        return response.items;
    }
}

export default Testray;
