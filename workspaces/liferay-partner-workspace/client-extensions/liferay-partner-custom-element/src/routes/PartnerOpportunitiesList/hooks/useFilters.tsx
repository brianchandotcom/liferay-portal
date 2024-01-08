/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';

import {Filters} from '../../../common/utils/constants/filters';
import getSearchFilterTerm from '../../../common/utils/getSearchFilterTerm';
import {INITIAL_FILTER} from '../utils/constants/initialFilter';

export default function useFilters(
	openOpportunitiesFilter?: boolean,
	isRenewalListing?: boolean
) {
	const [filters, setFilters] = useState(
		(JSON.parse(
			sessionStorage.getItem('opportunitiesFilters')!
		) as typeof INITIAL_FILTER) || INITIAL_FILTER
	);

	const [filtersTerm, setFilterTerm] = useState('');

	const opportunitiesInitialFilter = isRenewalListing
		? openOpportunitiesFilter
			? Filters.RENEWAL_LISTING.openWIP
			: Filters.RENEWAL_LISTING.closedWIP
		: openOpportunitiesFilter
		? Filters.OPPORTUNITY_LISTING.openWIP
		: Filters.OPPORTUNITY_LISTING.closedWIP;

	const onFilter = useCallback(
		(newFilters: Partial<typeof INITIAL_FILTER>) =>
			setFilters((previousFilters) => ({
				...previousFilters,
				...newFilters,
			})),
		[]
	);

	sessionStorage.setItem('opportunitiesFilters', JSON.stringify(filters));
	sessionStorage.setItem(
		'openOpportunitiesFilter',
		JSON.stringify(openOpportunitiesFilter)
	);

	useEffect(() => {
		let initialFilter = ``;

		if (opportunitiesInitialFilter) {
			initialFilter = initialFilter
				? initialFilter.concat(opportunitiesInitialFilter)
				: `${opportunitiesInitialFilter}`;
		}

		if (filters.searchTerm) {
			initialFilter = initialFilter
				? initialFilter.concat(getSearchFilterTerm(filters.searchTerm))
				: getSearchFilterTerm(filters.searchTerm);
		}

		setFilterTerm(initialFilter);
	}, [filters.searchTerm, opportunitiesInitialFilter, setFilters]);

	return {filters, filtersTerm, onFilter};
}
