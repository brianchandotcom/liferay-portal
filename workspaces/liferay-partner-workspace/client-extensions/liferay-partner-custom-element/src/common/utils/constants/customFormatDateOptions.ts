/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IOption {
	[key: string]: Intl.DateTimeFormatOptions;
}

export const customFormatDateOptions: IOption = {
	SHORT_MONTH: {
		day: '2-digit',
		month: 'short',
		timeZone: 'UTC',
		year: 'numeric',
	},
	SHORT_MONTH_YEAR: {
		day: '2-digit',
		month: 'short',
		timeZone: 'UTC',
	},
};
