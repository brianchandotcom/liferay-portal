/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {Filters} from '../../../common/utils/constants/filters';
import {getCamelCase} from '../../../common/utils/getCamelCase';
import {INITIAL_FILTER} from '../utils/constants/initialFilter';
import getDateCreatedFilterTerm from '../utils/getDateCreatedFilterTerm';

export default function useFilters(
	openClaimsFilter: boolean,
	sort: string,
	urlParams: URLSearchParams,
	isChannel?: boolean
) {
	const [filters, setFilters] = useState(() => {
		const initialFilter: typeof INITIAL_FILTER =
			structuredClone(INITIAL_FILTER);

		if (urlParams.getAll('partner').length) {
			initialFilter.partner.value = urlParams.getAll('partner')!;
		}

		if (urlParams.get('search')) {
			initialFilter.searchTerm = urlParams.get('search')!;
		}

		if (urlParams.getAll('status').length) {
			initialFilter.status.value = urlParams.getAll('status')!;
		}

		if (urlParams.get('enddate')) {
			initialFilter.submitDate.dates.endDate = urlParams.get('enddate')!;
		}

		if (urlParams.get('startdate')) {
			initialFilter.submitDate.dates.startDate =
				urlParams.get('startdate')!;
		}

		if (urlParams.getAll('type').length) {
			initialFilter.type.value = urlParams.getAll('type')!;
		}

		return initialFilter;
	});

	const mdfClaimRoleFilter = isChannel
		? openClaimsFilter
			? Filters.MDF_CLAIM_LISTING.channelsOpen
			: Filters.MDF_CLAIM_LISTING.channelsCompleted
		: openClaimsFilter
			? Filters.MDF_CLAIM_LISTING.partnersOpen
			: Filters.MDF_CLAIM_LISTING.partnersCompleted;

	const onFilter = (newFilters: Partial<typeof INITIAL_FILTER>) => {
		setFilters((previousFilters) => {
			return {...previousFilters, ...newFilters};
		});
	};

	useEffect(() => {
		let initialFilter = '';
		let hasFilter = false;

		if (mdfClaimRoleFilter) {
			initialFilter = initialFilter
				? initialFilter.concat(mdfClaimRoleFilter)
				: `${mdfClaimRoleFilter}`;
		}

		if (
			filters.submitDate.dates.endDate ||
			filters.submitDate.dates.startDate
		) {
			hasFilter = true;
			initialFilter = getDateCreatedFilterTerm(
				initialFilter,
				filters.submitDate
			);

			if (filters.submitDate?.dates.endDate) {
				urlParams.set('enddate', filters.submitDate?.dates.endDate);
			}
			else {
				urlParams.delete('enddate');
			}

			if (filters.submitDate?.dates.startDate) {
				urlParams.set('startdate', filters.submitDate?.dates.startDate);
			}
			else {
				urlParams.delete('startdate');
			}
		}
		else {
			urlParams.delete('enddate');
			urlParams.delete('startdate');
		}

		if (filters.status.value.length) {
			hasFilter = true;

			const statusFilter = filters.status.value
				.map((status) => {
					return `(mdfClaimStatus eq '${getCamelCase(status)}')`;
				})
				.join(' or ');

			initialFilter = initialFilter
				? initialFilter.concat(` and (${statusFilter})`)
				: initialFilter.concat(`(${statusFilter})`);

			urlParams.delete('status');

			filters.status.value.forEach((value) =>
				urlParams.append('status', value)
			);
		}
		else {
			urlParams.delete('status');
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

		if (filters.type.value.length) {
			hasFilter = true;

			const partnerFilter = filters.type.value
				.map((type) => {
					return `(partial eq ${type === 'Partial' ? true : false})`;
				})
				.join(' or ');

			initialFilter = initialFilter
				? initialFilter.concat(` and (${partnerFilter})`)
				: initialFilter.concat(`(${partnerFilter})`);

			urlParams.delete('type');

			filters.type.value.forEach((value) =>
				urlParams.append('type', value)
			);
		}
		else {
			urlParams.delete('type');
		}

		onFilter({
			hasValue: hasFilter,
		});

		urlParams.set('filter', initialFilter);
		urlParams.set('sort', sort);
	}, [
		filters.submitDate,
		filters.partner,
		filters.searchTerm,
		filters.status,
		filters.type,
		mdfClaimRoleFilter,
		openClaimsFilter,
		setFilters,
		sort,
		urlParams,
	]);

	return {filters, onFilter, setFilters};
}
