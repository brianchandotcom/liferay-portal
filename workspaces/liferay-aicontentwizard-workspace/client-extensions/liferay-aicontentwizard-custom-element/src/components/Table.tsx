/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayTable from '@clayui/table';
import {ReactElement} from 'react';

type TableProps = {
	actions: any[];
	columns: any[];
	emptyState: ReactElement;
	rows: any[];
};

export default function Table({
	actions,
	columns,
	emptyState,
	rows,
}: TableProps) {
	if (!rows.length) {
		return emptyState;
	}

	return (
		<ClayTable>
			<ClayTable.Head>
				{columns.map((column, index) => (
					<ClayTable.Cell headingCell key={index}>
						{column.name}
					</ClayTable.Cell>
				))}
				{!!actions.length && (
					<ClayTable.Cell headingCell></ClayTable.Cell>
				)}
			</ClayTable.Head>

			<ClayTable.Body>
				{rows.map((row, rowIndex) => (
					<ClayTable.Row key={rowIndex}>
						{columns.map((column, index) => (
							<ClayTable.Cell key={index}>
								{column.render
									? column.render(row[column.key], row)
									: row[column.key]}
							</ClayTable.Cell>
						))}

						{!!actions.length && (
							<ClayTable.Cell>
								<div className="d-flex justify-content-end">
									<ClayDropDownWithItems
										items={actions.map((action) => ({
											...action,
											onClick: () => action?.onClick(row),
										}))}
										trigger={
											<ClayButtonWithIcon
												aria-label="Row Actions"
												displayType="secondary"
												size="sm"
												symbol="ellipsis-v"
											/>
										}
									/>
								</div>
							</ClayTable.Cell>
						)}
					</ClayTable.Row>
				))}
			</ClayTable.Body>
		</ClayTable>
	);
}
