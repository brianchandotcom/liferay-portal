/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import remove from 'lodash/remove';
import React, {useEffect, useRef, useState} from 'react';
import AppliedFilters from '~/shared/components/filter/AppliedFilters';
import DropdownMenu from '~/shared/components/filter/DropdownMenu';

interface IFilterProps {
	items?: Item[];
	onChange: (param: SelectedItems) => void;
}

type Item = {
	category: string;
	checked?: boolean;
	hasSearch: boolean;
	inputType: string;
	items: Item[] | null;
	label: string;
	value: string;
};

type SelectedItems = {[key: string]: string[]};

const Filter: React.FC<IFilterProps> = ({
	items: initialItems = [],
	onChange,
}) => {
	const [selectedItems, setSelectedItems] = useState<SelectedItems>({});
	const [showDropdown, setShowDropdown] = useState(false);

	const [items, setItems] = useState(initialItems);

	const _elementRef = useRef<HTMLDivElement>(null);

	useEffect(() => {
		document.addEventListener('click', handleDocClick);

		return () => {
			document.removeEventListener('click', handleDocClick);
		};
	}, []);

	useEffect(() => {
		setItems(getCheckedItems(initialItems));

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [initialItems]);

	const getCheckedItems = (parentItems: Item[]): Item[] =>
		parentItems.map((item) => {
			const categoryItems = selectedItems[item.category];

			let childItems = null;

			if (item.items) {
				childItems = getCheckedItems(item.items);
			}

			if (categoryItems && categoryItems.indexOf(item.label) > -1) {
				return {
					...item,
					checked: true,
					items: childItems,
				};
			}

			return {...item, items: childItems};
		});

	const updateRadioItems = ({category, label}: Item): void => {
		handleUpdateFilters({...selectedItems, [category]: [label]});
	};

	const updateCheckboxItems = ({category, checked, label}: Item): void => {
		const categoryItems = selectedItems[category] || [];

		if (checked) {
			categoryItems.push(label);
		}
		else {
			remove(categoryItems, (n) => n === label);
		}

		selectedItems[category] = categoryItems;

		handleUpdateFilters({...selectedItems});
	};

	const handleChangeDropdownItem = ({
		dropdownItem,
	}: {
		dropdownItem: Item;
	}): void => {
		if (dropdownItem.inputType === 'radio') {
			updateRadioItems(dropdownItem);
		}
		else if (dropdownItem.inputType === 'checkbox') {
			updateCheckboxItems(dropdownItem);
		}
	};

	const handleClickToggleDropdown = (): void => {
		setShowDropdown(!showDropdown);
	};

	const handleDocClick = ({target}: Event): void => {
		if (!_elementRef.current) {
			return;
		}

		const dropdown = _elementRef.current.querySelector(
			'.analytics-dropdown'
		);
		const dropdownMenu: Element[] = Object.assign(
			[],
			document.querySelectorAll('.analytics-dropdown-menu')
		);

		if (
			(dropdown && dropdown.contains(target as Node)) ||
			dropdownMenu.find((menu) => menu.contains(target as Node))
		) {
			return;
		}

		setShowDropdown(false);
	};

	const handleUpdateFilters = (selectedItems: SelectedItems): void => {
		onChange(selectedItems);

		setSelectedItems(selectedItems);
	};

	return (
		<div className="analytics-filter" ref={_elementRef}>
			<div className="analytics-dropdown border-0 btn-group dropdown">
				<ClayButton
					aria-label="Dropdown Filter"
					className="btn-outline-borderless dropdown-toggle"
					displayType="secondary"
					onClick={handleClickToggleDropdown}
					size="sm"
				>
					{Liferay.Language.get('filter')}

					<ClayIcon
						className="icon-root ml-2"
						symbol="caret-bottom"
					/>
				</ClayButton>
			</div>

			<DropdownMenu
				items={getCheckedItems(items)}
				onSelectItemsChange={handleChangeDropdownItem}
				show={showDropdown}
			/>

			<AppliedFilters
				filters={selectedItems}
				onChange={handleUpdateFilters}
			/>
		</div>
	);
};

export {Filter};
export default Filter;
