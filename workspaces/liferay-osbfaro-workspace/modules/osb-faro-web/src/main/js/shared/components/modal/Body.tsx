/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';

interface IBodyProps extends React.HTMLAttributes<HTMLDivElement> {
	inlineScroller?: boolean;
}

const Body: React.FC<IBodyProps> = ({
	children,
	className,
	inlineScroller = false,
}) => (
	<div
		className={getCN('modal-body', className, {
			'inline-scroller': inlineScroller,
		})}
	>
		{children}
	</div>
);

export default Body;
