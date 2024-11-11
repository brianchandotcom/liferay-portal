/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Body, Cell, Head, Row, Table} from '@clayui/core';

import './SVTable.css';

interface IColumn {
	columnKey: string;
	label: string;
}

interface IRow {
	[key: string]: string | number | JSX.Element | undefined;
}

interface IProps {
	columns: IColumn[];
	rows: IRow[];
}

const SVTable = ({columns, rows}: IProps) => {
	return (
		<Table
			borderless
			className="sv-table table"
			columnsVisibility={false}
			noWrap
			striped={false}
		>
			<Head align="left" items={columns}>
				{columns.map((column) => (
					<Cell className="text-neutral-10" key={column.columnKey}>
						{column.label}
					</Cell>
				))}
			</Head>

			<Body align="left" defaultItems={rows}>
				{rows.map((row, index) => (
					<Row key={index}>
						{columns.map((column) => (
							<Cell key={column.columnKey}>
								{row[column.columnKey]}
							</Cell>
						))}
					</Row>
				))}
			</Body>
		</Table>
	);
};

export default SVTable;
