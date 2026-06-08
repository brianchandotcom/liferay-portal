/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';

interface IBodyProps extends React.HTMLAttributes<HTMLElement> {
	disabled?: boolean;
	fluid?: boolean;
	pageContainer?: boolean;
	sidebarOpened?: boolean;
}

const Body: React.FC<IBodyProps> = ({
	children,
	className,
	disabled,
	fluid = false,
	pageContainer = true,
	sidebarOpened = false,
}) => {
	if (fluid) {
		return (
			<div
				className={getCN('fluid-body-root', className, {
					disabled,
					'sidebar-opened': sidebarOpened,
				})}
			>
				{children}
			</div>
		);
	}

	return (
		<div
			className={getCN(
				'body-root',
				{
					disabled,
					'page-container': pageContainer,
					'sidebar-opened': sidebarOpened,
				},
				className
			)}
		>
			<span className="children-wrapper">{children}</span>
		</div>
	);
};

export default Body;
