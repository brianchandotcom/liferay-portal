/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import {get} from 'lodash';
import {PropTypes} from 'prop-types';
import React from 'react';
import {
	Cell,
	Legend,
	Pie,
	PieChart,
	ResponsiveContainer,
	Sector,
	Tooltip,
} from 'recharts';
import TextTruncate from '~/shared/components/TextTruncate';
import {Colors} from '~/shared/util/charts';
import {toRounded, toThousands} from '~/shared/util/numbers';
import {getPercentage} from '~/shared/util/util';

import ChartTooltip from '../../shared/components/chart-tooltip';

const CLASSNAME = 'analytics-web-browser-chart';

const getChartPercentage = (value, total) =>
	`${toRounded(getPercentage(value, total))}%`;

/**
 * Web Browser
 */
class WebBrowser extends React.Component {
	static defaultProps = {
		browsers: [],
		height: 370,
	};

	static propTypes = {
		browsers: PropTypes.array,
		height: PropTypes.number,
	};

	state = {
		hoverIndex: -1,
	};
	@autobind
	renderActiveShape({
		cx,
		cy,
		endAngle,
		fill,
		innerRadius,
		outerRadius,
		startAngle,
	}) {
		return (
			<g>
				<Sector
					cx={cx}
					cy={cy}
					endAngle={endAngle}
					fill={fill}
					innerRadius={innerRadius}
					outerRadius={outerRadius + 4}
					startAngle={startAngle}
				/>
			</g>
		);
	}

	@autobind
	renderTooltip({active, payload}) {
		const {metricLabel, total} = this.props;

		if (active && payload && !!payload.length) {
			const {value, valueKey} = get(payload, [0, 'payload'], {});

			return (
				<div
					className="bb-tooltip-container"
					style={{position: 'static'}}
				>
					<ChartTooltip
						header={[
							{
								columns: [
									{
										label: valueKey,
									},
									{
										label: '',
									},
								],
							},
						]}
						rows={[
							{
								columns: [
									{
										label: `${toThousands(
											value
										)} ${metricLabel}`,
										width: 120,
									},
									{
										align: 'right',
										label: getChartPercentage(value, total),
										weight: 'semibold',
										width: 50,
									},
								],
							},
						]}
					/>
				</div>
			);
		}

		return null;
	}

	render() {
		const {
			props: {browsers, height, total},
			state: {hoverIndex},
		} = this;

		return (
			<div className={CLASSNAME}>
				<ResponsiveContainer height={height}>
					<PieChart>
						<Tooltip content={this.renderTooltip} />

						<Legend
							align="right"
							formatter={(val, {payload: {value, valueKey}}) => (
								<>
									<TextTruncate
										inline
										maxCharLength={24}
										title={valueKey}
									/>

									<span className="legend-percentage">
										{getChartPercentage(value, total)}
									</span>
								</>
							)}
							iconSize={14}
							layout="vertical"
							onMouseMove={(event, index) =>
								this.setState({hoverIndex: index})
							}
							onMouseOut={() => this.setState({hoverIndex: -1})}
							verticalAlign="middle"
						/>

						<Pie
							activeIndex={hoverIndex}
							activeShape={this.renderActiveShape}
							blendStroke
							className="col-7"
							cy={185}
							data={browsers}
							dataKey="value"
							endAngle={-270}
							innerRadius="50%"
							isAnimationActive={false}
							legendType="circle"
							onMouseMove={(event, index) =>
								this.setState({hoverIndex: index})
							}
							onMouseOut={() => this.setState({hoverIndex: -1})}
							startAngle={90}
						>
							{browsers.map((browser, index) => (
								<Cell
									fill={Colors.pallete[index]}
									fillOpacity={
										hoverIndex >= 0 && hoverIndex !== index
											? 0.2
											: 1
									}
									key={`cell-${index}`}
									strokeOpacity={
										hoverIndex >= 0 && hoverIndex !== index
											? 0
											: 1
									}
								/>
							))}
						</Pie>
					</PieChart>
				</ResponsiveContainer>
			</div>
		);
	}
}

export {WebBrowser};
export default WebBrowser;
