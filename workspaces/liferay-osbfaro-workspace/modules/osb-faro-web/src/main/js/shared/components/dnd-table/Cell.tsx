/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import {get, isNil} from 'lodash';
import React from 'react';
import {IDataColumn} from '~/shared/types';

interface ICellProps extends IDataColumn {
	data: any;
}

const Cell: React.FC<ICellProps> = ({
	accessor,
	cellRenderer: CellRenderer,
	cellRendererProps,
	className,
	data,
	dataFormatter = (val) => val,
}) => {
	if (CellRenderer) {
		return (
			<CellRenderer
				{...cellRendererProps}
				className={className}
				data={data}
			/>
		);
	}

	const dataValue = get(data, accessor);

	return (
		<ClayTable.Cell>
			{!isNil(dataValue) ? dataFormatter(dataValue, data) : '-'}
		</ClayTable.Cell>
	);
};

export default Cell;
