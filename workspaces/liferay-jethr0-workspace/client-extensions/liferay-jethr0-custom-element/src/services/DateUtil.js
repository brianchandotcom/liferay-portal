/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function isValidDate(date) {
	if (date instanceof Date && !isNaN(date)) {
		return true;
	}

	return false;
}

export function toLocaleString(dateString) {
	if (dateString === null || dateString === '') {
		return '';
	}

	const date = new Date(dateString);

	if (!isValidDate(date)) {
		return '';
	}

	return date.toLocaleString();
}
