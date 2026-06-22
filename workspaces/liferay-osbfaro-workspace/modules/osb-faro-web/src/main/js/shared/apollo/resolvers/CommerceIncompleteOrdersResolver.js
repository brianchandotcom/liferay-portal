export default () => [
	{
		__typename: 'orderIncompleteCurrencyValues',
		currencyCode: 'EUR',
		trend: {
			__typename: 'orderIncompleteCurrencyValuesTrend',
			percentage: 100.0,
			trendClassification: 'POSITIVE'
		},
		value: '20000.00'
	},
	{
		__typename: 'orderIncompleteCurrencyValues',
		currencyCode: 'USD',
		trend: {
			__typename: 'orderIncompleteCurrencyValuesTrend',
			percentage: 20.0,
			trendClassification: 'POSITIVE'
		},
		value: '50000.00'
	},
	{
		__typename: 'orderIncompleteCurrencyValues',
		currencyCode: 'BRL',
		trend: {
			__typename: 'orderIncompleteCurrencyValuesTrend',
			percentage: 100.0,
			trendClassification: 'NEGATIVE'
		},
		value: '100000.00'
	}
];
