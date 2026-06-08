/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';
import {Sankey as SankeyChart, Tooltip as RechartsTooltip} from 'recharts';
import {RangeSelectors} from '~/shared/types';

import {Link} from './Link';
import {Node} from './Node';
import {Tooltip} from './Tooltip';
import {MAIN_NODE_WIDTH, SANKEY_HEIGHT} from './utils';

interface ISankeyProps {
	data: any;
	rangeSelectors: RangeSelectors;
	width: number;
}

const Sankey: React.FC<ISankeyProps> = ({data, rangeSelectors, width}) => {
	const [hovered, setMouseEnter] = useState(false);
	const [selectedNode, setSelectedNode] = useState(null);

	return (
		<SankeyChart
			className="analytics-sankey"
			data={data}
			height={SANKEY_HEIGHT}
			link={
				<Link
					hovered={hovered}
					onNodeChange={setSelectedNode}
					selectedNode={selectedNode}
				/>
			}
			linkCurvature={0.3}
			margin={{bottom: 30, right: 20, top: 60}}
			node={
				<Node
					hovered={hovered}
					onNodeChange={setSelectedNode}
					rangeSelectors={rangeSelectors}
					selectedNode={selectedNode}
				/>
			}
			nodePadding={80}
			nodeWidth={MAIN_NODE_WIDTH}
			onMouseEnter={() => {
				setMouseEnter(true);
			}}
			onMouseLeave={() => {
				setMouseEnter(false);
			}}
			sort={false}
			width={width}
		>
			<RechartsTooltip
				allowEscapeViewBox={{x: true, y: true}}
				content={<Tooltip />}
			/>
		</SankeyChart>
	);
};

export default Sankey;
