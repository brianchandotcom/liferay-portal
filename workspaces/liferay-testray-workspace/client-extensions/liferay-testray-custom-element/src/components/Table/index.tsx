/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import React, {useState} from 'react';
import {Link} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import i18n from '~/i18n';

import {Sort} from '../../context/ListViewContext';
import Permission from '../../core/Permission';
import useContextMenu from '../../hooks/useContextMenu';
import {APIResponse} from '../../services/rest';
import {Action, SortDirection, SortOption} from '../../types';
import ContextMenu from '../ContextMenu';

export type Column<T = any> = {
	clickable?: boolean;
	key: string;
	render?: (
		itemValue: any,
		item: T,
		mutate: KeyedMutator<APIResponse<T>>
	) => String | React.ReactNode;
	selectable?: boolean;
	size?: 'sm' | 'md' | 'lg' | 'xl' | 'none';
	sorteable?: boolean;
	truncate?: boolean;
	value: string;
	width?: '50' | '75' | '100' | '200' | '250' | '300' | '350' | '400';
};

export type TableProps<T = any> = {
	actions?: Action[];
	allRowsChecked?: boolean;
	bodyVerticalAlignment?: 'bottom' | 'middle' | 'top';
	columns?: Column<T>[];
	highlight?: (item: T) => boolean;
	items: T[];
	mutate: KeyedMutator<T>;
	navigateTo?: (item: T) => string;
	normalizers: {
		onSelectRow: (item: any) => any;
	};
	onClickRow?: (item: T) => void;
	onSelectAllRows: () => void;
	onSelectRow?: (row: any) => void;
	onSort: (columnTable: string, direction: SortDirection) => void;
	responsive?: boolean;
	rowSelectable?: boolean;
	rowWrap?: boolean;
	selectedRows?: number[];
	sort?: Sort | Sort[];
};

const Table: React.FC<TableProps> = ({
	actions,
	allRowsChecked = false,
	bodyVerticalAlignment = 'middle',
	columns,
	highlight,
	items,
	mutate,
	navigateTo,
	normalizers,
	onClickRow,
	onSelectAllRows,
	onSelectRow,
	onSort,
	responsive,
	rowSelectable = false,
	rowWrap = false,
	selectedRows = [],
	sort,
}) => {
	const [firstRowAction] = items;

	const filteredActions = actions
		? Permission.filterActions(actions, firstRowAction?.actions)
		: [];

	const displayActionColumn = !!filteredActions.length;

	const {contextMenuState, handleContext, setContextMenuState} =
		useContextMenu(displayActionColumn);

	const [sorted, setSorted] = useState<SortDirection>(SortOption.ASC);

	const changeSort = (key: string) => {
		onSort(key, sorted);
		setSorted(
			sorted === SortOption.DESC ? SortOption.ASC : SortOption.DESC
		);
	};

	const getSortSymbol = (key: string) => {
		if (!sort) {
			return '';
		}

		let selectedSort = sort;

		if (Array.isArray(selectedSort)) {
			selectedSort =
				selectedSort.find((_sort) => _sort.key === key) || ({} as Sort);
		}

		if (selectedSort?.key === key) {
			return selectedSort.direction === SortOption.ASC
				? 'caret-top-l'
				: 'caret-bottom-l';
		}

		return 'caret-double-l';
	};

	return (
		<>
			<ClayTable
				borderless
				className="tr-table"
				hover
				responsive={responsive}
				striped={false}
				tableVerticalAlignment={bodyVerticalAlignment}
			>
				<ClayTable.Head>
					<ClayTable.Row>
						{rowSelectable && (
							<ClayTable.Cell>
								<ClayCheckbox
									checked={allRowsChecked}
									onChange={() => {
										onSelectAllRows();
									}}
								/>
							</ClayTable.Cell>
						)}

						{columns?.map((column, index) => (
							<ClayTable.Cell headingTitle key={index}>
								<span className="d-flex justify-content-between">
									<span
										className={classNames({
											'cursor-pointer': column.sorteable,
										})}
										onClick={() => {
											if (column.sorteable) {
												changeSort(column.key);
											}
										}}
									>
										{column.value}
									</span>

									{column.sorteable && (
										<ClayIcon
											className="cursor-pointer"
											onClick={() =>
												changeSort(column.key)
											}
											symbol={getSortSymbol(column.key)}
										/>
									)}
								</span>
							</ClayTable.Cell>
						))}
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{items.map((item, rowIndex) => (
						<ClayTable.Row
							active={
								rowIndex === contextMenuState.rowIndex &&
								contextMenuState.visible
							}
							className={classNames('tr-table__row', {
								'text-nowrap': !rowWrap,
								'text-wrap': rowWrap,
								'tr-table__row--highligth':
									highlight && highlight(item),
							})}
							key={rowIndex}
							onContextMenu={(event) => {
								if (displayActionColumn) {
									handleContext({
										actions: filteredActions,
										event,
										item,
										rowIndex,
									});
								}
							}}
						>
							{rowSelectable && onSelectRow && (
								<ClayTable.Cell>
									<ClayCheckbox
										aria-label={columns
											?.map((column) => {
												const getValue = (
													value: any
												) => {
													if (
														React.isValidElement(
															value
														)
													) {
														return (
															value?.props as any
														)?.children;
													}

													return value;
												};

												const value = column.render
													? getValue(
															column.render(
																item[
																	column.key
																],
																{
																	...item,
																	rowIndex,
																},
																mutate
															)
														)
													: item[column.key];

												return `${column.value}: ${
													value ??
													i18n.translate('empty')
												}`;
											})
											.join(', ')}
										checked={selectedRows.includes(
											normalizers?.onSelectRow(item)
										)}
										onChange={() => onSelectRow(item)}
									/>
								</ClayTable.Cell>
							)}

							{columns?.map((column, columnIndex) => {
								const Wrapper =
									column.selectable ||
									(column.clickable && navigateTo)
										? Link
										: (props: any) => <div {...props} />;

								return (
									<ClayTable.Cell
										className={classNames('text-dark', {
											'cursor-pointer': column.clickable,
											[`table-cell-minw-${column.width}`]:
												column.width,
											'table-cell-expand':
												column.size === 'sm',
											'table-cell-expand-small':
												column.size === 'xl',
											'table-cell-expand-smaller':
												column.size === 'lg',
											'table-cell-expand-smallest':
												column.size === 'md',
										})}
										expanded={column.truncate}
										key={columnIndex}
										onClick={() => {
											if (onClickRow) {
												onClickRow(item);
											}
										}}
										truncate={column.truncate}
									>
										<Wrapper
											className="text-dark"
											to={
												navigateTo?.(
													item
												)?.toString() as string
											}
										>
											{column.render
												? column.render(
														item[column.key],
														{...item, rowIndex},
														mutate
													)
												: item[column.key]}
										</Wrapper>
									</ClayTable.Cell>
								);
							})}
						</ClayTable.Row>
					))}
				</ClayTable.Body>
			</ClayTable>

			{displayActionColumn && contextMenuState.visible && (
				<ContextMenu
					contextMenuState={contextMenuState}
					mutate={mutate}
					setContextMenuState={setContextMenuState}
				/>
			)}
		</>
	);
};

export default Table;
