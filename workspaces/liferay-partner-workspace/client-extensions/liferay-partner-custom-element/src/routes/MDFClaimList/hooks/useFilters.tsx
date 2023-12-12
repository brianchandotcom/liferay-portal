/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {Filters} from '../../../common/utils/constants/filters';
import {getCamelCase} from '../../../common/utils/getCamelCase';
import getSearchFilterTerm from '../../../common/utils/getSearchFilterTerm';
import {INITIAL_FILTER} from '../utils/constants/initialFilter';
import getDateCreatedFilterTerm from '../utils/getDateCreatedFilterTerm';

export default function useFilters(
	openClaimsFilter: boolean,
	isChannel?: boolean
) {
	const [filters, setFilters] = useState(INITIAL_FILTER);

	const [filtersTerm, setFilterTerm] = useState('');

	const mdfClaimRoleFilter = isChannel
		? openClaimsFilter
			? Filters.MDF_CLAIM_LISTING.channelsOpen
			: Filters.MDF_CLAIM_LISTING.channelsCompleted
		: openClaimsFilter
		? Filters.MDF_CLAIM_LISTING.partnersOpen
		: Filters.MDF_CLAIM_LISTING.partnersCompleted;

	const onFilter = (newFilters: Partial<typeof INITIAL_FILTER>) =>
		setFilters((previousFilters) => ({...previousFilters, ...newFilters}));

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
		filters.submitDate,
		filters.partner,
		filters.searchTerm,
		filters.status,
		filters.type,
		setFilters,
		openClaimsFilter,
		mdfClaimRoleFilter,
	]);

	return {filters, filtersTerm, onFilter, setFilters};
}
