/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const formatLocaleCurrency = (
	value: number,
	locale = 'en-us',
	currency = 'USD'
) => {
	return value.toLocaleString(locale, {
		currency,
		maximumFractionDigits: 2,
		style: 'currency',
	});
};

export default formatLocaleCurrency;
