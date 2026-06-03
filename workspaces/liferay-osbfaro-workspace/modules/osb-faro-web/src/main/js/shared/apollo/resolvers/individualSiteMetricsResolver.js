export default () => ({
	__typename: 'SiteMetric',
	anonymousVisitorsMetric: {
		__typename: 'Metric',
		histogram: {
			__typename: 'HistogramMetricBag',
			asymmetricComparison: false,
			metrics: [
				{
					__typename: 'HistogramMetric',
					key: '2025-02-11T00:00',
					value: 70.0,
					valueKey: '2025-02-11T00:00'
				},
				{
					__typename: 'HistogramMetric',
					key: '2025-02-12T00:00',
					value: 26.0,
					valueKey: '2025-02-12T00:00'
				},
				{
					__typename: 'HistogramMetric',
					key: '2025-02-13T00:00',
					value: 1835.0,
					valueKey: '2025-02-13T00:00'
				},
				{
					__typename: 'HistogramMetric',
					key: '2025-02-14T00:00',
					value: 3937.0,
					valueKey: '2025-02-14T00:00'
				}
			],
			total: 4
		},
		trend: {__typename: 'Trend', percentage: -77.6},
		value: 5714.0
	},
	knownVisitorsMetric: {
		__typename: 'Metric',
		histogram: {
			__typename: 'HistogramMetricBag',
			asymmetricComparison: false,
			metrics: [
				{
					__typename: 'HistogramMetric',
					key: '2025-02-11T00:00',
					value: 10.0,
					valueKey: '2025-02-11T00:00'
				},
				{
					__typename: 'HistogramMetric',
					key: '2025-02-12T00:00',
					value: 5.0,
					valueKey: '2025-02-12T00:00'
				},
				{
					__typename: 'HistogramMetric',
					key: '2025-02-13T00:00',
					value: 26.0,
					valueKey: '2025-02-13T00:00'
				},
				{
					__typename: 'HistogramMetric',
					key: '2025-02-14T00:00',
					value: 56.0,
					valueKey: '2025-02-14T00:00'
				}
			],
			total: 4
		},
		trend: {__typename: 'Trend', percentage: -48.6},
		value: 73.0
	},
	visitorsMetric: {
		__typename: 'Metric',
		histogram: {
			__typename: 'HistogramMetricBag',
			asymmetricComparison: false,
			metrics: [
				{
					__typename: 'HistogramMetric',
					key: '2025-02-11T00:00',
					value: 80.0,
					valueKey: '2025-02-11T00:00'
				},
				{
					__typename: 'HistogramMetric',
					key: '2025-02-12T00:00',
					value: 31.0,
					valueKey: '2025-02-12T00:00'
				},
				{
					__typename: 'HistogramMetric',
					key: '2025-02-13T00:00',
					value: 1861.0,
					valueKey: '2025-02-13T00:00'
				},
				{
					__typename: 'HistogramMetric',
					key: '2025-02-14T00:00',
					value: 3993.0,
					valueKey: '2025-02-14T00:00'
				}
			],
			total: 4
		},
		trend: {__typename: 'Trend', percentage: -77.4},
		value: 5787.0
	}
});
