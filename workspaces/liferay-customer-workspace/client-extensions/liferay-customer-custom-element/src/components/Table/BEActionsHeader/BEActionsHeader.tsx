/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';

import './BEActionsHeader.css';

import SearchBar from '~/components/SearchBar';
import i18n from '~/utils/I18n';

import {IBEFilter} from '../../../features/project/pages/Project/BusinessEvent/utils/constants/IBEFilter';
import BEFilter from '~/components/BEFilter';
import BEBadgeFilter from '~/components/BEFilter/components/FilterResults/BEBadgeFilter';

interface IPropsHeader {
	availableFields: {
		eventStatus: string[];
		eventType: string[];
	};
	filtersState: [IBEFilter, React.Dispatch<React.SetStateAction<IBEFilter>>];
	hasAllEventsPermissions: boolean;
}

const BEActionsHeader = ({
	availableFields,
	filtersState: [filters, setFilters],
	hasAllEventsPermissions,
}: IPropsHeader) => {
	return (
		<div className="d-flex flex-column mt-4">
			<div className="be-table-header p-3">
				<div className="d-flex justify-content-between">
					<div className="d-flex">
						<SearchBar
							clearSearchTerm={undefined}
							isBusinessEvent={true}
							onSearchSubmit={(term: string) => {
								setFilters((previousFilters) => ({
									...previousFilters,
									searchTerm: term,
								}));
							}}
						/>

						<BEFilter
							availableFields={availableFields}
							filtersState={[filters, setFilters]}
						/>
					</div>

					{hasAllEventsPermissions && (
						<Button className="be-create-event">
							{i18n.translate('create-event')}
						</Button>
					)}
				</div>

				<BEBadgeFilter filtersState={[filters, setFilters]} />
			</div>
		</div>
	);
};

export default BEActionsHeader;
