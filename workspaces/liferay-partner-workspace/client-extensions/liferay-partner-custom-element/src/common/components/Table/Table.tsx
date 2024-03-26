/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	Body,
	Cell,
	Head,
	Row as ClayRow,
	Table as ClayTable,
} from '@clayui/core';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';

import './index.css';
import TableColumn from '../../interfaces/tableColumn';

type TableLayout = 'auto' | 'fixed';

interface BasicRow {
	[key: string]: string | number | boolean | string[] | undefined;
}

interface TableProps<T> {
	className?: string;
	columns: TableColumn<T>[];
	customClickOnRow?: (item: T) => void;
	rows: T[];
	tableLayout?: TableLayout;
}

interface RowProps<T> {
	columns: TableColumn<T>[];
	customClickOnRow?: (item: T) => void;
	row: T;
}

type ChildrenRender<T> = ((item: T) => React.ReactElement) & string;

const Row = <T extends BasicRow>({
	columns,
	customClickOnRow,
	row,
}: RowProps<T>) => {
	const id = Math.random().toString(16).slice(2);

	return (
		<ClayRow className="border-0 font-weight-normal" items={columns}>
			{
				((column) => {
					const data = row[column.columnKey];

					return (
						<Cell
							className="py-4"
							key={id + ':' + column.columnKey}
							onClick={() => {
								if (customClickOnRow) {
									return customClickOnRow(row);
								}
							}}
						>
							{column.render ? (
								column.render(data as T[keyof T], row, 0)
							) : (
								<span
									className={classNames('table-cell-items', {
										'text-ellipsis-lg':
											column.size === 'lg',
										'text-ellipsis-md':
											column.size === 'md',
										'text-ellipsis-sm':
											column.size === 'sm',
										'text-wrap': column.wrap,
									})}
									data-tooltip-align="top"
									title={data as string}
								>
									{data}
								</span>
							)}
						</Cell>
					);
				}) as ChildrenRender<TableColumn<T>>
			}
		</ClayRow>
	);
};

const Table = <T extends BasicRow>({
	className,
	columns,
	customClickOnRow,
	rows,
	tableLayout,
}: TableProps<T>) => {
	return (
		<ClayTooltipProvider>
			<ClayTable
				borderless
				className={classNames(className, {
					'table-layout-auto': tableLayout === 'auto',
					'table-layout-fixed': tableLayout === 'fixed',
				})}
				columnsVisibility={false}
				noWrap
			>
				<Head align="left" items={columns}>
					{
						((column) => (
							<Cell
								className="align-baseline border-neutral-2 rounded-0"
								key={column.columnKey}
							>
								{column.label}
							</Cell>
						)) as ChildrenRender<TableColumn<T>>
					}
				</Head>

				<Body align="left" defaultItems={rows}>
					{
						((row) => (
							<Row
								columns={columns}
								customClickOnRow={customClickOnRow}
								row={row}
							/>
						)) as ChildrenRender<T>
					}
				</Body>
			</ClayTable>
		</ClayTooltipProvider>
	);
};

export default Table;
