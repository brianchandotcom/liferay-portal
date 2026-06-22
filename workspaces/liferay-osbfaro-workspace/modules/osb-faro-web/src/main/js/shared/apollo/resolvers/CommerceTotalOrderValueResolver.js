export default () => [
	{
		__typename: 'orderTotalCurrencyValues',
		currencyCode: 'EUR',
		trend: {
			__typename: 'orderTotalCurrencyValuesTrend',
			percentage: 100.0,
			trendClassification: 'POSITIVE'
		},
		value: '20000.00'
	},
	{
		__typename: 'orderTotalCurrencyValues',
		currencyCode: 'USD',
		trend: {
			__typename: 'orderTotalCurrencyValuesTrend',
			percentage: 20.0,
			trendClassification: 'POSITIVE'
		},
		value: '50000.00'
	},
	{
		__typename: 'orderTotalCurrencyValues',
		currencyCode: 'BRL',
		trend: {
			__typename: 'orderTotalCurrencyValuesTrend',
			percentage: 100.0,
			trendClassification: 'NEGATIVE'
		},
		value: '100000.00'
	}
];
