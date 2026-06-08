/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import autobind from 'autobind-decorator';
import {PropTypes} from 'prop-types';
import React from 'react';
import {Label} from '~/shared/components/filter/Label';
import {
	filterLangMap,
	hasCategoryFilters,
	isClearFilterVisible,
} from '~/shared/util/filter';
import {getDeviceLabel} from '~/shared/util/lang';

const CLASSNAME = 'analytics-applied-filters';

/**
 * Applied Filters
 * @class
 */
class AppliedFilters extends React.Component {
	static defaultProps = {
		filters: {},
	};

	static propTypes = {
		filters: PropTypes.object.isRequired,
		onChange: PropTypes.func,
	};

	/**
	 * Handle Remove Filter
	 * @param {object} param0
	 */
	@autobind
	handleRemoveFilter({category, label}) {
		const {filters} = this.props;

		const hasCategoryProperty = Object.prototype.hasOwnProperty.call(
			filters,
			category
		);

		if (category && hasCategoryProperty) {
			this.updateFilters({
				...filters,
				[category]: filters[category].filter(
					(currentLabel) => currentLabel !== label
				),
			});
		}
	}

	/**
	 * Handle Remove All Filters
	 */
	@autobind
	handleRemoveAllFilters() {
		this.updateFilters({});
	}

	/**
	 * Update Filters
	 * @param {object} filters
	 */
	updateFilters(filters) {
		const {onChange} = this.props;

		if (onChange) {
			onChange(filters);
		}
	}

	/**
	 * Render Category
	 * @param {string} category
	 */
	renderCategory(category) {
		const {filters} = this.props;

		const subItems = filters[category];

		return (
			<div className="ml-3" key={`${category}_item`}>
				<small className="font-weight-semibold mr-2 text-secondary text-uppercase">
					{filterLangMap[category]}
				</small>
				{subItems.map((label, index) => (
					<Label
						key={`${index}_subItems`}
						label={getDeviceLabel(label) || label}
						onRemove={() =>
							this.handleRemoveFilter({category, label})
						}
					/>
				))}
			</div>
		);
	}

	/**
	 * Lifecycle Render - ReactJS
	 */
	render() {
		const {filters} = this.props;

		const items = Object.keys(filters);

		return (
			<div className={`${CLASSNAME} d-inline-flex align-items-center`}>
				{items.map(
					(category) =>
						hasCategoryFilters(filters, category) &&
						this.renderCategory(category)
				)}

				{isClearFilterVisible(filters) && (
					<ClayButton
						className="ml-4"
						displayType="secondary"
						onClick={this.handleRemoveAllFilters}
						size="sm"
					>
						<ClayIcon className="mr-2" symbol="trash" />

						{Liferay.Language.get('clear-filter')}
					</ClayButton>
				)}
			</div>
		);
	}
}

export {AppliedFilters};
export default AppliedFilters;
