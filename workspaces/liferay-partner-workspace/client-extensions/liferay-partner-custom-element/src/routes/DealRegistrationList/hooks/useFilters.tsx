/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {Filters} from '../../../common/utils/constants/filters';
import getSearchFilterTerm from '../../../common/utils/getSearchFilterTerm';
import {INITIAL_FILTER} from '../utils/constants/initialFilter';

export default function useFilters(submittedDealsFilter?: boolean) {
	const [filters, setFilters] = useState(
		(JSON.parse(
			sessionStorage.getItem('dealRegistrationFilters')!
		) as typeof INITIAL_FILTER) || INITIAL_FILTER
	);

	const [filtersTerm, setFilterTerm] = useState('');

	const dealsInitialFilter = submittedDealsFilter
		? Filters.DEAL_LISTING.submitted
		: Filters.DEAL_LISTING.rejected;

	const onFilter = (newFilters: Partial<typeof INITIAL_FILTER>) =>
		setFilters((previousFilters) => ({...previousFilters, ...newFilters}));

	sessionStorage.setItem('dealRegistrationFilters', JSON.stringify(filters));
	sessionStorage.setItem(
		'submittedDealsFilter',
		JSON.stringify(submittedDealsFilter)
	);

	useEffect(() => {
		let initialFilter = '';

		if (dealsInitialFilter) {
			initialFilter = initialFilter
				? initialFilter.concat(dealsInitialFilter)
				: `${dealsInitialFilter}`;
		}

		if (filters.searchTerm) {
			initialFilter = initialFilter
				? initialFilter.concat(getSearchFilterTerm(filters.searchTerm))
				: getSearchFilterTerm(filters.searchTerm);
		}

		setFilterTerm(initialFilter);
	}, [dealsInitialFilter, filters.searchTerm, setFilters]);

	return {filters, filtersTerm, onFilter};
}
