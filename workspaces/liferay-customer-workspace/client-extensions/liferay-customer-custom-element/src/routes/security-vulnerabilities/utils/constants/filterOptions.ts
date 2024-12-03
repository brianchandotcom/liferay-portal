/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {JiraEnum} from './jiraEnum';

export interface IProps {
	[JiraEnum.AFFECTED_VERSIONS]?: string[];
	[JiraEnum.CATEGORY]?: string[];
	[JiraEnum.CLASSIFICATION]?: string[];
	[JiraEnum.FIX_VERSIONS]?: string[];
	[JiraEnum.SEVERITY]?: string[];
}

export const FILTER_MAP: {[key: string]: string} = {
	affectedVersions: 'filterAffectedVersions',
	category: 'filterCategories',
	fixVersions: 'filterFixVersions',
	issueClassification: 'clea',
	severity: 'filterSeverities',
};

export const FILTER_OPTIONS: IProps = {
	[JiraEnum.AFFECTED_VERSIONS]: ['2024.Q4', '2024.Q3', '2024.Q2', '2024.Q1'],
	[JiraEnum.CATEGORY]: ['Paas', 'Saas', 'Self-Hosted', 'Docker'],
	[JiraEnum.CLASSIFICATION]: [
		'Confirmed Vulnerability',
		'Ignored',
		'False Positive',
		'Advisory',
		'Threat Information',
	],
	[JiraEnum.FIX_VERSIONS]: ['2024.Q4', '2024.Q3', '2024.Q2', '2024.Q1'],
	[JiraEnum.SEVERITY]: ['Critical', 'High', 'Medium', 'Low', 'None'],
};
