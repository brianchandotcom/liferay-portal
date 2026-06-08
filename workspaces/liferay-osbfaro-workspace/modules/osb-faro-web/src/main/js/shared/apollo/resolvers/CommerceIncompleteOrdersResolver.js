/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const CommerceIncompleteOrdersResolver =
	function CommerceIncompleteOrdersResolver() {
		return [
			{
				__typename: 'orderIncompleteCurrencyValues',
				currencyCode: 'EUR',
				trend: {
					__typename: 'orderIncompleteCurrencyValuesTrend',
					percentage: 100.0,
					trendClassification: 'POSITIVE',
				},
				value: '20000.00',
			},
			{
				__typename: 'orderIncompleteCurrencyValues',
				currencyCode: 'USD',
				trend: {
					__typename: 'orderIncompleteCurrencyValuesTrend',
					percentage: 20.0,
					trendClassification: 'POSITIVE',
				},
				value: '50000.00',
			},
			{
				__typename: 'orderIncompleteCurrencyValues',
				currencyCode: 'BRL',
				trend: {
					__typename: 'orderIncompleteCurrencyValuesTrend',
					percentage: 100.0,
					trendClassification: 'NEGATIVE',
				},
				value: '100000.00',
			},
		];
	};

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default CommerceIncompleteOrdersResolver;
