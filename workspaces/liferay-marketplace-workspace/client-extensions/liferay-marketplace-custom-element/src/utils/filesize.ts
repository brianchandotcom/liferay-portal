/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const units = {
	GB: 1000 ** 3,
	KB: 1000,
	MB: 1000 ** 2,
};

export function convertSize(
	value: number | string,
	fromUnit: keyof typeof units,
	toUnit: keyof typeof units
) {
	return (Number(value) * units[fromUnit]) / units[toUnit];
}
