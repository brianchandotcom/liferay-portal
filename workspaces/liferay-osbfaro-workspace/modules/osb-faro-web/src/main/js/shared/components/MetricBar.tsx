/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {round} from 'lodash';
import React from 'react';

export enum Displays {
	Danger = 'danger',
	Primary = 'primary',
	Warning = 'warning',
}

export enum Sizes {
	Xs = 'xs',
	Sm = 'sm',
	Md = 'md',
	Lg = 'lg',
	Default = 'default',
}

interface IMetricBarProps extends React.HTMLAttributes<HTMLElement> {
	barClassName?: string;
	barStyle?: {[key: string]: string};
	display?: Displays;
	percent: number;
	size: Sizes;
}

const MetricBar: React.FC<IMetricBarProps> = ({
	barClassName,
	barStyle = {},
	children,
	className,
	display,
	percent = 0,
	size = Sizes.Default,
}) => {
	const barClasses = getCN('bar', barClassName, {
		[`bar-${display}`]: display,
		[`bar-${size}`]: size,
	});

	return (
		<div className={getCN('metric-bar-root', className)}>
			<div
				className={barClasses}
				style={{width: `${round(percent * 100)}%`, ...barStyle}}
			/>

			{children && (
				<div className="align-items-center d-flex info-wrapper justify-content-between">
					{children}
				</div>
			)}
		</div>
	);
};

export default MetricBar;
