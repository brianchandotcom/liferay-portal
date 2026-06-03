import moment from 'moment';
import {getSafeRangeSelectors} from 'shared/util/util';
import {Interval, RangeSelectors} from 'shared/types';
import {Map} from 'immutable';
import {safeResultToProps} from 'shared/util/mappers';

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

export const mapPropsToOptions = ({
	channelId,
	interval,
	rangeSelectors
}: {
	channelId: string;
	interval: Interval;
	rangeSelectors: RangeSelectors;
}) => ({
	variables: {
		channelId,
		interval,
		...getSafeRangeSelectors(rangeSelectors)
	}
});

export const mapResultToProps = safeResultToProps(
	({
		site: {anonymousVisitorsMetric, knownVisitorsMetric, visitorsMetric}
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
					visitors: visitorsMetric.histogram.metrics[i].value
				}
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
					)
			])
		)
	})
);
