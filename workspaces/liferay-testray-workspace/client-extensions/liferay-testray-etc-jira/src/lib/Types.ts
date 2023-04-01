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
export interface APIResponse<T = any> {
    items: T[];
    totalCount: number;
}

export interface JiraAuthorizeCallback {
    code: string;
    expiresIn?: number;
    state: string;
}

export interface JiraAuthorizePayload {
    access_token: string;
    expires_in: number;
    refresh_token: string;
    scope: string;
}

export type JiraAccessibleResponse = {
    id: string;
    name: string;
    scopes: string[];
    url: string;
}[];

export interface TestrayJiraOAuth {
    accessToken: string;
    expiresIn: number;
    id: number;
    r_testrayJiraOAuth_userId: number;
    refreshToken: string;
}

export interface TestrayIssue {
    id: string;
    issueToCaseResultsIssues: {
        r_caseResultToCaseResultsIssues_c_caseResult: {
            r_caseToCaseResult_c_case: {
                name: string;
                priority: number;
            };
        };
    }[];
    name: string;
}
