/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button} from '~/components';
import BadgeButton from '~/components/BadgeButton';
import i18n from '~/utils/I18n';

import {IFilterOption} from '../../Filter';

export interface IProps {
	onChange: (selectedFilters: IFilterOption[]) => void;
	searchResultsCount: number;
	searchTerm: string;
	selectedFilters: IFilterOption[];
}

const FilterResults = ({
	onChange,
	searchResultsCount,
	searchTerm,
	selectedFilters,
}: IProps) => {
	const hasFilterValue = (selectedFilters: IFilterOption[]) => {
		return selectedFilters.some((option) => !!option.value.length);
	};

	const handleClearFilter = (filterName: string) => {
		onChange(
			selectedFilters.map((option) => {
				if (option.name === filterName) {
					return {...option, value: []};
				}

				return option;
			})
		);
	};

	const handleClearAllFilters = () => {
		onChange([]);
	};

	return (
		<>
			<div className="d-flex">
				{searchTerm !== '' && (
					<p className="font-weight-semi-bold m-0 mt-3 text-paragraph-sm">
						{searchResultsCount > 1
							? `${i18n.sub('x-results-for-x', [
									searchResultsCount.toString(),
									`"${searchTerm}"`,
								])}`
							: `${i18n.sub('x-result-for-x', [
									searchResultsCount.toString(),
									`"${searchTerm}"`,
								])}`}
					</p>
				)}
			</div>
			<div className="bd-highlight d-flex">
				<div className="bd-highlight col d-flex flex-wrap pl-0 pt-2 w-100">
					{selectedFilters.map((option) => {
						if (option.value.length) {
							return (
								<BadgeButton
									filterName={i18n.translate(option.name)}
									filterValue={option.value.join(', ')}
									key={option.name}
									onClick={() =>
										handleClearFilter(option.name)
									}
								/>
							);
						}

						return null;
					})}
				</div>

				<div className="bd-highlight flex-shrink-2 pt-2">
					{hasFilterValue(selectedFilters) && (
						<Button
							borderless
							className="link"
							onClick={handleClearAllFilters}
							prependIcon="times-circle"
							small
						>
							{i18n.translate('clear-all-filters')}
						</Button>
					)}
				</div>
			</div>
		</>
	);
};

export default FilterResults;
