/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import {useSearchParams} from 'react-router-dom';
import {Liferay} from '~/services/liferay';

import {
	FILTER_MAP,
	IProps as IFilterOptions,
} from '../utils/constants/filterOptions';
import {JiraEnum} from '../utils/constants/jiraEnum';
import {IJiraIssue} from './useJiraIssue';

export interface IJiraResponse {
	[JiraEnum.ISSUES]?: IJiraIssue[];
	[JiraEnum.KEYWORDS]?: string;
	[JiraEnum.PAGE]?: number;
	[JiraEnum.PAGE_SIZE]?: number;
	[JiraEnum.SORT_BY]?: string;
	[JiraEnum.SORT_ORDER]?: string;
	[JiraEnum.TOTAL]?: number;
}

export interface IProps {
	[JiraEnum.FILTERS]?: IFilterOptions;
	[JiraEnum.KEYWORDS]?: string;
	[JiraEnum.PAGE]?: number;
	[JiraEnum.PAGE_SIZE]?: number;
	[JiraEnum.SORT_BY]?: string;
	[JiraEnum.SORT_ORDER]?: string;
}

const getSearchParams = (
	urlSearchParams: URLSearchParams,
	defaultParams?: IProps,
	params?: IProps
): URLSearchParams => {
	const newURLSearchParams = new URLSearchParams(urlSearchParams);

	for (const key in params) {
		const defaultValue = defaultParams?.[key as keyof IProps];
		const value = params[key as keyof IProps];

		if (value === undefined || value === null || value === defaultValue) {
			newURLSearchParams.delete(key);

			continue;
		}

		if (key === JiraEnum.FILTERS) {
			const filters = value as IFilterOptions;
			const defaultFilters = defaultValue as IFilterOptions | undefined;

			for (const filterKey in filters) {
				const defaultFilterValue =
					defaultFilters?.[filterKey as keyof typeof filters];
				const filterValue = filters[filterKey as keyof typeof filters];

				if (
					filterValue === undefined ||
					filterValue === null ||
					!filterValue.length ||
					(defaultFilterValue !== undefined &&
						filterValue.join(',') === defaultFilterValue.join(','))
				) {
					newURLSearchParams.delete(filterKey);

					continue;
				}

				newURLSearchParams.set(filterKey, filterValue.join(','));
			}
		}
		else if (typeof value === 'string') {
			if (value) {
				newURLSearchParams.set(key, value);
			}
			else {
				newURLSearchParams.delete(key);
			}
		}
		else if (typeof value === 'number') {
			newURLSearchParams.set(key, String(value));
		}
	}

	return newURLSearchParams;
};

const processSearchParams = (
	urlSearchParams: URLSearchParams,
	defaultParams?: IProps
): string => {
	const newURLSearchParams = new URLSearchParams(urlSearchParams);

	for (const key in defaultParams) {
		const value = defaultParams[key as keyof IProps];

		if (key === JiraEnum.FILTERS) {
			const filters = value as IFilterOptions;

			for (const filterKey in filters) {
				const filterValue = filters[filterKey as keyof typeof filters];

				if (
					!newURLSearchParams.has(filterKey) &&
					filterValue &&
					filterValue.length
				) {
					newURLSearchParams.set(filterKey, filterValue.join(','));
				}
			}
		}
		else if (!newURLSearchParams.has(key) && typeof value === 'string') {
			newURLSearchParams.set(key, value);
		}
		else if (!newURLSearchParams.has(key) && typeof value === 'number') {
			newURLSearchParams.set(key, String(value));
		}
	}

	return newURLSearchParams
		.toString()
		.split('&')
		.map((param) => {
			const [key, value] = param.split('=');
			const mappedKey = FILTER_MAP[key] ?? key;

			return `${mappedKey}=${value}`;
		})
		.join('&');
};

const useJiraSearch = (defaultParams?: IProps) => {
	const [jiraSearch, setJiraSearch] = useState<IJiraResponse>();
	const [loading, setLoading] = useState(true);
	const [searchParams, setSearchParams] = useSearchParams();

	const fetchJiraSearch = useCallback(
		async (urlSearchParams: URLSearchParams, defaultParams?: IProps) => {
			setLoading(true);

			const queryString = processSearchParams(
				urlSearchParams,
				defaultParams
			);

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
		},
		[]
	);

	const updateSearchParams = useCallback(
		(params: IProps) => {
			setSearchParams((urlSearchParams) => {
				return getSearchParams(urlSearchParams, defaultParams, params);
			});
		},
		[defaultParams, setSearchParams]
	);

	useEffect(() => {
		fetchJiraSearch(searchParams, defaultParams);
	}, [defaultParams, fetchJiraSearch, searchParams]);

	return {jiraSearch, loading, searchParams, updateSearchParams};
};

export default useJiraSearch;
