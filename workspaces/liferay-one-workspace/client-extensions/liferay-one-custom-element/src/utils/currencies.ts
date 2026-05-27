/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
