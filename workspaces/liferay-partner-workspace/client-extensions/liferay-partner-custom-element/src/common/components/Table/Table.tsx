/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';

import TableColumn from '../../interfaces/tableColumn';

interface TableProps<T> {
	className?: string;
	columns: TableColumn<T>[];
	customClickOnRow?: (row: T) => void;
	rows: T[];
}

const Table = <T extends unknown>({
	className,
	columns,
	customClickOnRow,
	rows,
}: TableProps<T>) => (
	<ClayTable
		borderless
		className={className}
		noWrap
		responsive
		tableVerticalAlignment="middle"
	>
		<ClayTable.Head>
			<ClayTable.Row>
				{columns.map((column: TableColumn<T>, index: number) => (
					<ClayTable.Cell
						align="left"
						className="align-baseline border-neutral-2 rounded-0"
						headingCell
						key={index}
						truncate
					>
						<p className="mt-4 text-neutral-10">{column.label}</p>
					</ClayTable.Cell>
				))}
			</ClayTable.Row>
		</ClayTable.Head>

		<ClayTable.Body>
			{rows.map((row, rowIndex) => (
				<ClayTable.Row key={rowIndex}>
					{columns.map((column, colIndex) => {
						const data: any = row[column.columnKey as keyof T];

						return (
							<ClayTable.Cell
								align="left"
								className="border-0 font-weight-normal py-4 text-neutral-10"
								headingCell
								key={colIndex}
								noWrap
								onClick={() => {
									if (customClickOnRow) {
										return customClickOnRow(row);
									}
								}}
								truncate
							>
								{column.render
									? column.render(data, row, rowIndex)
									: data}
							</ClayTable.Cell>
						);
					})}
				</ClayTable.Row>
			))}
		</ClayTable.Body>
	</ClayTable>
);

export default Table;
