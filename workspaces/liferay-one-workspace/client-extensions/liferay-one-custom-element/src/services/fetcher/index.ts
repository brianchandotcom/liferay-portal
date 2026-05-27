/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../../liferay/liferay';
import FetcherError from './FetcherError';

const liferayHost = window.location.origin;

function changeResource(resource: RequestInfo) {
	if (resource.toString().startsWith('http')) {
		return resource;
	}

	return `${liferayHost}/${resource}`;
}

function getHeaders(options?: RequestInit): Record<string, string> {
	const defaultHeaders = options?.headers;

	const normalizedHeaders = defaultHeaders
		? defaultHeaders instanceof Headers || Array.isArray(defaultHeaders)
			? Object.fromEntries(defaultHeaders as any)
			: (defaultHeaders as Record<string, string>)
		: {};

	const hasContentType = Object.keys(normalizedHeaders).some(
		(name) => name.toLowerCase() === 'content-type'
	);

	const isFormData = options?.body instanceof FormData;

	const headers: Record<string, string> = {
		'x-csrf-token': Liferay.authToken,
		...normalizedHeaders,
	};

	if (!hasContentType && !isFormData) {
		headers['Content-Type'] = 'application/json';
	}

	return headers;
}

const fetcher = async <T = any>(
	resource: RequestInfo,
	options?: RequestInit
): Promise<T> => {
	const headers = getHeaders(options);

	const response = await fetch(changeResource(resource), {
		...options,
		headers,
	});

	if (!response.ok) {
		const error = new FetcherError(
			'An error occurred while fetching the data.'
		);

		error.info = await response.json();
		error.status = response.status;
		throw error;
	}

	if (
		options?.method === 'DELETE' ||
		response.status === 204 ||
		response.headers.get('Content-Length') === '0'
	) {
		return {} as T;
	}

	return response.json();
};

fetcher.delete = (resource: RequestInfo) =>
	fetcher(resource, {
		method: 'DELETE',
	});

fetcher.patch = <T = any>(
	resource: RequestInfo,
	data: unknown,
	options?: RequestInit
) =>
	fetcher<T>(resource, {
		...options,
		body: JSON.stringify(data),
		method: 'PATCH',
	});

fetcher.post = <T = any>(
	resource: RequestInfo,
	data?: unknown,
	options?: RequestInit & {shouldStringify?: boolean}
): Promise<T> => {
	const shouldStringify = options?.shouldStringify ?? true;

	let body: BodyInit | null = null;

	if (data instanceof FormData) {
		body = data;
	}
	else if (data !== null) {
		body = shouldStringify ? JSON.stringify(data) : (data as BodyInit);
	}

	return fetcher<T>(resource, {
		...options,
		body,
		method: 'POST',
	});
};

fetcher.put = (resource: RequestInfo, data: unknown, options?: RequestInit) =>
	fetcher(resource, {
		...options,
		body: JSON.stringify(data),
		method: 'PUT',
	});

export default fetcher;
