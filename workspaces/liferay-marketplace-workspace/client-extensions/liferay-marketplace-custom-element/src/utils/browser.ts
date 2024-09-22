/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function copyToClipboard(text: string) {
	navigator.clipboard.writeText(text);
}

export function scrollToTop() {
	globalThis.scrollTo({behavior: 'smooth', left: 0, top: 0});
}
