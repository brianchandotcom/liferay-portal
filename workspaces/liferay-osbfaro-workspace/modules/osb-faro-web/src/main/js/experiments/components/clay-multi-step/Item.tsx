/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import getCN from 'classnames';
import React from 'react';

type Body = {
	status: string;
};

interface ClayMultiStepItemIProps
	extends React.LiHTMLAttributes<HTMLLIElement> {
	Body?: React.FC<Body>;
	lastChild?: boolean;
	showIndicatorLabel?: boolean;
	status?: string;
	stepNumber?: string;
	title?: string;
}

const ClayMultiStepItem: React.FC<ClayMultiStepItemIProps> = ({
	Body,
	lastChild,
	showIndicatorLabel,
	status,
	stepNumber,
	title,
}) => {
	const mainClassName = getCN(status, 'multi-step-item', {
		'multi-step-item-expand': !lastChild,
	});

	return (
		<li className={mainClassName}>
			{title && <div className="multi-step-title">{title}</div>}
			{!lastChild && <div className="multi-step-divider"></div>}
			<div className="multi-step-indicator">
				{showIndicatorLabel && (
					<div className="multi-step-indicator-label">
						{stepNumber}
					</div>
				)}
				<ClayLink
					className="multi-step-icon"
					data-multi-step-icon={stepNumber}
					href={`#${stepNumber}`}
				></ClayLink>
			</div>

			{Body && <Body status={status ?? ''} />}
		</li>
	);
};

export default ClayMultiStepItem;
