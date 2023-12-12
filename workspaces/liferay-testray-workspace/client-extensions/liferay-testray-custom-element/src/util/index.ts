/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function isIncludingFormPage(pathname: string) {
	return ['create', 'update'].some((path) => pathname.includes(path));
}

export function getUniqueList(items: number[]) {
	return [...new Set([...items])];
}

export function safeJSONParse(
	value: string | null,
	defaultValue: unknown = null
) {
	if (defaultValue && typeof value !== 'string') {
		return defaultValue;
	}

	try {
		return JSON.parse(value as string);
	}
	catch (error) {
		return defaultValue;
	}
}

export function waitTimeout(timer: number) {
	return new Promise((resolve) => setTimeout(resolve, timer));
}
