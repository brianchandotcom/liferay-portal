/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {Filters} from '../../../common/utils/constants/filters';
import {getCamelCase} from '../../../common/utils/getCamelCase';
import getSearchFilterTerm from '../../../common/utils/getSearchFilterTerm';
import {INITIAL_FILTER} from '../utils/constants/initialFilter';
import getActivityPeriodFilterTerm from '../utils/getActivityPeriodFilterTerm';

export default function useFilters(
	openRequestFilter: boolean,
	isChannel?: boolean
) {
	const [filters, setFilters] = useState(
		(JSON.parse(
			sessionStorage.getItem('requestFilters')!
		) as typeof INITIAL_FILTER) || INITIAL_FILTER
	);

	const [filtersTerm, setFilterTerm] = useState('');

	const mdfRequestRoleFilter = isChannel
		? openRequestFilter
			? Filters.MDF_REQUEST_LISTING.channelsOpen
			: Filters.MDF_REQUEST_LISTING.channelsCompleted
		: openRequestFilter
		? Filters.MDF_REQUEST_LISTING.partnersOpen
		: Filters.MDF_REQUEST_LISTING.partnersCompleted;

	const onFilter = (newFilters: Partial<typeof INITIAL_FILTER>) =>
		setFilters((previousFilters) => ({...previousFilters, ...newFilters}));

	sessionStorage.setItem('requestFilters', JSON.stringify(filters));
	sessionStorage.setItem(
		'openRequestFilter',
		JSON.stringify(openRequestFilter)
	);

	useEffect(() => {
		let initialFilter = '';
		let hasFilter = false;

		if (mdfRequestRoleFilter) {
			initialFilter = initialFilter
				? initialFilter.concat(mdfRequestRoleFilter)
				: `${mdfRequestRoleFilter}`;
		}

		if (
			filters.activityPeriod.dates.endDate ||
			filters.activityPeriod.dates.startDate
		) {
			hasFilter = true;
			initialFilter = getActivityPeriodFilterTerm(
				initialFilter,
				filters.activityPeriod
			);
		}

		if (filters.status.value.length) {
			hasFilter = true;

			const statusFilter = filters.status.value
				.map((status) => {
					return `(mdfRequestStatus eq '${getCamelCase(status)}')`;
				})
				.join(' or ');

			initialFilter = initialFilter
				? initialFilter.concat(` and (${statusFilter})`)
				: initialFilter.concat(`(${statusFilter})`);
		}

		if (filters.partner.value.length) {
			hasFilter = true;

			const partnerFilter = filters.partner.value
				.map((partner) => {
					return `(companyName eq '${partner}')`;
				})
				.join(' or ');

			initialFilter = initialFilter
				? initialFilter.concat(` and (${partnerFilter})`)
				: initialFilter.concat(`(${partnerFilter})`);
		}

		if (filters.searchTerm) {
			initialFilter = initialFilter.concat(
				getSearchFilterTerm(filters.searchTerm)
			);
		}

		onFilter({
			hasValue: hasFilter,
		});

		setFilterTerm(initialFilter);
	}, [
		filters.activityPeriod,
		filters.searchTerm,
		filters.status,
		filters.partner,
		setFilters,
		mdfRequestRoleFilter,
	]);

	return {filters, filtersTerm, onFilter, setFilters};
}
