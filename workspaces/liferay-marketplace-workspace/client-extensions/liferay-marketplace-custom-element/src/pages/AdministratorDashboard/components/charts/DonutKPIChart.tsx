/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayIcon from '@clayui/icon';
import Label from '@clayui/label';
import React from 'react';
import {useNavigate} from 'react-router-dom';
import {Cell, Pie, PieChart, ResponsiveContainer} from 'recharts';

import Loading from '../../../../components/Loading';
import i18n from '../../../../i18n';
import useKPI from '../../hooks/useKPI';

import './DonutKPIChart.scss';

const getPercentage = (total: number, partial: number): number => {
	if (!total) {
		return 0;
	}

	return Math.round((partial / total) * 100);
};

interface DonutKPIChartProps {
	chartData: NonNullable<ReturnType<typeof useKPI>['data']>[number];
	isLoading: boolean;
}

const DonutKPIChart: React.FC<DonutKPIChartProps> = ({
	chartData,
	isLoading,
}) => {
	const percentage = getPercentage(
		Number(chartData.annualTargetTotal),
		chartData.annualTargetCurrent
	);

	const data = [
		{name: 'filed', value: percentage},
		{name: 'remainder', value: 100 - percentage},
	];

	return (
		<div className="border d-inline-flex flex-column justify-content-between marketplace-donut-chart p-5">
			<div className="align-items-start d-flex flex-row justify-content-between">
				<span className="font-weight-bold mr-3 text-wrap">
					{chartData.title}
				</span>

				{chartData.onClick && (
					<span className="align-items-center d-flex justify-content-center">
						<Button
							className="details-button rounded-lg text-nowrap"
							displayType="secondary"
							onClick={chartData.onClick}
							size="sm"
						>
							{i18n.translate('view-details')}
						</Button>
					</span>
				)}
			</div>

			{isLoading ? (
				<div className="align-items-center d-flex flex-column h-100 justify-content-center">
					<Loading
						className="loading"
						displayType="primary"
						size="md"
					/>
				</div>
			) : (
				<div className="align-items-center d-flex flex-row justify-content-between mt-3">
					<div className="donut-chart-container">
						<ResponsiveContainer>
							<PieChart tabIndex={-1}>
								<Pie
									cornerRadius={0}
									data={data}
									dataKey="value"
									endAngle={-270}
									innerRadius={40}
									outerRadius={80}
									paddingAngle={0}
									startAngle={90}
								>
									{data.map((_: any, index: number) => (
										<Cell
											fill={chartData.colors[index]}
											key={index}
										>
											{index}
										</Cell>
									))}
								</Pie>

								<text
									className="marketplace-donut-chart-center-legend"
									dominantBaseline="middle"
									textAnchor="middle"
									x="52%"
									y="52%"
								>
									<tspan fontSize="44">{percentage}</tspan>
									<tspan fontSize="14">%</tspan>
								</text>
							</PieChart>
						</ResponsiveContainer>
					</div>

					<div className="chart-legend d-flex flex-column">
						<div className="d-flex flex-column mb-3">
							<span className="font-weight-bold text-small">
								{i18n.translate('annual-target')}
							</span>

							<div className="align-items-center d-flex flex-row">
								<div className="align-items-center d-flex font-weight-bold justify-content-center text-title">
									<span className="chart-legend-data mr-1">
										{chartData.annualTargetCurrent || 0}
									</span>
									/
									<span className="mx-1">
										{chartData.annualTargetTotal || 0}
									</span>
								</div>
								<span className="text-small">
									{i18n.translate('of-target')}
								</span>
							</div>
						</div>

						{!!chartData.monthlyIncreaseValue && (
							<div className="d-flex flex-column">
								<span className="font-weight-bold text-small">
									{i18n.translate('monthly-increase')}
								</span>

								<div className="align-items-center d-flex flex-row">
									<div className="font-weight-bold mr-3 text-title">
										{`${chartData.monthlyIncreaseValueIsGrowing ? '+' : '-'}${chartData.monthlyIncreaseValue}`}
									</div>

									<Label
										displayType={
											chartData.monthlyIncreaseValueIsGrowing
												? 'success'
												: 'danger'
										}
									>
										<span className="d-flex lign-items-center">
											<ClayIcon
												symbol={
													chartData.monthlyIncreaseValueIsGrowing
														? 'order-arrow-up'
														: 'order-arrow-donw'
												}
											/>
											<span className="ml-1">{`${chartData.monthlyIncreasePct} % `}</span>
										</span>
									</Label>
								</div>
							</div>
						)}
					</div>
				</div>
			)}
		</div>
	);
};

export default DonutKPIChart;
