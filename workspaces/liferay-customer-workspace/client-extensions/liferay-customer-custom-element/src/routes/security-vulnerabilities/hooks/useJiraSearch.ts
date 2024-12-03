/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import {useSearchParams} from 'react-router-dom';
import {Liferay} from '~/common/services/liferay';

import {IProps as IFilterOptions} from '../utils/constants/filterOptions';
import {JiraEnum} from '../utils/constants/jiraEnum';
import {getFilterParams} from '../utils/getFilterParams';
import {IJiraIssue} from './useJiraIssue';

export interface IJiraResponse {
	[JiraEnum.ISSUES]?: IJiraIssue[];
	[JiraEnum.KEYWORDS]?: string;
	[JiraEnum.PAGE]?: number;
	[JiraEnum.PAGE_SIZE]?: number;
	[JiraEnum.SORT_BY]?: string;
	[JiraEnum.SORT_ORDER]?: string;
}

export interface IProps {
	[JiraEnum.FILTERS]?: IFilterOptions;
	[JiraEnum.KEYWORDS]?: string;
	[JiraEnum.PAGE]?: number;
	[JiraEnum.PAGE_SIZE]?: number;
	[JiraEnum.SORT_BY]?: string;
	[JiraEnum.SORT_ORDER]?: string;
}

const useJiraSearch = () => {
	const [jiraSearch, setJiraSearch] = useState<IJiraResponse>();
	const [loading, setLoading] = useState(true);
	const [searchParams, setSearchParams] = useSearchParams();

	const fetchJiraSearch = useCallback(async (params: URLSearchParams) => {
		setLoading(true);

		const queryString = getFilterParams(params.toString());

		try {
			const response: IJiraResponse =
				await Liferay.OAuth2Client.FromUserAgentApplication(
					'liferay-customer-etc-spring-boot-oaua'
				)
					.fetch(
						`/jira/security-vulnerabilities/search?${queryString}`
					)
					.then((response) => response.json());

			setJiraSearch(response);
		}
		catch (error) {
			console.error('Error fetching Jira data:', error);
		}
		finally {
			setLoading(false);
		}
	}, []);

	const updateSearchParams = useCallback(
		(newParams?: IProps) => {
			const newSearchParams = new URLSearchParams(searchParams);

			if (newParams === undefined) {
				return;
			}

			for (const key in newParams) {
				if (Object.prototype.hasOwnProperty.call(newParams, key)) {
					const value = newParams[key as keyof IProps];

					if (value === undefined || value === null) {
						newSearchParams.delete(key);
						continue;
					}

					if (key === JiraEnum.FILTERS) {
						const filters = value as IFilterOptions;

						for (const filterKey in filters) {
							if (
								Object.prototype.hasOwnProperty.call(
									filters,
									filterKey
								)
							) {
								const filterValue =
									filters[filterKey as keyof typeof filters];

								if (
									filterValue === undefined ||
									filterValue === null ||
									!filterValue.length
								) {
									newSearchParams.delete(filterKey);
									continue;
								}

								newSearchParams.set(
									filterKey,
									filterValue.join(',')
								);
							}
						}
					}
					else if (typeof value === 'string') {
						if (value) {
							newSearchParams.set(key, value);
						}
						else {
							newSearchParams.delete(key);
						}
					}
				}
			}

			setSearchParams(newSearchParams);

			return newSearchParams;
		},
		[searchParams, setSearchParams]
	);

	useEffect(() => {
		fetchJiraSearch(searchParams);
	}, [fetchJiraSearch, searchParams]);

	return {jiraSearch, loading, searchParams, updateSearchParams};
};

export default useJiraSearch;
