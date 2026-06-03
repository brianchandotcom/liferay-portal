import React from 'react';
import {MAIN_NODE_HEIGHT, MAIN_NODE_WIDTH} from './utils';
import {Node} from './Node';
import {RangeSelectors} from 'shared/types';
import {Tooltip as RechartsTooltip, Sankey as SankeyChart} from 'recharts';
import {Tooltip} from './Tooltip';

interface IEmptySankeyProps {
	data: any;
	emptyState: boolean;
	rangeSelectors: RangeSelectors;
}

export const EmptySankey: React.FC<IEmptySankeyProps> = ({
	data,
	emptyState,
	rangeSelectors
}) => {
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
