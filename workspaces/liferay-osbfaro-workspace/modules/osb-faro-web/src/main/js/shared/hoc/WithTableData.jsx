/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {withEmpty} from '~/cerebro-shared/hocs/utils';
import Table from '~/shared/components/table';
import {compose, withError} from '~/shared/hoc';

const withTableData = (
	withData,
	{emptyDescription, emptyIcon, emptyTitle, getColumns, rowIdentifier}
) => {
	const TableWithData = compose(
		withData(),
		(WrappedComponent) => (props) => (
			<WrappedComponent {...props} columns={getColumns(props)} />
		),
		withError({page: false}),
		withEmpty({emptyDescription, emptyIcon, emptyTitle, spacer: true})
	)(Table);

	class TableData extends React.Component {
		render() {
			return (
				<TableWithData {...this.props} rowIdentifier={rowIdentifier} />
			);
		}
	}

	return TableData;
};

export default withTableData;
