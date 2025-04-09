/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import React, {useEffect} from 'react';
import {IFilterOption} from '~/components/Filter/Filter';
import i18n from '~/utils/I18n';
import getKebabCase from '~/utils/getKebabCase';

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
		{
			key: string;
			name: string;
		}[]
	>(
		selectedFilters.find(
			(selectedFilter) => selectedFilter.key === filter.key
		)?.values || []
	);

	useEffect(() => {
		setLocalSelectedValues(
			selectedFilters.find(
				(selectedFilter) => selectedFilter.key === filter.key
			)?.values || []
		);
	}, [selectedFilters, filter.key]);

	const handleCheckboxChange = (
		value: {key: string; name: string},
		checked: boolean
	) => {
		let updatedValues = [...localSelectedValues];

		if (checked) {
			updatedValues.push(value);
		}
		else {
			updatedValues = updatedValues.filter((v) => v.key !== value.key);
		}

		setLocalSelectedValues(updatedValues);
	};

	const handleClick = () => {
		const updatedFilter: IFilterOption = {
			key: filter.key,
			name: filter.name,
			values: localSelectedValues,
		};

		onChange(
			localSelectedValues.length
				? selectedFilters
						.filter(
							(selectedFilter) =>
								selectedFilter.key !== filter.key
						)
						.concat(updatedFilter)
				: selectedFilters.filter(
						(selectedFilter) => selectedFilter.key !== filter.key
					)
		);
	};

	return (
		<div className="w-100">
			<div className="filter-content pt-2 px-3">
				{filter.values.map((value) => (
					<ClayCheckbox
						checked={localSelectedValues.some(
							(item) => item.key === value.key
						)}
						key={`${filter.key}-${value.key}`}
						label={i18n.translate(
							getKebabCase(value.key) as string
						)}
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
