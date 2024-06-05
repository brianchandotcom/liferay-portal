/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {Filters} from '../../../common/utils/constants/filters';
import {INITIAL_FILTER} from '../utils/constants/initialFilter';
import getDateSubmittedFilterTerm from '../utils/getDateSubmittedFilterTerm';

export default function useFilters(
	sort: string,
	urlParams: URLSearchParams,
	submittedDealsFilter?: boolean
) {
	const [filters, setFilters] = useState(
		(JSON.parse(
			sessionStorage.getItem('dealRegistrationFilters')!
		) as typeof INITIAL_FILTER) || INITIAL_FILTER
	);

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
		let hasFilter = false;
		let initialFilter = '';

		if (dealsInitialFilter) {
			initialFilter = initialFilter
				? initialFilter.concat(dealsInitialFilter)
				: `${dealsInitialFilter}`;
		}

		if (
			filters.dataSubmitted?.dates.endDate ||
			filters.dataSubmitted?.dates.startDate
		) {
			hasFilter = true;
			initialFilter = getDateSubmittedFilterTerm(
				initialFilter,
				filters.dataSubmitted
			);
		}

		onFilter({
			hasValue: hasFilter,
		});

		urlParams.set('filter', initialFilter);
		urlParams.set('sort', sort);
	}, [
		dealsInitialFilter,
		filters.searchTerm,
		filters.dataSubmitted,
		setFilters,
		sort,
		urlParams,
	]);

	return {filters, onFilter};
}
