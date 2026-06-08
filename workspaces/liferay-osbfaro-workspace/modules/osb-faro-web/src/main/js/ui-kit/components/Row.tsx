/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';

interface IRowProps extends React.HTMLAttributes<HTMLElement> {
	flex?: Boolean;
}

const Row: React.FC<IRowProps> = ({
	children,
	className,
	flex = false,
	...otherProps
}) => (
	<div
		{...otherProps}
		className={getCN('kit-row-root', className, {
			['d-flex flex-wrap']: flex,
		})}
	>
		{children}
	</div>
);

export default Row;
