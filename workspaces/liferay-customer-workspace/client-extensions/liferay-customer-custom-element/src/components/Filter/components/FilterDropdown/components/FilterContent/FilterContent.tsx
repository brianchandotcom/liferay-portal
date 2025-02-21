/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import React, {useEffect} from 'react';
import {IFilterOption} from '~/components/Filter/Filter';
import i18n from '~/utils/I18n';

interface IProps {
	filter: IFilterOption;
	onChange: (selectedFilters: IFilterOption[]) => void;
	selectedFilters: IFilterOption[];
}

const FilterContent: React.FC<IProps> = ({
	filter,
	onChange,
	selectedFilters,
}) => {
	const [localSelectedValues, setLocalSelectedValues] = React.useState<
		string[]
	>(
		selectedFilters.find(
			(selectedFilter) => selectedFilter.name === filter.name
		)?.value || []
	);

	useEffect(() => {
		setLocalSelectedValues(
			selectedFilters.find(
				(selectedFilter) => selectedFilter.name === filter.name
			)?.value || []
		);
	}, [selectedFilters, filter.name]);

	const handleCheckboxChange = (value: string, checked: boolean) => {
		let updatedValues = [...localSelectedValues];

		if (checked) {
			updatedValues.push(value);
		}
		else {
			updatedValues = updatedValues.filter((v) => v !== value);
		}

		setLocalSelectedValues(updatedValues);
	};

	const handleClick = () => {
		const updatedFilter: IFilterOption = {
			name: filter.name,
			value: localSelectedValues,
		};

		onChange(
			selectedFilters
				.filter((selectedFilter) => selectedFilter.name !== filter.name)
				.concat(localSelectedValues.length ? updatedFilter : [])
		);
	};

	return (
		<div className="w-100">
			<div className="filter-content pt-2 px-3">
				{filter.value.map((value) => (
					<ClayCheckbox
						checked={localSelectedValues.includes(value)}
						key={`${filter.name}-${value}`}
						label={i18n.translate(value)}
						onChange={(event) =>
							handleCheckboxChange(value, event.target.checked)
						}
					/>
				))}
			</div>

			<div className="mb-3 mt-2 mx-3">
				<ClayButton
					className="w-100"
					onClick={handleClick}
					small={true}
				>
					{i18n.translate('apply')}
				</ClayButton>
			</div>
		</div>
	);
};

export default FilterContent;
