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

import TableColumn from '../../interfaces/tableColumn';

interface BasicRow {
	[key: string]: string | number | boolean | string[] | undefined;
}

interface TableProps<T> {
	className?: string;
	columns: TableColumn<T>[];
	customClickOnRow?: (item: T) => void;
	rows: T[];
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
		<ClayRow items={columns}>
			{
				((column) => {
					const data = row[column.columnKey];

					return (
						<Cell
							key={id + ":" + column.columnKey}
							onClick={() => {
								if (customClickOnRow) {
									return customClickOnRow(row);
								}
							}}
						>
							{column.render
								? column.render(data as T[keyof T], row, 0)
								: data}
						</Cell>
					);
				}) as ChildrenRender<TableColumn<T>>
			}
		</ClayRow>
	);
};

const PRMTable = <T extends BasicRow>({
	className,
	columns,
	customClickOnRow,
	rows,
}: TableProps<T>) => {
	return (
		<ClayTable
			borderless
			className={className}
			columnsVisibility={false}
			size="sm"
		>
			<Head items={columns}>
				{
					((column) => (
						<Cell key={column.columnKey}>{column.label}</Cell>
					)) as ChildrenRender<TableColumn<T>>
				}
			</Head>

			<Body defaultItems={rows}>
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
	);
};

export default PRMTable;
