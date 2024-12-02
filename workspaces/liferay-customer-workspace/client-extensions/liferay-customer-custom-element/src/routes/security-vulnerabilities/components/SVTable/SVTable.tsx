/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';

import './SVTable.css';

export interface IColumn {
	columnKey: string;
	label: string;
}

export interface IRow {
	[key: string]: string | number | JSX.Element | undefined;
}

interface IProps {
	columns: IColumn[];
	rows: IRow[];
}

const SVTable = ({columns, rows}: IProps) => {
	return (
		<ClayTable borderless className="sv-table table" noWrap striped={false}>
			<ClayTable.Head align="left">
				<ClayTable.Row>
					{columns.map((column) => (
						<ClayTable.Cell
							className="text-neutral-10"
							key={column.columnKey}
						>
							{column.label}
						</ClayTable.Cell>
					))}
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body align="left">
				{rows.map((row, index) => (
					<ClayTable.Row key={index}>
						{columns.map((column) => (
							<ClayTable.Cell key={column.columnKey}>
								{column.columnKey === 'prioritySummary'
									? row[column.columnKey]
									: row[column.columnKey]}
							</ClayTable.Cell>
						))}
					</ClayTable.Row>
				))}
			</ClayTable.Body>
		</ClayTable>
	);
};

export default SVTable;
