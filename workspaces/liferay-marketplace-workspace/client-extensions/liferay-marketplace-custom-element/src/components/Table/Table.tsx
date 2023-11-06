/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayTable from '@clayui/table';
import {ReactNode} from 'react';

import './Table.scss';

type TableProps = {
	className?: string;
	columns: TableColumn[];
	hasKebabButton?: boolean;
	hasPagination?: boolean;
	isLoading?: boolean;
	rows: TableRow[];
};

type TableColumn = {
	align?: 'center' | 'left' | 'right';
	bodyClass?: string;
	columnTextAlignment?: 'center' | 'end' | 'start';
	disableCustomClickOnRow?: boolean;
	expanded?: boolean;
	header: {
		description?: string;
		name: string;
		noWrap?: boolean;
		styles?: string;
	};
	id: string;
	noWrap?: boolean;
	truncate?: boolean;
};

type TableRow = {
	columns: {
		[keys: string]: {
			description?: string;
			value: ReactNode;
		};
	};
	id: string;
	onClickRow?: () => void;
};

export default function Table({
	className,
	columns,
	hasKebabButton,
	isLoading,
	rows,
}: TableProps) {
	return (
		<>
			<ClayTable borderless className={`table ${className}`}>
				<ClayTable.Head>
					<ClayTable.Row className="border-bottom header-row">
						{columns.map((column) => (
							<ClayTable.Cell
								align={column.align}
								className={
									column.header.styles ||
									'bg-transparent font-weight-bold'
								}
								headingCell
								key={column.id}
								noWrap={column.header.noWrap}
							>
								{column.header.description ? (
									<>
										<p className="description-title m-0">
											{column.header.name}
										</p>

										<p className="description m-0">
											{column.header.description}
										</p>
									</>
								) : (
									<span className="description-title">
										{column.header.name}
									</span>
								)}
							</ClayTable.Cell>
						))}

						{hasKebabButton && <ClayTable.Cell></ClayTable.Cell>}
					</ClayTable.Row>
				</ClayTable.Head>
				{!isLoading && (
					<ClayTable.Body className="table-body">
						{rows.map((row, rowIndex) => (
							<ClayTable.Row key={row.id || rowIndex}>
								{columns.map((column, columnIndex) => (
									<ClayTable.Cell
										align={column.align}
										className={column.bodyClass}
										columnTextAlignment={
											column.columnTextAlignment
										}
										expanded={column.expanded}
										key={`${rowIndex}-${columnIndex}`}
										noWrap={column.noWrap}
										onClick={() => {
											if (
												!column.disableCustomClickOnRow &&
												row.onClickRow
											) {
												return row.onClickRow();
											}
										}}
										truncate={column.truncate}
									>
										{row.columns[column.id].description ? (
											<>
												<p className="description-title m-0">
													{
														row.columns[column.id]
															?.value
													}
												</p>

												<p className="description m-0">
													{
														row.columns[column.id]
															?.description
													}
												</p>
											</>
										) : (
											row.columns[column.id]?.value
										)}
									</ClayTable.Cell>
								))}

								{hasKebabButton && (
									<ClayTable.Cell
										className="border-0"
										columnTextAlignment="center"
									>
										<ClayButtonWithIcon
											aria-label="Menu"
											displayType={null}
											onClick={() => {}}
											symbol="ellipsis-v"
											title="Menu"
										/>
									</ClayTable.Cell>
								)}
							</ClayTable.Row>
						))}
					</ClayTable.Body>
				)}
			</ClayTable>
		</>
	);
}
