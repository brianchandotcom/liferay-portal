/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayTable from '@clayui/table';
import {ReactNode} from 'react';

import './Table.scss';

type TableProps<T = any> = {
	onClickRow?: (item: T) => void;
	className?: string;
	columns: TableColumn<T>[];
	hasKebabButton?: boolean;
	hasPagination?: boolean;
	rows: T[];
};

type TableColumn<T = any> = {
	align?: 'center' | 'left' | 'right';
	bodyClass?: string;
	onClick?: (item: T) => void;
	columnTextAlignment?: 'center' | 'end' | 'start';
	disableCustomClickOnRow?: boolean;
	expanded?: boolean;
	key: string;
	noWrap?: boolean;
	render?: (value: any, item: T) => ReactNode | string;
	styles?: string;
	title: ReactNode;
	truncate?: boolean;
};

const Table: React.FC<TableProps> = ({
	className,
	onClickRow = () => {},
	columns,
	hasKebabButton,
	rows,
}) => (
	<ClayTable borderless className={className}>
		<ClayTable.Head>
			<ClayTable.Row className="border-bottom header-row">
				{columns.map((column, index) => (
					<ClayTable.Cell
						align={column.align}
						className="bg-transparent font-weight-bold"
						headingCell
						key={index}
						noWrap={column.noWrap}
					>
						{column.title}
					</ClayTable.Cell>
				))}

				{hasKebabButton && <ClayTable.Cell />}
			</ClayTable.Row>
		</ClayTable.Head>

		<ClayTable.Body className="table-body">
			{rows.map((row, rowIndex) => (
				<ClayTable.Row key={row.id || rowIndex} onClick={onClickRow}>
					{columns.map((column, columnIndex) => {
						const data = row[column.key];

						const value = column.render
							? column.render(data, {
									...row,
									rowIndex,
							  })
							: data;

						return (
							<ClayTable.Cell
								align={column.align}
								className={column.bodyClass}
								columnTextAlignment={column.columnTextAlignment}
								expanded={column.expanded}
								key={`${rowIndex}-${columnIndex}`}
								noWrap={column.noWrap}
								onClick={() => {
									if (column.onClick) {
										return row.onClick(row);
									}
								}}
								truncate={column.truncate}
							>
								{value}
							</ClayTable.Cell>
						);
					})}

					{hasKebabButton && (
						<ClayTable.Cell
							className="border-0"
							columnTextAlignment="center"
						>
							<ClayButtonWithIcon
								aria-label="Menu"
								displayType={null}
								symbol="ellipsis-v"
								title="Menu"
							/>
						</ClayTable.Cell>
					)}
				</ClayTable.Row>
			))}
		</ClayTable.Body>
	</ClayTable>
);

export default Table;
