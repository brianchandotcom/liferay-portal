/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import PropTypes from 'prop-types';
import React from 'react';
import {compose} from 'redux';
import {withEmpty, withError} from '~/cerebro-shared/hocs/utils';
import BasePage from '~/shared/components/base-page';
import Filter from '~/shared/components/filter';
import {RangeKeyTimeRanges} from '~/shared/util/constants';

/**
 * HOC
 * @description Filter
 * @param {object} withFilter
 */
const withFilterComponent = (withFilter) => {
	const FilterWithData = compose(
		withFilter(),
		withEmpty(),
		withError()
	)(Filter);

	/**
	 * Filter Component
	 * @class
	 */
	class FilterComponent extends React.Component {
		static contextType = BasePage.Context;

		static propTypes = {
			onChange: PropTypes.func,
		};

		/**
		 * Receive applied filters and set state
		 * @param {object} filters
		 */
		@autobind
		handleApplyFilters(appliedFilters) {
			const {onChange} = this.props;

			onChange(appliedFilters);
		}

		/**
		 * Lifecycle Render - ReactJS
		 */
		render() {
			const {
				context: {router},
				props: {className},
			} = this;

			return (
				<FilterWithData
					className={className}
					isTopLevel
					onChange={this.handleApplyFilters}
					rangeSelectors={{rangeKey: RangeKeyTimeRanges.LastYear}}
					router={router}
				/>
			);
		}
	}

	return FilterComponent;
};

export {withFilterComponent};
export default withFilterComponent;
