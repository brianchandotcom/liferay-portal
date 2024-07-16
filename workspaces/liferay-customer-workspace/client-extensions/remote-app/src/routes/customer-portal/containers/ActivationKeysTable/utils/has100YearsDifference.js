/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function has100YearsDifference(startDate, expirationDate) {
	const startDateDne = new Date(startDate);
	const expirationDateDne = new Date(expirationDate);
	const differenceInYears =
		expirationDateDne.getFullYear() - startDateDne.getFullYear();

	return Math.abs(differenceInYears - 100) < 1;
}
