/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function safeJSONParse<T = any>(
	value: string | null,
	defaultValue: T
): T {
	if (defaultValue && typeof value !== 'string') {
		return defaultValue as T;
	}

	try {
		return JSON.parse(value as string);
	}
	catch {
		return defaultValue;
	}
}

export function waitTimeout(timer: number) {
	return new Promise((resolve) => setTimeout(() => resolve(null), timer));
}
