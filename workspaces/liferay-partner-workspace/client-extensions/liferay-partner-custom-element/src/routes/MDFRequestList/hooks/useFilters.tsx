/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import useIsChannel from '../../../common/hooks/useIsChannel';
import {Filters} from '../../../common/utils/constants/filters';
import {getCamelCase} from '../../../common/utils/getCamelCase';
import getSearchFilterTerm from '../../../common/utils/getSearchFilterTerm';
import {INITIAL_FILTER} from '../utils/constants/initialFilter';
import getActivityPeriodFilterTerm from '../utils/getActivityPeriodFilterTerm';

export default function useFilters() {
	const [filters, setFilters] = useState(INITIAL_FILTER);

	const [filtersTerm, setFilterTerm] = useState('');
	const {isChannel} = useIsChannel();

	const mdfRequestRoleFilter = isChannel
		? Filters.MDF_REQUEST_LISTING.channels
		: Filters.MDF_REQUEST_LISTING.partners;

	const onFilter = (newFilters: Partial<typeof INITIAL_FILTER>) =>
		setFilters((previousFilters) => ({...previousFilters, ...newFilters}));

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
			initialFilter = getSearchFilterTerm(filters.searchTerm);
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
