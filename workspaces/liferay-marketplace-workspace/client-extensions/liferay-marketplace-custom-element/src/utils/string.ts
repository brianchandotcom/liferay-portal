/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DOMPurify from 'dompurify';

export function extractHTMLText(html: string) {
	const sanitizedHTML = DOMPurify.sanitize(html);

	const tempElement = document.createElement('div');
	tempElement.innerHTML = sanitizedHTML;

	const textContent = tempElement.textContent || tempElement.innerText;

	return textContent;
}

export function removeUnnecessaryURLString(str: string) {
	const index = str.indexOf('/o');

	return str.substring(index);
}
