/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Item from './Item';

export type CohortHeatMapType = {
	colorHex: string | null;
	date: string;
	dateLabelFn: (date: string, showYear: boolean) => string;
	periodLabel: string | string[];
	retention: number;
	value: number;
};

interface ICohortChartProps {
	aggregatedCounts: CohortHeatMapType[];
	data: CohortHeatMapType[][];
	dateLabels: string[];
	periodLabels: Array<string | string[]>;
}

export default class CohortChart extends React.Component<ICohortChartProps> {
	renderAggregated() {
		const {aggregatedCounts} = this.props;

		const aggregatedVisitorsCount = aggregatedCounts[0].value;

		return (
			<tr>
				<th />

				<th className="table-column-text-end visitors">
					{aggregatedVisitorsCount.toLocaleString()}
				</th>

				{aggregatedCounts.map(({retention}, i) => (
					<th className="table-column-text-center" key={i}>
						{`${retention.toFixed(2)}%`}
					</th>
				))}
			</tr>
		);
	}

	renderPeriods() {
		const {periodLabels} = this.props;

		return (
			<tr>
				<th />

				<th className="table-column-text-end visitors-header">
					{Liferay.Language.get('visitors')}
				</th>

				{periodLabels.map((periodLabel, index) => (
					<th className="period table-column-text-center" key={index}>
						{periodLabel}
					</th>
				))}
			</tr>
		);
	}

	renderRow(row: CohortHeatMapType[], rowIndex: number) {
		const {dateLabels} = this.props;

		const rowVisitorsCount = row[0].value;

		return (
			<tr key={rowIndex}>
				<td className="interval">{dateLabels[rowIndex]}</td>

				<td className="table-column-text-end visitors">
					{rowVisitorsCount.toLocaleString()}
				</td>

				{row.map(
					(
						{
							colorHex,
							date,
							dateLabelFn,
							periodLabel,
							retention,
							value,
						},
						i
					) => (
						<Item
							colorHex={colorHex}
							date={date}
							dateLabelFn={dateLabelFn}
							key={`${rowIndex}-${i}`}
							periodLabel={periodLabel}
							retention={retention}
							value={value}
						/>
					)
				)}
			</tr>
		);
	}

	render() {
		const {data} = this.props;

		return (
			<table className="cohort-chart-root">
				<thead>
					{this.renderPeriods()}

					{this.renderAggregated()}
				</thead>

				<tbody>{data.map((row, i) => this.renderRow(row, i))}</tbody>
			</table>
		);
	}
}
