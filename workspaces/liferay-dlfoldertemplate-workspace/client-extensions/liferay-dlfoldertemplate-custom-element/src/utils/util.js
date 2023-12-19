/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from './constants';

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
					showError(error);

					reject(error);
				}
			)
			.catch((error) => {
				showError(error);

				reject(error);
			});
	});

	return prom;
}

export function getHostUrl() {
	return config.external ? config.apiHost : '';
}

export function showSuccess(message) {
	Liferay.Util.openToast({title: message, type: 'success'});
}

export function showError(message) {
	Liferay.Util.openToast({title: message, type: 'danger'});
}
