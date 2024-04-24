/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Body, Cell, Head, Row, Table as ClayTable} from '@clayui/core';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';

import TableColumn from '../../interfaces/tableColumn';

import './index.css';

interface BasicRow {
	[key: string]: string | number | boolean | string[] | undefined;
}

interface TableProps<T> {
	className?: string;
	columns: TableColumn<T>[];
	customClickOnRow?: (item: T) => void;
	layoutAuto: boolean;
	rows: T[];
}

type ChildrenRender<T> = ((item: T) => React.ReactElement) & string;

const Table = <T extends BasicRow>({
	className,
	columns,
	customClickOnRow,
	layoutAuto,
	rows,
}: TableProps<T>) => (
	<ClayTooltipProvider>
		<ClayTable
			borderless
			className={classNames(className, {
				'table-layout-auto': layoutAuto,
			})}
			columnsVisibility={false}
			noWrap
		>
			<Head align="left" items={columns}>
				{
					((column) => (
						<Cell
							className="align-baseline border-neutral-2 rounded-0 text-neutral-10"
							key={column.columnKey}
						>
							{column.label}
						</Cell>
					)) as ChildrenRender<TableColumn<T>>
				}
			</Head>

			<Body align="left">
				{rows.map((row, rowIndex) => (
					<Row
						key={rowIndex}
						onClick={() => {
							if (customClickOnRow) {
								return customClickOnRow(row);
							}
						}}
					>
						{columns.map((column, colIndex) => {
							const data: any = row[column.columnKey as keyof T];

							return (
								<Cell
									align="left"
									className="border-0 font-weight-normal py-4 table-cell"
									key={`${rowIndex}-${colIndex}`}
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
								</Cell>
							);
						})}
					</Row>
				))}
			</Body>
		</ClayTable>
	</ClayTooltipProvider>
);

export default Table;
