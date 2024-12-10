/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import {useNavigate} from 'react-router-dom';

import './SVTable.css';

import React from 'react';

export interface IColumn {
	columnKey: string;
	label: string;
}

export interface IRow {
	link?: string;
	[key: string]: string | number | JSX.Element | undefined;
}

interface IProps {
	columns: IColumn[];
	rows: IRow[];
}

const SVTable = ({columns, rows}: IProps) => {
	const navigate = useNavigate();

	return (
		<ClayTable
			borderless
			className="sv-structured-data sv-table table"
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
							{column.label}
						</ClayTable.Cell>
					))}
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body align="left">
				{rows.map((row, index) => (
					<ClayTable.Row
						className="sv-row"
						key={index}
						onClick={() => row.link && navigate(row.link)}
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

export default SVTable;
