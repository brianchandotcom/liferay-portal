/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {withEmpty} from '~/cerebro-shared/hocs/utils';
import BasePage from '~/shared/components/base-page';
import Table from '~/shared/components/table';
import {compose, withError, withPaginationBar, withToolbar} from '~/shared/hoc';
import {withLoading} from '~/shared/hoc/util';
import FaroConstants from '~/shared/util/constants';

const {
	pagination: {delta: defaultDelta},
} = FaroConstants;

const defaultHOC = (WrappedComponent) => (props) => (
	<WrappedComponent {...props} />
);

const withBaseResults = (withData, configs) => {
	const {
		disableSearch = false,
		emptyDescription,
		emptyIcon,
		emptyPrimary = true,
		emptyTitle,
		getColumns,
		legacyDropdownRangeKey = true,
		rangeKeys,
		rowIdentifier,
		showDropdownRangeKey = true,
		showFilterAndOrder = false,
		withQueryOptions = defaultHOC,
		withSelection = defaultHOC,
	} = configs;

	const TableWithData = compose(
		withData(),
		withQueryOptions,
		(WrappedComponent) => (props) => (
			<WrappedComponent {...props} columns={getColumns(props)} />
		),
		withSelection,
		withToolbar({
			disableSearch,
			legacyDropdownRangeKey,
			rangeKeys,
			showDropdownRangeKey,
			showFilterAndOrder,
		}),
		withPaginationBar({defaultDelta}),
		withLoading(),
		withError({page: false}),
		withEmpty({
			emptyDescription,
			emptyIcon,
			emptyTitle,
			primary: emptyPrimary,
		})
	)(Table);

	class BaseResults extends React.Component {
		static contextType = BasePage.Context;

		render() {
			const {
				context: {filters},
				props: {
					delta,
					orderIOMap,
					page,
					query,
					rangeSelectors,
					router,
					...otherProps
				},
			} = this;

			return (
				<div className="d-flex flex-column flex-grow-1 justify-content-between">
					<TableWithData
						{...otherProps}
						delta={delta}
						filters={filters}
						orderIOMap={orderIOMap}
						page={page}
						query={query}
						rangeKeys={rangeKeys}
						rangeSelectors={rangeSelectors}
						router={router}
						rowIdentifier={rowIdentifier}
					/>
				</div>
			);
		}
	}

	return BaseResults;
};

export default withBaseResults;
