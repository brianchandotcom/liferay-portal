/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import i18n from '~/utils/I18n';

import FilterDropdown from './components/FilterDropdown';
import FilterContent from './components/FilterDropdown/components/FilterContent';

export interface IFilterOption {
	key: string;
	name: string;
	values: {
		key: string;
		name: string;
	}[];
}

export interface IProps {
	availableFilters: IFilterOption[];
	onChange: (selectedFilters: IFilterOption[]) => void;
	selectedFilters: IFilterOption[];
}

const Filter = ({availableFilters, onChange, selectedFilters}: IProps) => {
	const menus: Record<string, any> = {
		root: availableFilters.map((filter) => ({
			child: filter.name,
			title: i18n.translate(filter.name),
			type: 'item',
		})),
	};

	availableFilters.forEach((filter) => {
		menus[filter.name] = [
			{
				child: (
					<FilterContent
						filter={filter}
						onChange={onChange}
						selectedFilters={selectedFilters}
					/>
				),
				type: 'component',
			},
		];
	});

	return (
		<FilterDropdown
			alignmentPosition={undefined}
			className="align-items-center d-flex"
			containerElement={undefined}
			initialActiveMenu="root"
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
	);
};

export default Filter;
