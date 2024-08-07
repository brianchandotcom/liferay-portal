/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import {useEffect, useState} from 'react';

interface CheckboxFilterProps {
	availableItems?: string[];
	clearCheckboxes?: boolean;
	closeFilterMenu: () => void;
	initialValues?: string[];
	updateFilter: (filter: any) => void;
}
const CheckboxFilter = ({
	availableItems,
	clearCheckboxes,
	closeFilterMenu,
	initialValues,
	updateFilter,
}: CheckboxFilterProps) => {
	const [checkedItems, setCheckedItems] = useState<string[]>(
		initialValues ? initialValues : []
	);
	const [showAll, setShowAll] = useState<boolean>(false);

	const itemsToShow = showAll ? availableItems : availableItems?.slice(0, 5);

	const handleSelectedCheckbox = (checkedItem: string) => {
		if (checkedItems.includes(checkedItem)) {
			return setCheckedItems(
				checkedItems.filter((item) => item !== checkedItem)
			);
		}

		setCheckedItems([...checkedItems, checkedItem]);
	};

	useEffect(() => {
		if (clearCheckboxes) {
			setCheckedItems([]);
		}
	}, [clearCheckboxes]);

	return (
		<div className="w-100">
			<div className="pt-2 px-3">
				{itemsToShow?.map((item, index) => (
					<ClayCheckbox
						checked={checkedItems.includes(item)}
						key={`${item}-${index}`}
						label={item}
						onChange={() => handleSelectedCheckbox(item)}
					/>
				))}
				{(availableItems?.length as number) > 5 && (
					<a
						className="font-weight-semi-bold text-neutral-8"
						onClick={() => setShowAll(!showAll)}
						style={{cursor: 'pointer'}}
					>
						{showAll ? 'Show less' : 'Show more'}
					</a>
				)}
			</div>

			<div className="mb-3 mt-3 mx-3">
				<Button
					className="w-100"
					onClick={() => {
						updateFilter(checkedItems);

						closeFilterMenu();
					}}
					size="sm"
				>
					Apply
				</Button>
			</div>
		</div>
	);
};
export default CheckboxFilter;
