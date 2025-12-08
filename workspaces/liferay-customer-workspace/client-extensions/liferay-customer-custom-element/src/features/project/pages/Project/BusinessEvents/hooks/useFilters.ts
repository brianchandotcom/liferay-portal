/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useMemo, useState} from 'react';
import {IFilterOption} from '~/components/Filter/Filter';
import {IProject} from '~/utils/types';

import {INITIAL_FILTER} from '../utils/constants/initialFilter';

export interface IState {
	availableFilters?: IFilterOption[];
	searchTerm?: string;
	selectedFilters?: IFilterOption[];
}

export default function useFilters(project?: IProject): {
	filterQuery: string;
	filters: IState;
	handleFilterChange: (value: IFilterOption[]) => void;
	handleSearchChange: (value: string) => void;
} {
	const [filters, setFilters] = useState<IState>({
		availableFilters: INITIAL_FILTER,
		searchTerm: '',
		selectedFilters: [],
	});

	const generateFilterQuery = useCallback(
		(filters: IState) => {
			const queryParams: string[] = [];

			queryParams.push(
				`r_accountEntryToBusinessEvents_accountEntryId eq '${project?.id || ''}'`
			);

			if (
				filters.selectedFilters &&
				Boolean(filters.selectedFilters.length)
			) {
				filters.selectedFilters.forEach((filter) => {
					if (filter.values && Boolean(filter.values.length)) {
						const filterQuery = `(${filter.values
							.map(
								(value: {key: string; name: string}) =>
									`${filter.key} eq '${value.key}'`
							)
							.join(' or ')})`;
						queryParams.push(filterQuery);
					}
				});
			}

			if (filters.searchTerm?.trim()) {
				queryParams.push(`(contains(name, '${filters.searchTerm}'))`);
			}

			const oneYearAgo = new Date();

			oneYearAgo.setFullYear(oneYearAgo.getFullYear() - 1);

			queryParams.push(
				`((eventStatus ne 'canceled' and eventStatus ne 'completed') or (eventStatus eq 'canceled' and dateModified ge ${oneYearAgo.toISOString()}) or (eventStatus eq 'completed' and actualGoLiveDateTime ge ${oneYearAgo.toISOString()}))`
			);

			return queryParams.length
				? `filter=${queryParams.join(' and ')}`
				: '';
		},
		[project?.id]
	);

	const filterQuery = useMemo(
		() => generateFilterQuery(filters),
		[filters, generateFilterQuery]
	);

	const handleFilterChange = useCallback(
		(newFilterOptions: IFilterOption[]) => {
			setFilters(((prevFilters: IState) => ({
				...prevFilters,
				selectedFilters: newFilterOptions,
			})) as unknown as IState);
		},
		[]
	);

	const handleSearchChange = useCallback((searchTerm: string) => {
		setFilters(((prevFilters: IState) => ({
			...prevFilters,
			searchTerm,
		})) as unknown as IState);
	}, []);

	return {filterQuery, filters, handleFilterChange, handleSearchChange};
}
