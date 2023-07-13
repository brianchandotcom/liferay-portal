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

import {Liferay} from '../../liferay/liferay';
import FetcherError from './FetcherError';

const liferayHost = window.location.origin;

const headlessAdminUserAPIs = ['account', 'accounts', 'roles', 'user-groups'];

const headlessDeliveryAPIs = [
	'message-board-messages',
	'message-board-threads',
];

function changeResource(resource: RequestInfo) {
	const getIsResourceFromAPI = (apis: string[]) =>
		apis.some((api) => resource.toString().includes(api));

	if (resource.toString().startsWith('http')) {
		return resource;
	}

	if (getIsResourceFromAPI(headlessDeliveryAPIs)) {
		return `${liferayHost}/o/headless-delivery/v1.0${resource}`;
	}

	if (getIsResourceFromAPI(headlessAdminUserAPIs)) {
		return `${liferayHost}/o/headless-admin-user/v1.0${resource}`;
	}

	return `${liferayHost}/${resource}`;
}

const fetcher = async <T = any>(
	resource: RequestInfo,
	options?: RequestInit
): Promise<T | undefined> => {
	const response = await fetch(changeResource(resource), {
		...options,
		headers: {
			...options?.headers,
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
	});

	if (!response.ok) {
		const error = new FetcherError(
			'An error occurred while fetching the data.'
		);

		error.info = await response.json();
		error.status = response.status;
		throw error;
	}

	if (options?.method !== 'DELETE' && response.status !== 204) {
		return response.json();
	}
};

export default fetcher;
