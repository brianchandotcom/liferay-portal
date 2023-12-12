/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode, forwardRef} from 'react';

export type ALIGN_POSITIONS =
	| 'bottom-left'
	| 'bottom-right'
	| 'bottom'
	| 'left'
	| 'right'
	| 'top-left'
	| 'top-right'
	| 'top';

type TooltipProps = {
	children: ReactNode;
	className?: string;
	position?: ALIGN_POSITIONS;
	ref?: React.ForwardedRef<HTMLDivElement>;
	title?: string;
};

const Tooltip: React.FC<TooltipProps> = forwardRef(
	({children, className, position = 'top', title}, ref) => (
		<div
			className={className}
			data-tooltip-align={position}
			ref={ref}
			title={title}
		>
			{children}
		</div>
	)
);

export default Tooltip;
