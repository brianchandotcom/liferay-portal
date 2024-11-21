/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {JiraEnum} from './jiraEnum';

export interface ISortOption {
	[JiraEnum.SORT_BY]: string;
	[JiraEnum.SORT_ORDER]: 'asc' | 'desc';
	key: string;
}

export interface IProps {
	sorts: ISortOption[];
}

export const SORT_OPTIONS: IProps = {
	sorts: [
		{
			[JiraEnum.SORT_BY]: JiraEnum.PUBLISHED_DATE,
			[JiraEnum.SORT_ORDER]: 'desc',
			key: 'newest',
		},
		{
			[JiraEnum.SORT_BY]: JiraEnum.PUBLISHED_DATE,
			[JiraEnum.SORT_ORDER]: 'asc',
			key: 'oldest',
		},
	],
};
