/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface IProps {
	affectedVersions: string[];
	categories: string[];
	classifications: string[];
	fixVersions: string[];
	severities: string[];
	sorts: string[];
}

export const FILTER_OPTIONS: IProps = {
	affectedVersions: ['2024.Q4', '2024.Q3', '2024.Q2', '2024.Q1'],
	categories: ['Paas', 'Saas', 'Self-Hosted', 'Docker'],
	classifications: [
		'Confirmed Vulnerability',
		'Ignored',
		'False Positive',
		'Advisory',
		'Threat Information',
	],
	fixVersions: ['2024.Q4', '2024.Q3', '2024.Q2', '2024.Q1'],
	severities: ['Critical', 'High', 'Medium', 'Low', 'None'],
	sorts: ['Newest', 'Oldest'],
};
