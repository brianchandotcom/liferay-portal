/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';

interface ICircleProps extends React.HTMLAttributes<HTMLElement> {
	color?: string;
	size?: number;
}

const Circle: React.FC<ICircleProps> = ({
	children,
	className,
	color,
	size = 8,
}) => (
	<span
		className={getCN('circle', className)}
		style={{
			backgroundColor: color,
			height: `${size}px`,
			width: `${size}px`,
		}}
	>
		{children}
	</span>
);

export default Circle;
