/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';

import './ChartContent.css';

import classNames from 'classnames';
import {Cell, Pie, PieChart, Text} from 'recharts';

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
	dataSizeUnits = '',
	displayUsage,
	maxCount = 0,
	maxCountText,
	title,
	usedCount = 0,
}) => {
	const chartData = useMemo(() => {
		let consumedValue = Math.random() * 100;
		let chartLegend = '##';

		if (displayUsage) {
			consumedValue = (usedCount / maxCount) * 100;
			chartLegend = usedCount.toLocaleString() + dataSizeUnits;
		}

		const emptySpace = 100 - consumedValue;

		return [
			{
				name: chartLegend,
				value: consumedValue,
			},
			{name: '', value: emptySpace},
		];
	}, [usedCount, dataSizeUnits, displayUsage, maxCount]);

	return (
		<div className="align-items-center chart-content d-flex w-100">
			<PieChart className="mr-3" height={140} width={140}>
				<Pie
					data={chartData}
					dataKey="value"
					endAngle={470}
					innerRadius={50}
					label={CustomLabel}
					labelLine={false}
					outerRadius={70}
					startAngle={90}
					stroke="none"
				>
					{chartData.map((item, index) => (
						<Cell
							fill={!index ? '#377CFF' : '#EDEDED'}
							key={item.name}
							radius={20}
						/>
					))}
				</Pie>
			</PieChart>

			<p className="m-0">
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
						{displayUsage && `Total ${maxCountText}`}
					</span>

					<span
						className={classNames({
							'col empty-text': !displayUsage,
						})}
					>
						{displayUsage && maxCount + dataSizeUnits}
					</span>
				</h5>
			</p>
		</div>
	);
};

export default ChartContent;
