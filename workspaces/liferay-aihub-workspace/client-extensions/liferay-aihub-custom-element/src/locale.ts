/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function normalizeLocaleId(localeId: string): string {
	return localeId.replace(/-/g, '_');
}

export function getLanguageId(): string {
	const hostLang = document.documentElement.lang?.trim();

	if (hostLang) {
		return normalizeLocaleId(hostLang);
	}

	return normalizeLocaleId(navigator.language || '');
}

export function getLocalizedValue({
	defaultLanguageId,
	editingLanguageId,
}: {
	defaultLanguageId: string;
	editingLanguageId: string;
}) {
	return (value: Record<string, string> | undefined | null): string =>
		value ? value[editingLanguageId] ?? value[defaultLanguageId] ?? '' : '';
}
