/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Children, cloneElement} from 'react';

import Item from './Item';
import {getStatus} from './utils';

type IndicatorLabel = 'bottom' | 'top';

interface ClayMultiStepIProps extends React.OlHTMLAttributes<HTMLOListElement> {
	current?: number;
	indicatorLabel?: IndicatorLabel;
	initial?: number;
	showIndicatorLabel?: boolean;
}

const ClayMultiStep: React.FC<ClayMultiStepIProps> & {
	Item: typeof Item;
} = ({
	children,
	current = 0,
	indicatorLabel = 'bottom',
	initial = 0,
	showIndicatorLabel = true,
	...otherProps
}) => {
	const filteredChildren = React.Children.toArray(children).filter(
		(c) => !!c
	);

	return (
		<ol
			{...otherProps}
			className={`multi-step-nav multi-step-indicator-label-${indicatorLabel}`}
		>
			{Children.map(filteredChildren, (child: any, index) => {
				if (!child) {
					return null;
				}

				const stepNumber = initial + index;
				const childProps = {
					lastChild: stepNumber === filteredChildren.length - 1,
					showIndicatorLabel,
					status: getStatus(stepNumber, current),
					stepNumber: `${stepNumber + 1}`,
					...child.props,
				};

				return cloneElement(child, childProps);
			})}
		</ol>
	);
};

ClayMultiStep.Item = Item;

export default ClayMultiStep;
