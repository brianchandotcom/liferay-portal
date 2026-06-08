/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Sankey as SankeyChart, Tooltip as RechartsTooltip} from 'recharts';
import {RangeSelectors} from '~/shared/types';

import {Node} from './Node';
import {Tooltip} from './Tooltip';
import {MAIN_NODE_HEIGHT, MAIN_NODE_WIDTH} from './utils';

interface IEmptySankeyProps {
	data: any;
	emptyState: boolean;
	rangeSelectors: RangeSelectors;
}

export const EmptySankey = function EmptySankey({
	data,
	emptyState,
	rangeSelectors,
}: IEmptySankeyProps) {
	const marginTop = 60;

	return (
		<SankeyChart
			data={data}
			height={MAIN_NODE_HEIGHT + marginTop}
			margin={{bottom: 30, right: 20, top: marginTop}}
			node={
				<Node emptyState={emptyState} rangeSelectors={rangeSelectors} />
			}
			nodePadding={80}
			nodeWidth={MAIN_NODE_WIDTH}
			width={MAIN_NODE_WIDTH}
		>
			<RechartsTooltip
				allowEscapeViewBox={{x: true, y: true}}
				content={<Tooltip />}
			/>
		</SankeyChart>
	);
};
