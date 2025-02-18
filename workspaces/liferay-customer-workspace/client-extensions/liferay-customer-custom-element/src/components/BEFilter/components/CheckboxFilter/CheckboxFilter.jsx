/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import {useEffect, useState} from 'react';
import i18n from '~/utils/I18n';

const CheckboxFilter = ({availableItems, clearCheckboxes, updateFilters}) => {
	const [checkedItems, setCheckedItems] = useState([]);

	const itemDisplayMap = {
		Golive: 'Go-Live',
		OtherEvent: 'Other Event',
	};

	const displayItems = availableItems.map((item) => {
		return {
			display: itemDisplayMap[item] || item,
			value: item,
		};
	});

	const handleSelectedCheckbox = (checkedItem) => {
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
			<div className="filter-content pt-2 px-3">
				{displayItems?.map(({display, value}, index) => (
					<ClayCheckbox
						checked={checkedItems.includes(value)}
						key={`${value}-${index}`}
						label={display}
						onChange={() => handleSelectedCheckbox(value)}
					/>
				))}
			</div>

			<div className="mb-3 mt-2 mx-3">
				<ClayButton
					className="w-100"
					onClick={() => updateFilters(checkedItems)}
					required
					small={true}
				>
					{i18n.translate('apply')}
				</ClayButton>
			</div>
		</div>
	);
};
export default CheckboxFilter;
