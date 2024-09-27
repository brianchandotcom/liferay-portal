/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getRandomID() {
	try {
		return crypto.randomUUID();
	}
	catch {
		return `liferay-${Math.random()}`.replace('.', '-');
	}
}

export function normalizeURLProtocol(url = '') {
	if (window.location.href.startsWith('https')) {
		return url;
	}

	return url.replace('https', 'http');
}

export function removeUnnecessaryURLString(text: string) {
	const index = text.indexOf('/o');

	return text.substring(index);
}

export function removeHTMLTags(text: string) {
	return text.replace(/<\/?[^>]+(>|$)/g, '');
}

export function sanitizeStringForURL(text: string) {
	return text
		.toLowerCase()
		.trim()
		.replace(/[^a-z0-9\s-]/g, '')
		.replace(/\s+/g, '-')
		.replace(/-+/g, '-')
		.replace(/^-|-$/g, '');
}
