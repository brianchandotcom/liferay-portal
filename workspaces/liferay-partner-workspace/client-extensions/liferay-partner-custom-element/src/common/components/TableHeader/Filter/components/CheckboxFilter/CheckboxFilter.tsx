/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import {useEffect, useState} from 'react';

interface Props {
	availableItems?: string[];
	clearCheckboxes: boolean;
	updateFilters: (checkedItems: string[]) => void;
}

const CheckboxFilter = ({
	availableItems,
	clearCheckboxes,
	updateFilters,
}: Props) => {
	const [checkedItems, setCheckedItems] = useState<string[]>([]);

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
				{availableItems?.map((item: string, index: number) => (
					<ClayCheckbox
						checked={checkedItems.includes(item)}
						key={`${item}-${index}`}
						label={item}
						onChange={() => handleSelectedCheckbox(item)}
					/>
				))}
			</div>

			<div className="mb-3 mt-2 mx-3">
				<ClayButton
					className="w-100"
					onClick={() => updateFilters(checkedItems)}
					small={true}
				>
					Apply
				</ClayButton>
			</div>
		</div>
	);
};
export default CheckboxFilter;
