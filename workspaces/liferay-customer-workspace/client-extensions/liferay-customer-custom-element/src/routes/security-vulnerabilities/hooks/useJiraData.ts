/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import {Liferay} from '~/common/services/liferay';

import {ITicket} from '../interfaces/ITicket';

export enum JiraDataEnum {
	AFFECTED_VERSIONS = 'affectsVersion',
	CATEGORY = 'category',
	CLASSIFICATION = 'issueClassification',
	DESCRIPTION = 'customerPortalDescription',
	FIELDS = 'fields',
	FIX_VERSIONS = 'fixVersions',
	ISSUES = 'issues',
	PUBLISHED_DATE = 'customerPublishingDate',
	SEVERITY = 'severity',
	SUMMARY = 'customerPortalSummary',
}

interface IJiraResponse {
	issues: [
		{
			fields: {
				[JiraDataEnum.AFFECTED_VERSIONS]: {name: string}[];
				[JiraDataEnum.CATEGORY]: string;
				[JiraDataEnum.CLASSIFICATION]: string;
				[JiraDataEnum.DESCRIPTION]: string;
				[JiraDataEnum.FIX_VERSIONS]: {name: string}[];
				[JiraDataEnum.PUBLISHED_DATE]: string;
				[JiraDataEnum.SEVERITY]: string;
				[JiraDataEnum.SUMMARY]: string;
			};
			key: string;
		},
	];
}

const useJiraData = () => {
	const [jiraData, setJiraData] = useState<ITicket[] | undefined>(undefined);

	const getJiraData = useCallback(async () => {
		try {
			const response =
				await Liferay.OAuth2Client.FromUserAgentApplication(
					'liferay-customer-etc-spring-boot-oaua'
				)
					.fetch('/jira/search/customer')
					.then((response) => response.json());

			const formatedData: ITicket[] = (
				response as IJiraResponse
			).issues.map((issue) => ({
				affectedVersions: issue.fields[
					JiraDataEnum.AFFECTED_VERSIONS
				]?.map((affectedVersion) => affectedVersion.name),
				category: issue.fields[JiraDataEnum.CATEGORY],
				classification: issue.fields[JiraDataEnum.CLASSIFICATION],
				description: issue.fields[JiraDataEnum.DESCRIPTION],
				publishedDate: issue.fields[JiraDataEnum.PUBLISHED_DATE],
				severity: issue.fields[JiraDataEnum.SEVERITY],
				summary: issue.fields[JiraDataEnum.SUMMARY],
			}));

			setJiraData(formatedData);
		}
		catch (error) {
			console.error('Error fetching Jira data:', error);
		}
	}, []);

	useEffect(() => {
		getJiraData();
	}, [getJiraData]);

	return {jiraData};
};

export default useJiraData;
