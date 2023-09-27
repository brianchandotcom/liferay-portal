/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getHttpContext} from '../utils';

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

export type HttpContext = ReturnType<typeof getHttpContext>;
