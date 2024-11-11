/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import {ClayCheckbox, ClayRadio} from '@clayui/form';
import i18n from '~/common/I18n';

import {
	IFilterOptions,
	IFilters,
} from '../../pages/SecurityVulnerabilitiesList/SecurityVulnerabilitiesList';

import './SVFilter.css';

interface IProps {
	filterOptions: IFilterOptions;
	filters: IFilters;
	onChange: (filters: IFilters) => void;
}

const SVFilter = ({filterOptions, filters, onChange}: IProps) => {
	const toggleFilterValue = (filter: keyof IFilters, value: string) => {
		const currentFilterValues = filters[filter];

		if (Array.isArray(currentFilterValues)) {
			if (currentFilterValues.includes(value)) {
				onChange({
					...filters,
					[filter]: currentFilterValues.filter((v) => v !== value),
				});
			}
			else {
				onChange({
					...filters,
					[filter]: [...currentFilterValues, value],
				});
			}
		}
		else {
			onChange({
				...filters,
				[filter]: value,
			});
		}
	};

	const updateFilterValues = (
		filterKey: keyof IFilters,
		values: string[] | null
	) => {
		const newFilters = {...filters, [filterKey]: values ?? []};
		onChange({
			...filters,
			...newFilters,
		});
	};

	return (
		<div className="sv-filter-content">
			<div className="sv-filter-box">
				<h5 className="pb-2">{i18n.translate('sort-by')}</h5>

				<div className="align-items-center justify-content-center">
					{filterOptions.sorts.map((sort) => (
						<ClayRadio
							aria-label={sort}
							checked={filters.sort === sort}
							key="sort"
							label={i18n.translate(sort)}
							onChange={() => {
								toggleFilterValue('sort', sort);
							}}
							value={sort}
						/>
					))}
				</div>
			</div>

			<div className="sv-filter-box sv-severities">
				<h5>{i18n.translate('severity')}</h5>

				<div className="d-flex my-2">
					<ClayButton
						aria-label="Select All"
						className="mr-3 p-0 sv-select-all-button"
						displayType="link"
						onClick={() =>
							updateFilterValues('severities', [
								...filterOptions.severities,
							])
						}
					>
						{i18n.translate('select-all')}
					</ClayButton>

					<ClayButton
						aria-label="Clear"
						className="p-0 sv-clear-button"
						displayType="link"
						onClick={() => updateFilterValues('severities', null)}
					>
						{i18n.translate('clear')}
					</ClayButton>
				</div>

				{filterOptions.severities.map((severity) => (
					<ClayCheckbox
						aria-label={severity}
						checked={filters.severities.includes(severity)}
						key={severity}
						label={severity}
						onChange={() => {
							toggleFilterValue('severities', severity);
						}}
					/>
				))}
			</div>

			<div className="sv-categories sv-filter-box">
				<h5>{i18n.translate('category')}</h5>

				<div className="d-flex my-2">
					<ClayButton
						aria-label="Select All"
						className="mr-3 p-0 sv-select-all-button"
						displayType="link"
						onClick={() =>
							updateFilterValues('categories', [
								...filterOptions.categories,
							])
						}
					>
						{i18n.translate('select-all')}
					</ClayButton>

					<ClayButton
						aria-label="Clear"
						className="p-0 sv-clear-button"
						displayType="link"
						onClick={() => updateFilterValues('categories', null)}
					>
						{i18n.translate('clear')}
					</ClayButton>
				</div>

				{filterOptions.categories.map((category) => (
					<ClayCheckbox
						aria-label={category}
						checked={filters.categories.includes(category)}
						key={category}
						label={category}
						onChange={() => {
							toggleFilterValue('categories', category);
						}}
					/>
				))}
			</div>

			<div className="sv-classifications sv-filter-box">
				<h5>{i18n.translate('issue-classification')}</h5>

				<div className="d-flex my-2">
					<ClayButton
						aria-label="Select All"
						className="mr-3 p-0 sv-select-all-button"
						displayType="link"
						onClick={() =>
							updateFilterValues('classifications', [
								...filterOptions.classifications,
							])
						}
					>
						{i18n.translate('select-all')}
					</ClayButton>

					<ClayButton
						aria-label="Clear"
						className="p-0 sv-clear-button"
						displayType="link"
						onClick={() =>
							updateFilterValues('classifications', null)
						}
					>
						{i18n.translate('clear')}
					</ClayButton>
				</div>

				{filterOptions.classifications.map((classification) => (
					<ClayCheckbox
						aria-label={classification}
						checked={filters.classifications.includes(
							classification
						)}
						key={classification}
						label={classification}
						onChange={() => {
							toggleFilterValue(
								'classifications',
								classification
							);
						}}
					/>
				))}
			</div>

			<div className="sv-filter-box sv-versions">
				<h5>{i18n.translate('affected-version')}</h5>

				<div className="d-flex my-2">
					<ClayButton
						aria-label="Select All"
						className="mr-3 p-0 sv-select-all-button"
						displayType="link"
						onClick={() =>
							updateFilterValues('versions', [
								...filterOptions.versions,
							])
						}
					>
						{i18n.translate('select-all')}
					</ClayButton>

					<ClayButton
						aria-label="Clear"
						className="p-0 sv-clear-button"
						displayType="link"
						onClick={() => updateFilterValues('versions', null)}
					>
						{i18n.translate('clear')}
					</ClayButton>
				</div>

				{filterOptions.versions.map((version) => (
					<ClayCheckbox
						aria-label={version}
						checked={filters.versions.includes(version)}
						key={version}
						label={version}
						onChange={() => {
							toggleFilterValue('versions', version);
						}}
					/>
				))}
			</div>
		</div>
	);
};

export default SVFilter;
