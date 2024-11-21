/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import {Liferay} from '~/common/services/liferay';

import {JiraEnum} from '../utils/constants/jiraEnum';

export interface IJiraFields {
	[JiraEnum.AFFECTED_VERSIONS]?: string[];
	[JiraEnum.CATEGORY]?: string;
	[JiraEnum.CLASSIFICATION]?: string;
	[JiraEnum.DESCRIPTION]?: string;
	[JiraEnum.FIX_VERSIONS]?: string[];
	[JiraEnum.PUBLISHED_DATE]?: string;
	[JiraEnum.SEVERITY]?: string;
	[JiraEnum.SUMMARY]?: string;
}
export interface IJiraIssue {
	[JiraEnum.FIELDS]?: IJiraFields;
	[JiraEnum.KEY]?: string;
}

const useJiraIssue = (issueKey: string) => {
	const [jiraIssue, setJiraIssue] = useState<IJiraIssue | undefined>(
		undefined
	);

	const fetchJiraIssue = useCallback(async (issueKey: string) => {
		try {
			const response: IJiraIssue =
				await Liferay.OAuth2Client.FromUserAgentApplication(
					'liferay-customer-etc-spring-boot-oaua'
				)
					.fetch(`/jira/securities/issues/${issueKey}`)
					.then((response) => response.json());

			setJiraIssue(response);
		}
		catch (error) {
			console.error('Error fetching Jira data:', error);
		}
	}, []);

	useEffect(() => {
		fetchJiraIssue(issueKey);
	}, [fetchJiraIssue, issueKey]);

	return {fetchJiraIssue, jiraIssue};
};

export default useJiraIssue;
