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

import TableColumn from '../../interfaces/tableColumn';

import './index.css';

import {Dispatch, SetStateAction, useCallback, useState} from 'react';

import {SortableTable} from '../../enums/sortableTable';

interface BasicRow {
	[key: string]: string | number | boolean | string[] | undefined;
}

interface TableProps<T> {
	className?: string;
	columns: TableColumn<T>[];
	customClickOnRow?: (item: T) => void;
	rows: T[];
	setTableSort?: Dispatch<SetStateAction<string>>;
	sortable?: SortableTable[];
	tableLayoutAuto: boolean;
	tableSort?: string;
}

interface RowProps<T> {
	columns: TableColumn<T>[];
	customClickOnRow?: (item: T) => void;
	row: T;
	rowIndex: number;
}

type ChildrenRender<T> = ((item: T) => React.ReactElement) & string;

type Sorting = {
	column: React.Key;
	direction: 'ascending' | 'descending';
};

const Row = <T extends BasicRow>({
	columns,
	customClickOnRow,
	row,
	rowIndex,
}: RowProps<T>) => {
	const id = Math.random().toString(16).slice(2);

	return (
		<ClayRow
			className="border-0 font-weight-normal"
			items={columns}
			onClick={() => {
				if (customClickOnRow) {
					return customClickOnRow(row);
				}
			}}
		>
			{
				((column) => {
					const data = row[column.columnKey];

					return (
						<Cell
							className="py-4"
							key={id + ':' + column.columnKey}
						>
							{column.render ? (
								column.render(data as T[keyof T], row, rowIndex)
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
	setTableSort = () => {},
	sortable,
	tableLayoutAuto,
}: TableProps<T>) => {
	const [sort, setSort] = useState<Sorting | null>(null);

	const getSortString = (
		columnKey: string,
		prevSort: string,
		sortOrder: string
	) => {
		const columnMap: {[key: string]: string} = {
			ACCOUNT_NAME: 'prospectAccountName',
			CLAIM_STATUS: 'mdfClaimStatus',
			CLOSE_DATE: 'closeDate',
			DATE_SUBMITTED: 'submitDate',
			DEAL_DATE_SUBMITTED: 'dateCreated',
			END_ACT_PERIOD: 'maxDateActivity',
			OPPORTUNITY_ACCOUNT_NAME: 'accountName',
			PARTNER: 'companyName',
			PARTNER_ACCOUNT_NAME: 'partnerAccountName',
			PARTNER_NAME: 'partnerFirstName',
			REQUEST_STATUS: 'mdfRequestStatus',
			STAGE: 'stage',
			START_ACT_PERIOD: 'minDateActivity',
			TYPE: 'partial',
		};

		return columnMap[columnKey]
			? `${columnMap[columnKey]}:${sortOrder}`
			: prevSort;
	};

	const onSortChange = useCallback((sort: Sorting | null) => {
		setSort(sort);
	}, []);

	return (
		<ClayTooltipProvider>
			<ClayTable
				borderless
				className={classNames(className, {
					'table-layout-auto': tableLayoutAuto,
				})}
				columnsVisibility={false}
				noWrap
				onSortChange={onSortChange}
				sort={sort}
			>
				<Head align="left" items={columns}>
					{
						((column) => (
							<Cell
								className="align-baseline border-neutral-2 rounded-0 text-neutral-10"
								key={column.columnKey}
								onClickCapture={() => {
									setTableSort((prevSort: string) => {
										const sortOrder = prevSort.includes(
											'asc'
										)
											? 'desc'
											: 'asc';

										return getSortString(
											column.columnKey,
											prevSort,
											sortOrder
										);
									});
								}}
								sortable={
									sortable &&
									sortable?.some((item) =>
										column.columnKey.includes(item)
									)
								}
							>
								{column.label}
							</Cell>
						)) as ChildrenRender<TableColumn<T>>
					}
				</Head>

				<Body align="left" defaultItems={rows}>
					{
						((row: T, index: number) => {
							return (
								<Row
									columns={columns}
									customClickOnRow={customClickOnRow}
									row={row}
									rowIndex={index}
								/>
							);
						}) as ChildrenRender<T>
					}
				</Body>
			</ClayTable>
		</ClayTooltipProvider>
	);
};

export default Table;
