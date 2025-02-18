/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import CheckboxFilter from '~/components/BEFilter/components/CheckboxFilter';
import DropDownWithDrillDown from '~/components/BEFilter/components/DropDownWithDrillDown';
import i18n from '~/utils/I18n';

import {IBEFilter} from '../../../../utils/constants/IBEFilter';

interface IFilterProps {
	availableFields: {
		eventStatus: string[];
		eventType: string[];
	};
	filtersState: [IBEFilter, React.Dispatch<React.SetStateAction<IBEFilter>>];
}

const BEFilter = ({
	availableFields,
	filtersState: [filters, setFilters],
}: IFilterProps) => {
	const menus = {
		x0a0: [
			{child: 'x0a1', title: i18n.translate('event-status')},
			{child: 'x0a2', title: i18n.translate('event-type')},
		],
		x0a1: [
			{
				child: (
					<CheckboxFilter
						availableItems={availableFields.eventStatus}
						clearCheckboxes={
							filters.eventStatus.value?.length === 0
						}
						updateFilters={(checkedItems: any) =>
							setFilters((previousFilters) => ({
								...previousFilters,
								eventStatus: {
									...previousFilters.eventStatus,
									value: checkedItems,
								},
							}))
						}
					/>
				),
				type: 'component',
			},
		],
		x0a2: [
			{
				child: (
					<CheckboxFilter
						availableItems={availableFields.eventType}
						clearCheckboxes={filters.eventType.value?.length === 0}
						updateFilters={(checkedItems: any) =>
							setFilters((previousFilters) => ({
								...previousFilters,
								eventType: {
									...previousFilters.eventType,
									value: checkedItems,
								},
							}))
						}
					/>
				),
				type: 'component',
			},
		],
	};

	return (
		<>
			<DropDownWithDrillDown
				alignmentPosition={undefined}
				className="align-items-center d-flex"
				containerElement={undefined}
				initialActiveMenu="x0a0"
				menuElementAttrs={undefined}
				menuHeight={undefined}
				menuWidth={undefined}
				menus={menus}
				offsetFn={undefined}
				trigger={
					<ClayButton borderless className="text-neutral-10">
						<span className="inline-item inline-item-before">
							<ClayIcon symbol="filter" />
						</span>

						{i18n.translate('filters')}
					</ClayButton>
				}
			/>
		</>
	);
};

export default BEFilter;
