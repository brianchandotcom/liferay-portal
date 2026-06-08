/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';

import {MetricType} from './metrics';
import {formatValue, getRegexType} from './util';

interface IMetricValueProps extends React.HTMLAttributes<HTMLDivElement> {
	type?: MetricType;
	value: string;
}

const MetricValue: React.FC<IMetricValueProps> = ({
	className,
	type = MetricType.Number,
	value,
}) => (
	<div className={getCN('metric-value', className)}>
		{formatValue(value, getRegexType(type))}
	</div>
);

export default MetricValue;
