/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {Filters} from '../../../common/utils/constants/filters';
import {getCamelCase} from '../../../common/utils/getCamelCase';
import {INITIAL_FILTER} from '../utils/constants/initialFilter';
import getActivityPeriodFilterTerm from '../utils/getActivityPeriodFilterTerm';

export default function useFilters(
	openRequestFilter: boolean,
	urlParams: URLSearchParams,
	sort: string,
	isChannel?: boolean,
	nestedFields?: string
) {
	const [filters, setFilters] = useState(() => {
		const initialFilter: typeof INITIAL_FILTER =
			structuredClone(INITIAL_FILTER);

		if (urlParams.get('enddate')) {
			initialFilter.activityPeriod.dates.endDate =
				urlParams.get('enddate')!;
		}

		if (urlParams.get('startdate')) {
			initialFilter.activityPeriod.dates.startDate =
				urlParams.get('startdate')!;
		}

		if (urlParams.getAll('partner').length) {
			initialFilter.partner.value = urlParams.getAll('partner')!;
		}

		if (urlParams.get('search')) {
			initialFilter.searchTerm = urlParams.get('search')!;
		}

		if (urlParams.getAll('status').length) {
			initialFilter.status.value = urlParams.getAll('status')!;
		}

		return initialFilter;
	});

	const mdfRequestRoleFilter = isChannel
		? openRequestFilter
			? Filters.MDF_REQUEST_LISTING.channelsOpen
			: Filters.MDF_REQUEST_LISTING.channelsCompleted
		: openRequestFilter
			? Filters.MDF_REQUEST_LISTING.partnersOpen
			: Filters.MDF_REQUEST_LISTING.partnersCompleted;

	const onFilter = (newFilters: Partial<typeof INITIAL_FILTER>) => {
		setFilters((previousFilters) => {
			return {...previousFilters, ...newFilters};
		});
	};

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

			if (filters.activityPeriod?.dates.endDate) {
				urlParams.set('enddate', filters.activityPeriod?.dates.endDate);
			}
			else {
				urlParams.delete('enddate');
			}

			if (filters.activityPeriod?.dates.startDate) {
				urlParams.set(
					'startdate',
					filters.activityPeriod?.dates.startDate
				);
			}
			else {
				urlParams.delete('startdate');
			}
		}
		else {
			urlParams.delete('enddate');
			urlParams.delete('startdate');
		}

		if (nestedFields) {
			urlParams.set('nestedFields', nestedFields);
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

			urlParams.delete('partner');

			filters.partner.value.forEach((value) =>
				urlParams.append('partner', value)
			);
		}
		else {
			urlParams.delete('partner');
		}

		onFilter({
			hasValue: hasFilter,
		});

		urlParams.set('filter', initialFilter);
		urlParams.set('sort', sort);
	}, [
		filters.activityPeriod,
		filters.searchTerm,
		filters.status,
		filters.partner,
		mdfRequestRoleFilter,
		nestedFields,
		setFilters,
		sort,
		urlParams,
	]);

	return {filters, onFilter, setFilters};
}
