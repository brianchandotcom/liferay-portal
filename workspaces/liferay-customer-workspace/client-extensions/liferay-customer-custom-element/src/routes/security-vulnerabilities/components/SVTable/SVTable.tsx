/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import {Link, useNavigate} from 'react-router-dom';

import './SVTable.css';

import React from 'react';

export interface IColumn {
	columnKey: string;
	label: string;
}

export interface IRow {
	[key: string]: string | number | JSX.Element | undefined;
}

interface IProps {
	columns: IColumn[];
	rows: IRow[];
}

const SVTable = ({columns, rows}: IProps) => {
	const navigate = useNavigate();

	return (
		<ClayTable borderless className="sv-table table" noWrap striped={false}>
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
				{rows.map((row, index) => {
					let navigationLink = '#';
					const prioritySummaryElement = row['prioritySummary'];

					if (React.isValidElement(prioritySummaryElement)) {
						const prioritySummaryDiv =
							prioritySummaryElement as React.ReactElement;

						if (
							prioritySummaryDiv.type === 'div' &&
							prioritySummaryDiv.props.children
						) {
							const linkChild =
								prioritySummaryDiv.props.children[0].props
									.children[1].props.children;

							if (React.isValidElement(linkChild)) {
								const linkElement =
									linkChild as React.ReactElement;

								if (
									linkElement.type === Link &&
									linkElement.props.to
								) {
									navigationLink = linkElement.props.to;
								}
							}
						}
					}

					return (
						<ClayTable.Row
							className="sv-row"
							key={index}
							onClick={() => navigate(navigationLink)}
						>
							{columns.map((column) => (
								<ClayTable.Cell key={column.columnKey}>
									{row[column.columnKey]}
								</ClayTable.Cell>
							))}
						</ClayTable.Row>
					);
				})}
			</ClayTable.Body>
		</ClayTable>
	);
};

export default SVTable;
