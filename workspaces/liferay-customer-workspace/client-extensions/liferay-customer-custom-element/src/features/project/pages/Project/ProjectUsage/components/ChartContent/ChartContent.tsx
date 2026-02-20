/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';

import './ChartContent.css';

import classNames from 'classnames';
import {Cell, Pie, PieChart, ResponsiveContainer, Text} from 'recharts';

import {IChartData} from '../../hooks/useProjectUsageData';

interface ICustomLabelProps {
	cx: number;
	cy: number;
	name: string;
}

const CustomLabel: React.FC<ICustomLabelProps> = ({
	cx,
	cy,
	name,
}: ICustomLabelProps) => {
	if (!name) {
		return <></>;
	}

	return (
		<Text
			className="h4"
			dominantBaseline="central"
			fill="black"
			fontSize={24}
			textAnchor="middle"
			x={cx}
			y={cy}
		>
			{name}
		</Text>
	);
};

type IChartContentProps = Omit<IChartData, 'infoText'> & {
	displayUsage?: boolean;
};

const ChartContent: React.FC<IChartContentProps> = ({
	displayUsage,
	maxCount,
	maxCountText,
	maxCountUnits = '',
	percentage,
	title,
	usedCount,
	usedCountUnits = '',
}) => {
	const chartData = useMemo(() => {
		let consumedValue = Math.random() * 100;
		let chartLegend = '##';

		if (displayUsage) {
			if (percentage !== undefined) {
				const percentageValue = parseFloat(percentage);

				consumedValue = percentageValue >= 100 ? 100 : percentageValue;
				chartLegend = usedCount?.toLocaleString() + usedCountUnits;
			}
			else {
				consumedValue = 0;
				chartLegend = '-';
			}
		}

		const emptySpace = 100 - consumedValue;

		return [
			{
				name: chartLegend,
				value: consumedValue,
			},
			{name: '', value: emptySpace},
		];
	}, [usedCount, usedCountUnits, displayUsage, percentage]);

	return (
		<div className="align-items-center chart-content d-flex w-100">
			<div className="mr-3" style={{height: 140, width: '50%'}}>
				<ResponsiveContainer height="100%" width="100%">
					<PieChart>
						<Pie
							data={[{value: 100}]}
							dataKey="value"
							endAngle={470}
							innerRadius="71%"
							isAnimationActive={false}
							outerRadius="100%"
							startAngle={90}
							stroke="none"
						>
							<Cell fill="#EDEDED" />
						</Pie>

						<Pie
							data={chartData}
							dataKey="value"
							endAngle={470}
							innerRadius="71%"
							label={CustomLabel}
							labelLine={false}
							outerRadius="100%"
							startAngle={90}
							stroke="none"
						>
							{chartData.map((item, index) => (
								<Cell
									fill={!index ? '#377CFF' : 'transparent'}
									key={item.name}
									radius={20}
								/>
							))}
						</Pie>
					</PieChart>
				</ResponsiveContainer>
			</div>

			<div className="m-0">
				<h5 className="chart-title mb-3">{title}</h5>

				<h5
					className={classNames('m-0', {
						row: !displayUsage,
					})}
				>
					<span
						className={classNames('chart-max-text mr-3', {
							'col empty-text': !displayUsage,
						})}
					>
						{displayUsage && (maxCountText || '-')}
					</span>

					<span
						className={classNames({
							'col empty-text': !displayUsage,
						})}
					>
						{displayUsage &&
							(maxCount !== undefined
								? maxCount.toLocaleString() + maxCountUnits
								: '-')}
					</span>
				</h5>
			</div>
		</div>
	);
};

export default ChartContent;
