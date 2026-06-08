import {CHART_COLOR_NAMES, getIntervals} from '~/shared/util/charts';
import {RangeKeyTimeRanges} from '~/shared/util/constants';
import {toUnix} from '~/shared/util/date';
import * as data from '~/test/data';

import {CompositeMetric, MetricType} from '../metrics';
import {
	convertHistogramKeysToDate,
	getMetricsChartData,
	getMetricsData,
	getSiteMetricsChartData,
} from '../util';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.unmock('react-dom');

const {stark: CHART_BLUE, starkL2: CHART_BLUE_L2} = CHART_COLOR_NAMES;

describe('convertHistogramKeysToDate', () => {
	it('converts the histogram date key strings to Date types', () => {
		expect(
			[
				{
					key: '2018-07-16T00:00',
					previousValueKey: '2018-07-15T00:00',
					value: 15,
					valueKey: '2018-07-16T00:00',
				},
			].map(convertHistogramKeysToDate)
		).toMatchSnapshot();
	});
});

describe('getMetricsChartData', () => {
	it('returns data formatted for use in a Metrics chart', () => {
		const mockParameters = {
			histogram: data
				.mockMetricFragment(10)
				.histogram.metrics.map(convertHistogramKeysToDate) as any,
			name: 'fooMetric',
			title: '',
			tooltipTitle: '',
			type: MetricType.Number,
		};

		expect(getMetricsChartData(mockParameters)).toMatchSnapshot();
	});
});

describe('getMetricsData', () => {
	it('returns the chart items', () => {
		const rangeKey = 30;
		const keyDate = '2018-07-16T00:00';
		const valueKeyDate = '1531699200000';
		const previousValueKeyDate = '1531612800000';
		const metrics = [
			{
				name: 'comments',
				sortField: 'comments',
				title: 'comments',
				tooltipTitle: 'Avg. Comments',
				type: MetricType.Number,
			},
			{
				name: 'views',
				sortField: 'views',
				title: 'Views',
				tooltipTitle: 'Avg. Views',
				type: MetricType.Number,
			},
		] as any;

		const result = {
			comments: {
				histogram: {
					asymmetricalComparison: false,
					metrics: [
						{
							key: keyDate,
							previousValue: 0,
							previousValueKey: previousValueKeyDate,
							trend: {
								percentage: 10,
								trendClassification: 'NEUTRAL',
							},
							value: 15,
							valueKey: valueKeyDate,
						},
					],
				},
				trend: {
					percentage: 10,
					trendClassification: 'NEUTRAL',
				},
			},
			views: {
				histogram: {
					asymmetricalComparison: false,
					metrics: [
						{
							key: keyDate,
							previousValue: 0,
							previousValueKey: previousValueKeyDate,
							trend: {
								percentage: 10,
								trendClassification: 'NEUTRAL',
							},
							value: 5,
							valueKey: valueKeyDate,
						},
					],
				},
				trend: {
					percentage: -100,
					trendClassification: 'NEGATIVE',
				},
			},
		};
		const metricsData = getMetricsData(result as any, metrics, {
			rangeKey: rangeKey as unknown as RangeKeyTimeRanges,
		});

		const dateKeysIMap = new Map([
			[toUnix(keyDate), [toUnix(valueKeyDate)]],
		]);
		const prevDateKeysIMap = new Map([
			[toUnix(keyDate), [toUnix(previousValueKeyDate)]],
		]);

		metricsData.forEach((metricData) => {
			delete (metricData as any).format;
		});

		expect(metricsData).toEqual([
			{
				content: {
					details: {
						asymmetricComparison: undefined,
						color: '#6B6C7E',
						icon: 'caret-top-l',
						label: '10%',
					},
					name: 'comments',
					title: 'comments',
					type: 'number',
					value: '',
				},
				data: [
					{
						color: CHART_BLUE,
						data: [15],
						id: 'data_1',
						name: 'Avg. Comments',
						tooltipTitle: 'Avg. Comments',
					},
					{
						color: CHART_BLUE_L2,
						data: [0],
						id: 'data_previous',
						name: 'Previous Period',
					},
					{data: [toUnix(keyDate)], id: 'x'},
				],
				dateKeysIMap,
				intervals: getIntervals(
					RangeKeyTimeRanges.Last30Days,
					[toUnix(keyDate)] as number[],
					'D',
					dateKeysIMap
				),
				prevDateKeysIMap,
			},
			{
				content: {
					asymmetricComparison: undefined,
					details: {
						color: '#DA1414',
						icon: 'caret-bottom-l',
						label: '100%',
					},
					name: 'views',
					title: 'Views',
					type: 'number',
					value: '',
				},
				data: [
					{
						color: CHART_BLUE,
						data: [5],
						id: 'data_1',
						name: 'Avg. Views',
						tooltipTitle: 'Avg. Views',
					},
					{
						color: CHART_BLUE_L2,
						data: [0],
						id: 'data_previous',
						name: 'Previous Period',
					},
					{data: [toUnix(keyDate)], id: 'x'},
				],
				dateKeysIMap,
				intervals: getIntervals(
					RangeKeyTimeRanges.Last30Days,
					[toUnix(keyDate)] as number[],
					'D',
					dateKeysIMap
				),
				prevDateKeysIMap,
			},
		]);
	});
});

describe('getSiteMetricsChartData', () => {
	it('returns data formatted for use in a Site Metrics chart', () => {
		const {name, title, tooltipTitle, type} = CompositeMetric;

		const mockParameters = {
			compositeData: {
				anonymousVisitorsMetric: data
					.mockMetricFragment(25)
					.histogram.metrics.map(convertHistogramKeysToDate),
				knownVisitorsMetric: data
					.mockMetricFragment(55)
					.histogram.metrics.map(convertHistogramKeysToDate),
			},
			histogram: data
				.mockMetricFragment(85)
				.histogram.metrics.map(convertHistogramKeysToDate),
			name,
			title,
			tooltipTitle,
			type,
		};
		expect(
			getSiteMetricsChartData(mockParameters as any)
		).toMatchSnapshot();
	});
});
