/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import logger from './Logger';

export async function fetcher<T = any>(
    url: string | URL,
    options?: RequestInit
): Promise<T> {
    const response = await fetch(url, options);

    if (!response.ok) {
        const cause = await response.text();

        logger.error(cause, JSON.stringify({ options, url }, null, 2));

        throw new Error(cause);
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

export { baseFetcher };
