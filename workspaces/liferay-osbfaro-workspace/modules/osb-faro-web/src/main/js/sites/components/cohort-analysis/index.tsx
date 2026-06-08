/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {get} from 'lodash';
import React from 'react';
import CohortChart, {CohortHeatMapType} from '~/shared/components/cohort-chart';

import {
	IntervalType,
	VisitorsType,
	formatDate,
	getColorHex,
	getPeriodLabel,
} from './utils';

type RawHeatMapType = {
	colDimension: string;
	retention: number;
	rowDimension: string;
	rowKey: string;
	value: number;
};

interface ICohortCardProps {
	data: {
		anonymousVisitors: {
			items: RawHeatMapType[];
		};
		knownVisitors: {
			items: RawHeatMapType[];
		};
		visitors: {
			items: RawHeatMapType[];
		};
	};
	interval: IntervalType;
	visitorsType: VisitorsType;
}

type FormattedHeatMap = Record<string, CohortHeatMapType[]>;

export default class CohortCard extends React.Component<ICohortCardProps> {
	formatCohortHeatMap(items: RawHeatMapType[]): FormattedHeatMap {
		const {interval, visitorsType} = this.props;

		return items.reduce<FormattedHeatMap>(
			(acc, {colDimension, retention, rowDimension, rowKey, value}) => {
				const period = parseInt(colDimension);

				const item: CohortHeatMapType = {
					colorHex:
						period !== 0
							? getColorHex(retention, visitorsType)
							: null,
					date: rowKey,
					dateLabelFn: (date: string, abbreviated: boolean) =>
						formatDate(date, interval, abbreviated),
					periodLabel: getPeriodLabel(period, interval),
					retention,
					value,
				};

				const row = get(acc, rowDimension, [] as CohortHeatMapType[]);

				acc[rowDimension] = row.concat(item);

				return acc;
			},
			{}
		);
	}

	getAggregatedCounts(formattedData: CohortHeatMapType[][]) {
		return formattedData[0];
	}

	getDateLabels(formattedData: CohortHeatMapType[][]) {
		return formattedData.map((row) => {
			const {date, dateLabelFn} = row[0];

			return dateLabelFn(date, false);
		});
	}

	getHeatMapData(formattedData: FormattedHeatMap): CohortHeatMapType[][] {
		return Object.values(formattedData).slice(1) as CohortHeatMapType[][];
	}

	render() {
		const {data, visitorsType} = this.props;

		const {items} = data[visitorsType];

		const formattedData = this.formatCohortHeatMap(items);

		const heatMapData = this.getHeatMapData(formattedData);

		const aggregatedCounts = this.getAggregatedCounts(heatMapData);

		return (
			<CohortChart
				aggregatedCounts={aggregatedCounts}
				data={heatMapData}
				dateLabels={this.getDateLabels(heatMapData)}
				periodLabels={aggregatedCounts.map(
					({periodLabel}: CohortHeatMapType) => periodLabel
				)}
			/>
		);
	}
}
