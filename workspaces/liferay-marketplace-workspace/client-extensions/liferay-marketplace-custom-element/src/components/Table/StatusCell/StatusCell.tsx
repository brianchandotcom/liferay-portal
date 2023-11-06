/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React, {ReactNode} from 'react';

import './StatusCell.scss';

type StatusCellProps = {
	children: ReactNode;
	className?: string;
	icon: string;
	iconClass?: string;
};

export default function StatusCell({
	children,
	className,
	icon,
	iconClass,
}: StatusCellProps) {
	return (
		<div className={`align-itens-center d-flex status-cell ${className}`}>
			<div className="align-items-center d-flex">
				<ClayIcon
					className={`mr-2 status-cell-icon ${iconClass}`}
					symbol={icon}
				/>
			</div>

			{children}
		</div>
	);
}
