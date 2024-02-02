/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import axios from 'axios';

import {getOAuthToken} from './util';

export async function oAuthRequest(config) {
	return request({
		headers: {
			Authorization: `Bearer ${await getOAuthToken()}`,
		},
		...config,
	});
}

export function request(config) {
	return new Promise((resolve, reject) => {
		axios
			.request({
				headers: {
					'x-csrf-token': Liferay.authToken,
				},
				method: 'get',
				...config,
			})
			.then((response) => {
				resolve(response.data);
			})
			.catch((error) => {
				reject({error, message: error.response.data.error || ''});
			});
	});
}
