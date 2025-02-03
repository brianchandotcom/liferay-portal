/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';

export interface IColumn {
	columnKey: string;
	label: string;
	subLabel?: string;
}

export interface IRow {
	link?: string;
	[key: string]: string | number | JSX.Element | undefined;
}

interface IProps {
	className: string;
	columns: IColumn[];
	onRowClick?: (row: IRow) => void;
	rows: IRow[];
}

const DataTable = ({className, columns, onRowClick, rows}: IProps) => {
	return (
		<ClayTable
			borderless
			className={`${className}-table ${className}-structured-data table`}
			noWrap
			striped={false}
		>
			<ClayTable.Head align="left">
				<ClayTable.Row>
					{columns.map((column) => (
						<ClayTable.Cell
							className="font-weight-semi-bold text-neutral-10"
							key={column.columnKey}
						>
							<div>
								<div className="be-header-label">
									{column.label}
								</div>

								{column.subLabel && (
									<div className="be-header-sub-label color-neutral-7">
										{column.subLabel}
									</div>
								)}
							</div>
						</ClayTable.Cell>
					))}
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body align="left">
				{rows.map((row, index) => (
					<ClayTable.Row
						className={`${className}-row`}
						key={index}
						onClick={() => onRowClick && onRowClick(row)}
					>
						{columns.map((column) => (
							<ClayTable.Cell key={column.columnKey}>
								{row[column.columnKey]}
							</ClayTable.Cell>
						))}
					</ClayTable.Row>
				))}
			</ClayTable.Body>
		</ClayTable>
	);
};

export default DataTable;
