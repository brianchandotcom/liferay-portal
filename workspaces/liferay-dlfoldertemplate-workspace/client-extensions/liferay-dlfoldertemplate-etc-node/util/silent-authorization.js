/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {lxcConfig} from '@rotty3000/config-node';
import axios from 'axios';
import cache from 'memory-cache';

import {applicationExternalReferenceCodes} from './constants.js';

const serverOauthApp = lxcConfig.oauthApplication(
	applicationExternalReferenceCodes.OAUTH_SERVER_EXTERNAL_REFERENCE_CODE
);

const clientId = serverOauthApp.clientId();

const clientSecret = serverOauthApp.clientSecret();

const lxcDXPMainDomain = lxcConfig.dxpMainDomain();

const lxcDXPServerProtocol = lxcConfig.dxpProtocol();

const uri = serverOauthApp.jwksUri();

const tokenEndpoint = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}${uri}`;

export function getServerToken() {
	const prom = new Promise((resolve, reject) => {
		const cacheKey = `server_token`;

		const cachedData = cache.get(cacheKey);

		if (cachedData) {
			resolve(cachedData);
		}
		else {
			try {
				const requestBody = {
					client_id: clientId,
					client_secret: clientSecret,
					grant_type: 'client_credentials',
				};

				const headers = {
					'Content-Type': 'application/x-www-form-urlencoded',
				};

				axios
					.post(tokenEndpoint, requestBody, {headers})
					.then((response) => {
						cache.put(
							cacheKey,
							response.data.access_token,
							(parseInt(response.data.expires_in, 36) - 5) * 1000
						);
						const token = response.data.access_token;

						resolve(token);
					})
					.catch((error) => {
						console.error(
							`Error obtaining authorization code.`,
							error.response.data
						);

						reject('No valid token!');
					});
			}
			catch (error) {
				reject('No valid token!');
			}
		}
	});

	return prom;
}
