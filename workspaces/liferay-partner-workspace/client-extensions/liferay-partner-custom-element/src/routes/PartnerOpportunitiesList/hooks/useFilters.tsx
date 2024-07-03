/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';

import {Filters} from '../../../common/utils/constants/filters';
import getCloseDateFilterTerm from '../utils/constants/getCloseDateFilterTerm';
import {INITIAL_FILTER} from '../utils/constants/initialFilter';

export default function useFilters(
	sort: string,
	urlParams: URLSearchParams,
	isRenewalListing?: boolean
) {
	const [filters, setFilters] = useState(
		(JSON.parse(
			sessionStorage.getItem('opportunitiesFilters')!
		) as typeof INITIAL_FILTER) || INITIAL_FILTER
	);

	const opportunitiesInitialFilter = isRenewalListing
		? Filters.RENEWAL_LISTING.opportunities
		: Filters.OPPORTUNITY_LISTING.opportunities;

	const onFilter = useCallback(
		(newFilters: Partial<typeof INITIAL_FILTER>) =>
			setFilters((previousFilters) => ({
				...previousFilters,
				...newFilters,
			})),
		[]
	);

	sessionStorage.setItem('opportunitiesFilters', JSON.stringify(filters));

	useEffect(() => {
		let initialFilter = ``;
		let hasFilter = false;

		if (opportunitiesInitialFilter) {
			initialFilter = initialFilter
				? initialFilter.concat(opportunitiesInitialFilter)
				: `${opportunitiesInitialFilter}`;
		}

		if (
			filters.closeDate?.dates?.endDate ||
			filters.closeDate?.dates?.startDate
		) {
			hasFilter = true;
			initialFilter = getCloseDateFilterTerm(
				initialFilter,
				filters.closeDate
			);
		}

		if (filters.stage.value.length) {
			hasFilter = true;

			const stageFilter = filters.stage.value
				.map((stage) => {
					return `(stage eq '${stage}')`;
				})
				.join(' or ');

			initialFilter = initialFilter
				? initialFilter.concat(` and (${stageFilter})`)
				: initialFilter.concat(`(${stageFilter})`);
		}

		onFilter({
			hasValue: hasFilter,
		});

		urlParams.set('filter', initialFilter);
		urlParams.set('sort', sort);
	}, [
		filters.closeDate,
		filters.searchTerm,
		filters.stage,
		onFilter,
		opportunitiesInitialFilter,
		setFilters,
		sort,
		urlParams,
	]);

	return {filters, onFilter, setFilters};
}
