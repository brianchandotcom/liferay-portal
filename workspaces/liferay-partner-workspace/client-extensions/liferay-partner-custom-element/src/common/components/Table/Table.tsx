/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';

import TableColumn from '../../interfaces/tableColumn';

import './index.css';

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
	<ClayTooltipProvider>
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
						>
							{column.label instanceof String ? (
								<p className="mb-0 mt-4 text-neutral-10">
									{column.label}
								</p>
							) : (
								column.label
							)}
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
									className="border-0 font-weight-normal py-4 table-cell"
									headingCell
									key={colIndex}
									onClick={() => {
										if (customClickOnRow) {
											return customClickOnRow(row);
										}
									}}
								>
									{column.render ? (
										column.render(data, row, rowIndex)
									) : (
										<span
											className={classNames(
												'table-cell-items',
												{
													'text-ellipsis-lg':
														column.size === 'lg',
													'text-ellipsis-md':
														column.size === 'md',
													'text-ellipsis-sm':
														column.size === 'sm',
													'text-wrap': column.wrap,
												}
											)}
											data-tooltip-align="top"
											title={data}
										>
											{data}
										</span>
									)}
								</ClayTable.Cell>
							);
						})}
					</ClayTable.Row>
				))}
			</ClayTable.Body>
		</ClayTable>
	</ClayTooltipProvider>
);

export default Table;
