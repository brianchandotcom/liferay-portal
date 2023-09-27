/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import logger from './Logger';

export async function fetcher<T = any>(
	url: string | URL,
	options?: RequestInit
): Promise<T> {
	const response = await fetch(url, options);

	if (!response.ok) {
		const cause = await response.text();

		logger.error(cause, response, JSON.stringify({options, url}, null, 2));

		throw new Error('Error...');
	}

	return response.json();
}

const baseFetcher =
	<T = any>(baseURL: string | URL, baseOptions?: RequestInit) =>
	(url: string | URL, options?: RequestInit) =>
		fetcher<T>(`${baseURL}${url}`, {
			...baseOptions,
			...options,
		});

export {baseFetcher};
