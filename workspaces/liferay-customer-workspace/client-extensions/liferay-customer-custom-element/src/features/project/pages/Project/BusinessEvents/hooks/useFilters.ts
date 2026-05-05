/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useState} from 'react';
import {IFilterOption} from '~/components/Filter/Filter';

import {INITIAL_FILTER} from '../utils/constants/initialFilter';

export interface IState {
	availableFilters?: IFilterOption[];
	searchTerm?: string;
	selectedFilters?: IFilterOption[];
}

export default function useFilters(): {
	filters: IState;
	handleFilterChange: (value: IFilterOption[]) => void;
	handleSearchChange: (value: string) => void;
} {
	const [filters, setFilters] = useState<IState>({
		availableFilters: INITIAL_FILTER,
		searchTerm: '',
		selectedFilters: [],
	});

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

	return {filters, handleFilterChange, handleSearchChange};
}
