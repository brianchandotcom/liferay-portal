/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {get, last} from 'lodash';
import React, {Fragment} from 'react';
import Trend from '~/shared/components/Trend';
import {Interval, RangeSelectors} from '~/shared/types';
import {
	CHART_COLOR_NAMES,
	getAxisFormatter,
	getDataFormatter,
	getIntervals,
	getMetricFormatter,
} from '~/shared/util/charts';
import {toUnix} from '~/shared/util/date';
import {getIcon, getStatsColor} from '~/shared/util/metrics';
import {toRounded} from '~/shared/util/numbers';
import {INTERVAL_KEY_MAP} from '~/shared/util/time';

import MetricValue from './MetricValue';
import {Metric, MetricType} from './metrics';

export const CHART_DATA_ID_1 = 'data_1';
export const CHART_DATA_ID_2 = 'data_2';
export const CHART_DATA_PREVIOUS = 'data_previous';

const PREVIOUS_PERIOD_VISITORS_COLOR = '#393A4A';

export const METRIC_TOOLTIP_LABEL_MAP: Record<string, string> = {
	bounceRateMetric: Liferay.Language.get('avg-bounce'),
};

const {
	martell: CHART_GREEN,
	martellL2: CHART_GREEN_L2,
	mormont: CHART_ORANGE,
	stark: CHART_BLUE,
	starkL2: CHART_BLUE_L2,
} = CHART_COLOR_NAMES;

export const Icons = {
	negative: 'caret-bottom-l',
	neutral: undefined,
	positive: 'caret-top-l',
};

type THistogramItem = {
	key: string;
	previousValue: number;
	previousValueKey: string;
	value: number;
	valueKey: string;
};

type TMetricResultItem = {
	histogram: {
		asymmetricComparison?: boolean;
		metrics: THistogramItem[];
	};
	trend: {
		percentage: number;
		trendClassification: string;
	};
	value: number;
};

type TMetricsResult = Record<string, TMetricResultItem>;

type TChartDataSet = {
	color?: string;
	data: any;
	dataName?: string;
	id: string;
	name?: string;
	tooltipTitle?: string;
	type?: string;
};

type TTabContent = {
	details: {
		color: string;
		icon?: string;
		label: string;
	};
	name: string;
	title: string;
	type: MetricType;
	value: string;
};

type TTabItem = {
	content: TTabContent;
};

type TTab = {
	onClick: () => void;
	secondaryInfo: React.ReactElement;
	tabId: number;
	title: string;
};

export const buildTabs = function buildTabs({
	activeItemIndex,
	items,
	onActiveItemIndexChange,
}: {
	activeItemIndex: number;
	items: TTabItem[];
	onActiveItemIndexChange: (index: number) => void;
}): TTab[] {
	return items.map(({content}, index) => {
		const {details, title, type, value} = content;
		const {color, icon, label} = details;

		return {
			onClick: () => {
				if (activeItemIndex !== index) {
					onActiveItemIndexChange(index);
				}
			},
			secondaryInfo: (
				<span>
					<span className="primary-content">
						<MetricValue type={type} value={value} />
					</span>

					{label && <Trend color={color} icon={icon} label={label} />}
				</span>
			),
			tabId: index,
			title,
		};
	});
};

export const getMetricName = function getMetricName(
	activeItemIndex: number,
	metrics: Metric[]
) {
	return metrics.map(({name}) => name)[activeItemIndex];
};

export const getActiveItem = function getActiveItem(
	retVal: any,
	compareToPrevious: boolean
) {
	if (!retVal) {
		return {
			chartData: [],
			intervals: [],
			timeline: [],
		};
	}

	const chartData = retVal.data.slice(0, -1);
	const timeline = last(retVal.data);

	if (!compareToPrevious && retVal.asymmetricComparison) {
		retVal = {
			...retVal,
			chartData: chartData.map((dataSet: TChartDataSet) => ({
				...dataSet,
				data: dataSet.data.slice(1),
			})),
			intervals: retVal.intervals.slice(1),
			timeline: {
				data: (timeline as TChartDataSet).data.slice(1),
				id: (timeline as TChartDataSet).id,
			},
		};

		if (retVal.compositeData) {
			const compositeDataKeys = Object.keys(retVal.compositeData);

			retVal = {
				...retVal,
				compositeData: compositeDataKeys.reduce(
					(acc: Record<string, any>, val) => {
						acc[val] = retVal.compositeData[val].slice(1);

						return acc;
					},
					{}
				),
			};
		}
	}
	else if (compareToPrevious && retVal.asymmetricComparison) {
		retVal = {
			...retVal,
			chartData: chartData.map((dataSet: TChartDataSet) => ({
				...dataSet,
				data:
					dataSet.id !== CHART_DATA_PREVIOUS
						? [null, ...dataSet.data.slice(1)]
						: dataSet.data,
			})),
			timeline,
		};
	}
	else {
		retVal = {
			...retVal,
			chartData,
			timeline,
		};
	}

	return retVal;
};

export const getPreviousValueFromCompositeData =
	function getPreviousValueFromCompositeData(
		compositeData: Record<string, any> | undefined,
		dataName: string | undefined,
		dateKey: number
	) {
		const data = get(compositeData, dataName as string);

		if (data) {
			return data.find(
				(val: {key: string}) => toUnix(val.key) === dateKey
			)?.previousValue;
		}
	};

export const getRegexType = function getRegexType(type: MetricType): RegExp {
	return type === MetricType.Ratings ? /([/][0-9]+)/g : /([a-zA-Z%])+/g;
};

export const formatValue = function formatValue(
	value: string,
	regex: RegExp
): React.ReactElement[] {
	return value.split(' ').map((item, i) => {
		const [head, unit] = item.split(regex);

		return (
			<Fragment key={i}>
				{head}

				<span className="metric-value-letter">{unit}</span>
			</Fragment>
		);
	});
};

export const getMetricCardTabsData = function getMetricCardTabsData(
	result: TMetricsResult,
	metrics: Metric[]
): TTabItem[] {
	return metrics.map(({name, title, type}) => {
		const metricFormatter = getMetricFormatter(type);
		const {percentage, trendClassification} = result[name].trend;

		return {
			content: {
				details: {
					color: getStatsColor(trendClassification),
					icon: getIcon(percentage),
					label: `${toRounded(Math.abs(percentage))}%`,
				},
				name,
				title,
				type,
				value: metricFormatter(result[name].value),
			},
		};
	});
};

export const convertHistogramKeysToDate = function convertHistogramKeysToDate({
	key,
	previousValueKey,
	valueKey,
	...otherParams
}: {
	key: string;
	previousValueKey: string;
	valueKey: string;
	[k: string]: any;
}) {
	return {
		key: toUnix(key),
		previousValueKey: previousValueKey.split('/').map(toUnix),
		valueKey: valueKey.split('/').map(toUnix),
		...otherParams,
	};
};

export const getMetricsChartData = function getMetricsChartData({
	histogram,
	name,
	title,
	tooltipTitle,
	type,
}: {
	histogram: Array<{
		key: string;
		previousValue: number;
		value: number;
	}>;
	name: string;
	title: string;
	tooltipTitle?: string;
	type: MetricType;
}): TChartDataSet[] {
	const formatter = getDataFormatter(type);

	return [
		{
			color: CHART_BLUE,
			data: formatter(histogram.map(({value}) => value)),
			id: CHART_DATA_ID_1,
			name: tooltipTitle || METRIC_TOOLTIP_LABEL_MAP[name] || title,
			tooltipTitle,
		},
		{
			color: CHART_BLUE_L2,
			data: formatter(histogram.map(({previousValue}) => previousValue)),
			id: CHART_DATA_PREVIOUS,
			name: Liferay.Language.get('previous-period'),
		},
		{
			data: histogram.map(({key}) => key),
			id: 'x',
		},
	];
};

const buildCompositeData = (
	compositeMetrics: Metric[],
	result: TMetricsResult
) => {
	const compositeContent = compositeMetrics.reduce(
		(acc: Record<string, any>, {name, title, type}) => {
			const metricFormatter = getMetricFormatter(type);
			const {percentage, trendClassification} = result[name].trend;

			acc[name] = {
				details: {
					color: getStatsColor(trendClassification),
					icon: getIcon(percentage),
					label: `${toRounded(Math.abs(percentage))}%`,
				},
				name,
				title,
				type,
				value: metricFormatter(result[name].value),
			};

			return acc;
		},
		{}
	);

	const compositeData = compositeMetrics.reduce(
		(acc: Record<string, any>, {name}) => {
			acc[name] = result[name].histogram.metrics;

			return acc;
		},
		{}
	);

	return {compositeContent, compositeData};
};

export const getMetricData = function getMetricData({
	chartDataMapFn = getMetricsChartData,
	compositeMetrics,
	interval = INTERVAL_KEY_MAP.day,
	name,
	rangeSelectors,
	result,
	title,
	tooltipTitle,
	type,
}: {
	chartDataMapFn?: (...args: any[]) => any;
	compositeMetrics?: Metric[];
	interval?: string;
	name: string;
	rangeSelectors: Partial<RangeSelectors>;
	result: TMetricsResult;
	title: string;
	tooltipTitle?: string;
	type: MetricType;
}) {
	const metricFormatter = getMetricFormatter(type);

	const histogram = result[name].histogram.metrics.map(
		convertHistogramKeysToDate
	);

	const compositeMeta = compositeMetrics
		? buildCompositeData(compositeMetrics, result)
		: {};

	const dateKeysIMap = new Map(
		histogram.map(({key, valueKey}) => [key, valueKey])
	);

	const {percentage, trendClassification} = result[name].trend;

	return {
		...compositeMeta,
		asymmetricComparison: result[name].histogram.asymmetricComparison,
		content: {
			details: {
				color: getStatsColor(trendClassification),
				icon: getIcon(percentage),
				label: `${toRounded(Math.abs(percentage))}%`,
			},
			name,
			title,
			type,
			value: metricFormatter(result[name].value),
		},
		data: chartDataMapFn({
			...compositeMeta,
			histogram,
			name,
			title,
			tooltipTitle,
			type,
		}),
		dateKeysIMap,
		format: getAxisFormatter(type),
		intervals: getIntervals(
			rangeSelectors.rangeKey as RangeSelectors['rangeKey'],
			histogram.map(({key}) => key),
			interval as Interval,
			dateKeysIMap
		),
		prevDateKeysIMap: new Map(
			histogram.map(({key, previousValueKey}) => [key, previousValueKey])
		),
	};
};

export const getMetricsData = function getMetricsData(
	result: TMetricsResult,
	metrics: Metric[],
	rangeSelectors: Partial<RangeSelectors> = {},
	chartDataMapFn: (...args: any[]) => any = getMetricsChartData,
	interval: string = INTERVAL_KEY_MAP.day
) {
	return metrics.map(({compositeMetrics, name, title, tooltipTitle, type}) =>
		getMetricData({
			chartDataMapFn,
			compositeMetrics,
			interval,
			name,
			rangeSelectors,
			result,
			title,
			tooltipTitle,
			type,
		})
	);
};

export const getSiteMetricsChartData = function getSiteMetricsChartData({
	compositeData,
	histogram,
	name,
	title,
	tooltipTitle,
	type,
}: {
	compositeData: Record<string, Array<{value: number}>>;
	histogram: Array<{
		key: string;
		previousValue: number;
		value: number;
	}>;
	name: string;
	title: string;
	tooltipTitle?: string;
	type: MetricType;
}): TChartDataSet[] {
	if (name !== 'visitorsMetric') {
		return getMetricsChartData({
			histogram,
			name,
			title,
			tooltipTitle,
			type,
		}).map((data) =>
			[CHART_DATA_ID_1, CHART_DATA_PREVIOUS].includes(data.id)
				? {
						...data,
						color:
							data.id === CHART_DATA_PREVIOUS
								? CHART_GREEN_L2
								: CHART_GREEN,
					}
				: data
		);
	}

	const formatter = getDataFormatter(type);

	return [
		{
			color: CHART_BLUE,
			data: formatter(
				compositeData.knownVisitorsMetric.map(({value}) => value)
			),
			dataName: 'knownVisitorsMetric',
			id: CHART_DATA_ID_1,
			name: Liferay.Language.get('known-visitors'),
			tooltipTitle: Liferay.Language.get('known'),
			type: 'bar',
		},
		{
			color: CHART_ORANGE,
			data: formatter(
				compositeData.anonymousVisitorsMetric.map(({value}) => value)
			),
			dataName: 'anonymousVisitorsMetric',
			id: CHART_DATA_ID_2,
			name: Liferay.Language.get('anonymous-visitors'),
			tooltipTitle: Liferay.Language.get('anonymous'),
			type: 'bar',
		},
		{
			color: PREVIOUS_PERIOD_VISITORS_COLOR,
			data: formatter(histogram.map(({previousValue}) => previousValue)),
			id: CHART_DATA_PREVIOUS,
			name: Liferay.Language.get('previous-period'),
			type: 'line',
		},
		{
			data: histogram.map(({key}) => key),
			id: 'x',
		},
	];
};
