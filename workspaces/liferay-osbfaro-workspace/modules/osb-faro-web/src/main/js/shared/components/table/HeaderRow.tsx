/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderedMap} from 'immutable';
import React from 'react';
import {getFieldNameFromAccessor} from '~/shared/util/pagination';
import {OrderParams} from '~/shared/util/records';

import HeaderCell from './HeaderCell';
import {Column} from './Row';

interface IHeaderRowProps {
	className?: string;
	columns: Column[];
	headerLink?: boolean;
	onSortOrderChange: (orderParams: OrderParams) => void;
	orderIOMap?: OrderedMap<string, OrderParams>;
	showCheckbox?: boolean;
	showInlineRowActions?: boolean;
}

const HeaderRow: React.FC<IHeaderRowProps> = ({
	className,
	columns,
	headerLink,
	onSortOrderChange,
	orderIOMap = OrderedMap<string, OrderParams>(),
	showCheckbox,
	showInlineRowActions,
}) => (
	<thead>
		<tr className={className}>
			{showCheckbox && <th />}

			{columns.map((column, i) => {
				const {
					accessor,
					className,
					headProps = {},
					label,
					sortable,
				} = column;

				const field = getFieldNameFromAccessor(accessor);

				const {sortOrder} = orderIOMap.get(field, new OrderParams());

				return (
					<HeaderCell
						className={className}
						field={field}
						headerLink={headerLink}
						key={`${label}-${i}`}
						onSortOrderChange={onSortOrderChange}
						sortOrder={sortOrder}
						sortable={sortable}
						{...headProps}
					>
						{label}
					</HeaderCell>
				);
			})}

			{showInlineRowActions && <th />}
		</tr>
	</thead>
);

export default HeaderRow;
