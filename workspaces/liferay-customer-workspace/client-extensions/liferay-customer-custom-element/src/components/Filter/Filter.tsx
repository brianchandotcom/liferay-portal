/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import i18n from '~/utils/I18n';

import FilterCheckbox from './components/FilterCheckbox';
import FilterDropdown from './components/FilterDropdown';

export interface IFilterOption {
	name: string;
	value: string[];
}

export interface IProps {
	availableFilters: IFilterOption[];
	onChange: (selectedFilters: IFilterOption[]) => void;
	selectedFilters: IFilterOption[];
}

const Filter = ({availableFilters, onChange, selectedFilters}: IProps) => {
	const menuItems = availableFilters.map((option) => ({
		child: option.name,
		title: i18n.translate(option.name),
	}));

	const menus: Record<string, any> = {
		root: menuItems,
	};

	menuItems.forEach((item) => {
		const name = item.child;
		const availableOption = availableFilters.find(
			(option) => option.name === name
		);

		if (!availableOption) {
			console.warn(`Available option not found for name: ${name}`);

			return;
		}

		menus[name] = [
			{
				child: (
					<FilterCheckbox
						availableItems={availableOption.value}
						clearCheckboxes={!selectedFilters.length}
						updateFilters={(checkedItems: string[]) => {
							const updatedSelectedFilters = selectedFilters.map(
								(option) => {
									if (option.name === name) {
										return {...option, value: checkedItems};
									}

									return option;
								}
							);
							onChange(updatedSelectedFilters);
						}}
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
