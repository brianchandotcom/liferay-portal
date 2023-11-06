/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {ReactNode} from 'react';

type StatusCellProps = {
	children: ReactNode;
	className?: string;
	icon: string;
	iconClassName?: string;
};

const StatusCell: React.FC<StatusCellProps> = ({
	children,
	className,
	icon,
	iconClassName,
}) => (
	<div
		className={classNames(
			'd-flex align-items-center status-cell',
			className
		)}
	>
		<div className="align-items-center d-flex">
			<ClayIcon
				className={classNames(
					'status-cell-icon mr-2 mt-0 ',
					iconClassName
				)}
				fontSize={8}
				symbol={icon}
			/>
		</div>
		<span>{children}</span>
	</div>
);

export default StatusCell;
