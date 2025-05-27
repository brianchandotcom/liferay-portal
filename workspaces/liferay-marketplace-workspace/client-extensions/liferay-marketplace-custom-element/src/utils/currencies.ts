/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import EURFlag from '../assets/icons/eur_flag.svg';

export const currenciesCode = [
	{
		code: 'USD',
		flag: 'en-us',
		symbol: '$',
	},
	{
		code: 'CNY',
		flag: 'zh-cn',
		symbol: '¥',
	},
	{
		code: 'EUR',
		flag: 'eur-eur',
		iconSrc: EURFlag,
		symbol: '€',
	},
	{
		code: 'INR',
		flag: 'hi-in',
		symbol: '₹',
	},
];

export function formatCurrency(
	amount: number,
	currencyCode = 'USD',
	locale = 'en-US'
) {
	return new Intl.NumberFormat(locale, {
		currency: currencyCode,
		style: 'currency',
	}).format(amount);
}
