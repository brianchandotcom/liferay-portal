/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

interface ICellProps {
	children: React.ReactNode;
	className?: string;
	title?: boolean;
}

const Cell: React.FC<ICellProps> = ({children, className, title}) => (
	<td className={className}>
		{title ? (
			<div className="h4 table-title text-truncate">{children}</div>
		) : (
			children
		)}
	</td>
);

export default Cell;
