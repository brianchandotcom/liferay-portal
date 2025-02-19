/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';

import './TableHeader.css';

import Filter from '~/components/Filter';
import {IFilterOption} from '~/components/Filter/Filter';
import FilterResults from '~/components/Filter/components/FilterResults/FilterResults';
import SearchBar from '~/components/SearchBar';
import i18n from '~/utils/I18n';

interface IProps {
	availableFilters: IFilterOption[];
	hasCreatePermissions: boolean;
	onFilterChange: (selectedFilters: IFilterOption[]) => void;
	onSearchChange: (searchTerm: string) => void;
	searchResultsCount: number;
	searchTerm: string;
	selectedFilters: IFilterOption[];
}

const TableHeader = ({
	availableFilters,
	hasCreatePermissions,
	onFilterChange,
	onSearchChange,
	searchResultsCount,
	searchTerm,
	selectedFilters,
}: IProps) => {
	return (
		<div className="d-flex flex-column mt-4">
			<div className="be-table-header p-3">
				<div className="d-flex justify-content-between">
					<div className="d-flex">
						<SearchBar
							clearSearchTerm={() => onSearchChange('')}
							isBusinessEvent={true}
							onSearchSubmit={(term: string) => {
								onSearchChange(term);
							}}
						/>

						<Filter
							availableFilters={availableFilters}
							onChange={onFilterChange}
							selectedFilters={selectedFilters}
						/>
					</div>

					{hasCreatePermissions && (
						<Button className="be-create-event">
							{i18n.translate('create-event')}
						</Button>
					)}
				</div>

				<FilterResults
					onChange={onFilterChange}
					searchResultsCount={searchResultsCount}
					searchTerm={searchTerm}
					selectedFilters={selectedFilters}
				/>
			</div>
		</div>
	);
};

export default TableHeader;
