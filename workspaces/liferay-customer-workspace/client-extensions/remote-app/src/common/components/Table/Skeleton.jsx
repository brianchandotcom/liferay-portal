/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import Skeleton from '../Skeleton';

const TableSkeleton = ({hasCheckbox, totalColumns, totalItems}) => {
	return (
		<ClayTable.Body>
			{[...new Array(totalItems)].map((_, rowIndex) => (
				<ClayTable.Row key={rowIndex}>
					{hasCheckbox && (
						<ClayTable.Cell
							className="text-center"
							key={`checkbox-${rowIndex}`}
						>
							<input type="checkbox" />
						</ClayTable.Cell>
					)}

					{[...new Array(totalColumns)].map((_, cellIndex) => (
						<ClayTable.Cell
							align="center"
							expanded
							key={`table-${rowIndex}-${cellIndex}`}
						>
							<Skeleton className="w-100" height={24} />
						</ClayTable.Cell>
					))}
				</ClayTable.Row>
			))}
		</ClayTable.Body>
	);
};

export default TableSkeleton;
