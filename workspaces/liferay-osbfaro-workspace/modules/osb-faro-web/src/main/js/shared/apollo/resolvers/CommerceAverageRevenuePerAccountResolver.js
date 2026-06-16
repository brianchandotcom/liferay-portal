export default () => [
	{
		__typename: 'orderAverageCurrencyValues',
		currencyCode: 'EUR',
		trend: {
			__typename: 'orderAverageCurrencyValuesTrend',
			percentage: 100.0,
			trendClassification: 'POSITIVE'
		},
		value: '20000.00'
	},
	{
		__typename: 'orderAverageCurrencyValues',
		currencyCode: 'USD',
		trend: {
			__typename: 'orderAverageCurrencyValuesTrend',
			percentage: 20.0,
			trendClassification: 'POSITIVE'
		},
		value: '50000.00'
	},
	{
		__typename: 'orderAverageCurrencyValues',
		currencyCode: 'BRL',
		trend: {
			__typename: 'orderAverageCurrencyValuesTrend',
			percentage: 100.0,
			trendClassification: 'NEGATIVE'
		},
		value: '100000.00'
	}
];
