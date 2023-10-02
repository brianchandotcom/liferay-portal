/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default async function liferayFetcher<T>(
	url: string,
	token: string,
	options?: RequestInit
): Promise<T> {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(url, {
		...options,
		headers: {
			...options?.headers,
			'Accept': 'application/json',
			'x-csrf-token': token,
		},
	});

	if (!response.ok) {
		throw new Error(String(response.status));
	}

	if (response.status !== 204) {
		return response.json();
	}

	return response as any;
}

liferayFetcher.post = <T>(
	url: string,
	token: string,
	data: T,
	options?: RequestInit
) => {
	return liferayFetcher<T>(url, token, {
		body: JSON.stringify(data),
		headers: {
			'Content-Type': 'application/json',
		},
		method: 'POST',
		...options,
	});
};

liferayFetcher.put = <T>(
	url: string,
	token: string,
	data: Partial<T>,
	options?: RequestInit
) =>
	liferayFetcher<T>(url, token, {
		...options,
		body: JSON.stringify(data),
		headers: {
			...options?.headers,
			'Content-Type': 'application/json',
		},
		method: 'PUT',
	});

liferayFetcher.patch = <T>(
	url: string,
	token: string,
	data: Partial<T>,
	options?: RequestInit
) =>
	liferayFetcher<T>(url, token, {
		...options,
		body: JSON.stringify(data),
		headers: {
			...options?.headers,
			'Content-Type': 'application/json',
		},
		method: 'PATCH',
	});

liferayFetcher.delete = <T>(
	url: string,
	token: string,
	options?: RequestInit
) =>
	liferayFetcher<T>(url, token, {
		...options,
		headers: {
			...options?.headers,
		},
		method: 'DELETE',
	});
