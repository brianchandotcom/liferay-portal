/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import CheckboxFilter from '../filters/CheckboxFilter';
import DateFilter from '../filters/DateFilter';
import {Dates} from '../filters/DateFilter/DateFilter';

export enum FilterTypes {
	DATE = 'date',
	CHECKBOX = 'checkbox',
}

type DateFilterProps = {
	clearInputs: {
		dates: Dates;
	};
	filterDescription: string;
};

type CheckboxFilterProps = {
	availableItems: string[];
	clearCheckboxes: boolean;
};

export type FilterProps = {
	initialValues: any;
	props: CheckboxFilterProps | DateFilterProps;
	type: FilterTypes;
	updateFilter: (filter: any) => void;
};

type FilterSelectorProps = {
	closeFilterMenu: () => void;
	filterProps: FilterProps;
};

const FilterSelector = ({
	closeFilterMenu,
	filterProps: {
		props: {...fieldProps},
		type,
		...filterBaseProps
	},
}: FilterSelectorProps) => {
	switch (type) {
		case 'date':
			return (
				<DateFilter
					{...filterBaseProps}
					{...fieldProps}
					closeFilterMenu={closeFilterMenu}
				/>
			);

		case 'checkbox':
			return (
				<CheckboxFilter
					{...filterBaseProps}
					{...fieldProps}
					closeFilterMenu={closeFilterMenu}
				/>
			);

		default:
			return <></>;
	}
};

export default FilterSelector;
