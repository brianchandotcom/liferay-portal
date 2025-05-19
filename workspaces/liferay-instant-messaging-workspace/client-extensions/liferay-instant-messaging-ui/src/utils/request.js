/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import axios from 'axios';

import {config} from './constants';
import {showError} from './util';

export function getServerUrl() {
	return Liferay.OAuth2Client.FromUserAgentApplication(config.agentOauthAppId)
		.homePageURL;
}

export async function getOAuthToken() {
	const prom = new Promise((resolve, reject) => {
		Liferay.OAuth2Client.FromUserAgentApplication(config.agentOauthAppId)
			._getOrRequestToken()
			.then(
				(token) => {
					resolve(token.access_token);
				},
				(error) => {
					showError('Error', error);

					reject(error);
				}
			)
			.catch((error) => {
				showError('Error', error);

				reject(error);
			});
	});

	return prom;
}

export async function oAuthRequest(config) {
	return request({
		data: config.data ? config.data : null,
		headers: {
			...config.headers,
			Authorization: `Bearer ${await getOAuthToken()}`,
		},
		method: config.method,
		url: `${getServerUrl()}${config.url}`,
	});
}

export function request(config) {
	return new Promise((resolve, reject) => {
		axios
			.request({
				headers: {
					'accept-language':
						Liferay.ThemeDisplay.getLanguageId().split('_')[0],
					'x-csrf-token': Liferay.authToken,
				},
				method: 'get',
				...config,
			})
			.then((response) => {
				resolve(response.data);
			})
			.catch((error) => {
				reject({error, message: error || ''});
			});
	});
}

export function jsonRequest(url, data) {
	const prom = new Promise((resolve, reject) => {
		try {
			Liferay.Service(
				url,
				data,
				(result) => {
					resolve(result);
				},
				(error) => {
					reject(error);
				}
			);
		}
		catch (exp) {
			reject(exp.message);
		}
	});

	return prom;
}
