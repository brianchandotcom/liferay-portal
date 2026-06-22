import getCN from 'classnames';
import React from 'react';
import {formatValue, getRegexType} from './util';
import {MetricType} from './metrics';

interface IMetricValueProps extends React.HTMLAttributes<HTMLDivElement> {
	type?: MetricType;
	value: string;
}

const MetricValue: React.FC<IMetricValueProps> = ({
	className,
	type = MetricType.Number,
	value
}) => (
	<div className={getCN('metric-value', className)}>
		{formatValue(value, getRegexType(type))}
	</div>
);

export default MetricValue;
