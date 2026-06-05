import getCN from 'classnames';
import React from 'react';
import {round} from 'lodash';

export enum Displays {
	Danger = 'danger',
	Primary = 'primary',
	Warning = 'warning'
}

export enum Sizes {
	Xs = 'xs',
	Sm = 'sm',
	Md = 'md',
	Lg = 'lg',
	Default = 'default'
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
	size = Sizes.Default
}) => {
	const barClasses = getCN('bar', barClassName, {
		[`bar-${display}`]: display,
		[`bar-${size}`]: size
	});

	return (
		<div className={getCN('metric-bar-root', className)}>
			<div
				className={barClasses}
				style={{width: `${round(percent * 100)}%`, ...barStyle}}
			/>

			{children && (
				<div className='info-wrapper align-items-center d-flex justify-content-between'>
					{children}
				</div>
			)}
		</div>
	);
};

export default MetricBar;
