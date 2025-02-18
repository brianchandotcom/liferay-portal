/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button} from '~/components';
import BadgePillFilter from '~/components/BadgePillFilter';
import i18n from '~/utils/I18n';

import {BE_INITIAL_FILTER} from '../../../../../utils/BE_INITIAL_FILTER';
import {IBEFilter} from '../../../../../utils/constants/IBEFilter';

interface IBadgeProps {
	filtersState: [IBEFilter, React.Dispatch<React.SetStateAction<IBEFilter>>];
}

const BEBadgeFilter = ({filtersState: [filters, setFilters]}: IBadgeProps) => {
	const hasFilterValue = (filters: IBEFilter) => {
		return Object.values(filters).some(
			({value}) => Array.isArray(value) && !!value.length
		);
	};

	return (
		<div className="bd-highlight d-flex">
			<div className="bd-highlight col d-flex flex-wrap pl-0 pt-2 w-100">
				{!!filters.eventType.value?.length && (
					<BadgePillFilter
						filterName={filters.eventType.name}
						filterValue={filters.eventType.value.join(', ')}
						onClick={() =>
							setFilters((previousFilters) => ({
								...previousFilters,
								eventType: {
									...previousFilters.eventType,
									value: [],
								},
							}))
						}
					/>
				)}

				{!!filters.eventStatus.value?.length && (
					<BadgePillFilter
						filterName={filters.eventStatus.name}
						filterValue={filters.eventStatus.value.join(', ')}
						onClick={() =>
							setFilters((previousFilters) => ({
								...previousFilters,
								eventStatus: {
									...previousFilters.eventStatus,
									value: [],
								},
							}))
						}
					/>
				)}
			</div>

			<div className="bd-highlight flex-shrink-2 pt-2">
				{hasFilterValue(filters) && (
					<Button
						borderless
						className="link"
						onClick={() => {
							setFilters({
								...BE_INITIAL_FILTER,
								searchTerm: filters.searchTerm,
							});
						}}
						prependIcon="times-circle"
						small
					>
						{i18n.translate('clear-all-filters')}
					</Button>
				)}
			</div>
		</div>
	);
};

export default BEBadgeFilter;
