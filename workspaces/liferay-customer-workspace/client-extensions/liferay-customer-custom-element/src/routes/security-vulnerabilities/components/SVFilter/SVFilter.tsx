/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import {ClayCheckbox, ClayRadio} from '@clayui/form';
import i18n from '~/common/I18n';

import {IProps as IJiraSearch} from '../../hooks/useJiraSearch';
import {IProps as IFilterOptions} from '../../utils/constants/filterOptions';
import {JiraEnum} from '../../utils/constants/jiraEnum';
import {IProps as ISortOptions} from '../../utils/constants/sortOptions';

import './SVFilter.css';

interface IProps {
	filterOptions: IFilterOptions;
	onChange: (params: IJiraSearch) => void;
	params: URLSearchParams;
	sortOptions: ISortOptions;
}

const SVFilter = ({filterOptions, onChange, params, sortOptions}: IProps) => {
	const handleFilterChange = (
		param: keyof IFilterOptions,
		value: string[]
	) => {
		let newValue = value;

		if (value.length === 1) {
			newValue = params.getAll(param)[0]?.split(',') || [];

			if (newValue.includes(value[0])) {
				newValue = newValue.filter((item) => item !== value[0]);
			}
			else {
				newValue.push(value[0]);
			}
		}

		onChange({
			[JiraEnum.FILTERS]: {
				...params
					.getAll(JiraEnum.FILTERS)
					.reduce((acc, curr) => ({...acc, ...JSON.parse(curr)}), {}),
				[param]: newValue,
			},
		});
	};

	const renderFilterSection = (
		filterKey: keyof IFilterOptions,
		languageKey: string
	) => (
		<div className="sv-filter-box">
			<h5>{i18n.translate(languageKey)}</h5>

			<div className="d-flex my-2">
				<ClayButton
					aria-label={i18n.translate('select-all')}
					className="mr-3 p-0 sv-select-all-button"
					displayType="link"
					onClick={() =>
						handleFilterChange(
							filterKey,
							filterOptions[filterKey] as string[]
						)
					}
				>
					{i18n.translate('select-all')}
				</ClayButton>

				<ClayButton
					aria-label={i18n.translate('clear')}
					className="p-0 sv-clear-button"
					displayType="link"
					onClick={() => handleFilterChange(filterKey, [])}
				>
					{i18n.translate('clear')}
				</ClayButton>
			</div>

			{(filterOptions[filterKey] as string[])?.map((value) => (
				<ClayCheckbox
					aria-label={i18n.translate(value)}
					checked={params.getAll(filterKey)[0]?.includes(value)}
					key={value}
					label={i18n.translate(value)}
					onChange={() => handleFilterChange(filterKey, [value])}
					value={value}
				/>
			))}
		</div>
	);

	return (
		<div className="sv-filter-content">
			<div className="sv-filter-box">
				<h5 className="pb-2">{i18n.translate('sort-by')}</h5>

				<div className="align-items-center justify-content-center">
					{sortOptions.sorts.map((sortOption) => (
						<ClayRadio
							aria-label={i18n.translate(sortOption.key)}
							checked={
								params.get(JiraEnum.SORT_BY) ===
									sortOption[JiraEnum.SORT_BY] &&
								params.get(JiraEnum.SORT_ORDER) ===
									sortOption[JiraEnum.SORT_ORDER]
							}
							key={sortOption.key}
							label={i18n.translate(sortOption.key)}
							onChange={() => {
								onChange({
									[JiraEnum.SORT_BY]:
										sortOption[JiraEnum.SORT_BY],
									[JiraEnum.SORT_ORDER]:
										sortOption[JiraEnum.SORT_ORDER],
								});
							}}
							value={sortOption.key}
						/>
					))}
				</div>
			</div>

			{renderFilterSection(JiraEnum.SEVERITY, 'severity')}
			{renderFilterSection(JiraEnum.CATEGORY, 'category')}
			{renderFilterSection(
				JiraEnum.CLASSIFICATION,
				'issue-classification'
			)}
			{renderFilterSection(
				JiraEnum.AFFECTED_VERSIONS,
				'affected-version'
			)}
		</div>
	);
};

export default SVFilter;
