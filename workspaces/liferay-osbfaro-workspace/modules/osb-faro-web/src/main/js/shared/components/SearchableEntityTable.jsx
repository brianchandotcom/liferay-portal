/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import getCN from 'classnames';
import {noop} from 'lodash';
import PropTypes from 'prop-types';
import React from 'react';
import BaseResults from '~/shared/components/BaseResults';
import Table from '~/shared/components/table';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

const searchableEntityTablePropTypes = {
	autoFocusSearch: PropTypes.bool,
	bordered: PropTypes.bool,
	checkDisabled: PropTypes.func,
	columns: PropTypes.array,
	delta: PropTypes.number,
	internalSort: PropTypes.bool,
	nowrap: PropTypes.bool,
	onDeltaChange: PropTypes.func,
	onOrderIOMapChange: PropTypes.func,
	onPageChange: PropTypes.func,
	onQueryChange: PropTypes.func,
	orderIOMap: PropTypes.object,
	overrideLoading: PropTypes.bool,
	page: PropTypes.number,
	query: PropTypes.string,
	renderInlineRowActions: PropTypes.func,
	renderRowActions: PropTypes.func,
	rowIdentifier: PropTypes.oneOfType([PropTypes.array, PropTypes.string]),
	showCheckbox: PropTypes.bool,
};

class SearchableEntityTable extends React.Component {
	static defaultProps = {
		autoFocusSearch: true,
		bordered: false,
		checkDisabled: noop,
		internalSort: false,
		nowrap: true,
		overrideLoading: false,
		showCheckbox: false,
	};

	static propTypes = searchableEntityTablePropTypes;

	constructor(props) {
		super(props);

		this._resultsRef = React.createRef();
	}

	/**
	 * Public method for refreshing data
	 */
	reload() {
		this._resultsRef.current.reload();
	}

	@autobind
	renderTable({
		className,
		items,
		loading,
		onSelectItemsChange,
		selectedItemsIOMap,
	}) {
		const {
			bordered,
			checkDisabled,
			columns,
			internalSort,
			nowrap,
			onOrderIOMapChange,
			orderIOMap,
			overrideLoading,
			renderInlineRowActions,
			renderRowActions,
			rowBordered,
			rowIdentifier,
			showCheckbox,
		} = this.props;

		return (
			<Table
				checkDisabled={checkDisabled}
				className={className}
				columns={columns}
				headingNowrap
				internalSort={internalSort}
				items={items}
				list={bordered}
				loading={loading || overrideLoading}
				nowrap={nowrap}
				onOrderIOMapChange={onOrderIOMapChange}
				onSelectItemsChange={onSelectItemsChange}
				orderIOMap={orderIOMap}
				renderInlineRowActions={renderInlineRowActions}
				renderRowActions={renderRowActions}
				rowBordered={rowBordered}
				rowIdentifier={rowIdentifier}
				selectedItemsIOMap={selectedItemsIOMap}
				showCheckbox={showCheckbox}
			/>
		);
	}

	render() {
		const {
			autoFocusSearch,
			bordered,
			checkDisabled,
			className,
			delta,
			onDeltaChange,
			onOrderIOMapChange,
			onPageChange,
			onQueryChange,
			orderIOMap,
			page,
			query,
			showCheckbox,
			...otherProps
		} = this.props;

		const classes = getCN('searchable-table-root', className, {
			'searchable-table-borderless': !bordered,
		});

		return (
			<BaseResults
				{...omitDefinedProps(
					otherProps,
					searchableEntityTablePropTypes
				)}
				autoFocusSearch={autoFocusSearch}
				checkDisabled={checkDisabled}
				className={classes}
				delta={delta}
				onDeltaChange={onDeltaChange}
				onOrderIOMapChange={onOrderIOMapChange}
				onPageChange={onPageChange}
				onQueryChange={onQueryChange}
				orderIOMap={orderIOMap}
				page={page}
				query={query}
				ref={this._resultsRef}
				resultsRenderer={this.renderTable}
				showCheckbox={showCheckbox}
			/>
		);
	}
}

export default SearchableEntityTable;
