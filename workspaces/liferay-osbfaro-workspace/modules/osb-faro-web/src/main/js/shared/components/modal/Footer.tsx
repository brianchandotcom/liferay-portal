/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';

interface IFooterProps extends React.HTMLAttributes<HTMLDivElement> {
	border?: boolean;
}

const Footer: React.FC<IFooterProps> = ({
	border = false,
	children,
	className,
}) => (
	<div className={getCN('modal-footer', className, {border})}>{children}</div>
);

export default Footer;
