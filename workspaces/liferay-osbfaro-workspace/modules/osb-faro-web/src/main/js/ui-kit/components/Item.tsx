/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';

const Item: React.FC<React.HTMLAttributes<HTMLElement>> = ({
	children,
	className,
}) => <div className={getCN('kit-item-root', className)}>{children}</div>;

export default Item;
