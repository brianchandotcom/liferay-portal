/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import moment from 'moment';
import {Interval, RangeSelectors} from '~/shared/types';
import {safeResultToProps} from '~/shared/util/mappers';
import {getSafeRangeSelectors} from '~/shared/util/util';

interface IHistogramMetric {
	key: string;
	value: number;
	valueKey: string;
}

interface ISiteMetricsResult {
	site: {
		anonymousVisitorsMetric: {histogram: {metrics: IHistogramMetric[]}};
		knownVisitorsMetric: {histogram: {metrics: IHistogramMetric[]}};
		visitorsMetric: {histogram: {metrics: IHistogramMetric[]}};
	};
}

interface ISiteMetricsDataRow {
	anonymousVisitors: number;
	intervalInitDate: number;
	knownVisitors: number;
	visitors: number;
}

export const mapPropsToOptions = function mapPropsToOptions({
	channelId,
	interval,
	rangeSelectors,
}: {
	channelId: string;
	interval: Interval;
	rangeSelectors: RangeSelectors;
}) {
	return {
		variables: {
			channelId,
			interval,
			...getSafeRangeSelectors(rangeSelectors),
		},
	};
};

export const mapResultToProps = safeResultToProps(
	({
		site: {anonymousVisitorsMetric, knownVisitorsMetric, visitorsMetric},
	}: ISiteMetricsResult) => ({
		data: anonymousVisitorsMetric.histogram.metrics.reduce<
			ISiteMetricsDataRow[]
		>(
			(acc, {key, value}, i) => [
				...acc,
				{
					anonymousVisitors: value,
					intervalInitDate: moment.utc(key).valueOf(),
					knownVisitors:
						knownVisitorsMetric.histogram.metrics[i].value,
					visitors: visitorsMetric.histogram.metrics[i].value,
				},
			],
			[]
		),
		dateKeysIMap: Map(
			visitorsMetric.histogram.metrics.map(({key, valueKey}) => [
				moment.utc(key).valueOf(),
				valueKey
					.split('/')
					.map((valueKeyHalf: string) =>
						moment.utc(valueKeyHalf).valueOf()
					),
			])
		),
	})
);
