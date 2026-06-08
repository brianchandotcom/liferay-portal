/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {round} from 'lodash';
import React, {FC} from 'react';
import MetricBar, {Sizes} from '~/shared/components/MetricBar';
import TextTruncate from '~/shared/components/TextTruncate';

interface IRelativeMetricBarProps extends React.HTMLAttributes<HTMLElement> {
	data: {
		count: number;
		name: string;
	};
	empty?: boolean;
	maxCount?: number;
	showName?: boolean;
	total?: number;
	totalCount?: number;
}

const RelativeMetricBar: FC<IRelativeMetricBarProps> = ({
	data: {count, name},
	empty = false,
	showName = false,
	totalCount,
}) => {
	const percent = totalCount ? round(count / totalCount, 2) : 0;

	const displayName = showName ? name : '';

	return (
		<td className="relative-metric-bar-root table-cell-expand">
			<MetricBar percent={percent} size={Sizes.Lg}>
				<TextTruncate className="title" title={displayName} />

				{!empty && <span className="count">{count}</span>}
			</MetricBar>
		</td>
	);
};

export default RelativeMetricBar;
