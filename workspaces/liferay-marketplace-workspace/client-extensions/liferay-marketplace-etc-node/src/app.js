/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import bodyParser from 'body-parser';
import express from 'express';
import fetch from 'node-fetch';

import config from './util/configTreePath.js';
import {
	corsWithReady,
	liferayJWT,
} from './util/liferay-oauth2-resource-server.js';
import log from './util/log.js';

import 'dotenv/config.js';

const lxcDXPServerProtocol = config['com.liferay.lxc.dxp.server.protocol'];
const clientExtensionOauthRoute =
	config[
		'liferay-marketplace-etc-node-oauth-application-user-agent.oauth2.token.uri'
	];
const routesDXPConfig = config['com.liferay.lxc.dxp.mainDomain'];
const lxcDXPMainDomain = routesDXPConfig.split('\n')[0];

const SSA_BASE_URL = process.env.LIFERAY_MARKETPLACE_ETC_NODE_SSA_BASE_URL;
const SSA_DURATION = process.env.LIFERAY_MARKETPLACE_ETC_NODE_SSA_DURATION;
const SSA_CLIENT_ID = process.env.LIFERAY_MARKETPLACE_ETC_NODE_SSA_CLIENT_ID;
const SSA_CLIENT_SECRET =
	process.env.LIFERAY_MARKETPLACE_ETC_NODE_SSA_CLIENT_SECRET;
const SSA_SERVICE_USER_ID =
	process.env.LIFERAY_MARKETPLACE_ETC_NODE_SSA_SERVICE_USER_ID;

const trialTypes = {
	CLOUDAPP: 0,
	CLOUDAPP30: 30,
	DXPAPP: 0,
	DXPAPP30: 30,
	PROJECT60: 60,
	SOLUTIONS7: 7,
	SOLUTIONS30: 30,
};

const app = express();

let _ssaBearer;
let _ssaBearerExpiration;

const getSSABearer = async function () {
	if (_ssaBearer && _ssaBearerExpiration > Date.now()) {
		return _ssaBearer;
	}

	return fetch(`${SSA_BASE_URL}${clientExtensionOauthRoute}`, {
		body: new URLSearchParams({
			client_id: SSA_CLIENT_ID,
			client_secret: SSA_CLIENT_SECRET,
			grant_type: 'client_credentials',
		}),
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded',
		},
		method: 'POST',
	})
		.then((response) => {
			const expiresInMilli = response.expires_in * 1000;

			_ssaBearer = response.bearer;
			_ssaBearerExpiration = Date.now() + expiresInMilli;

			return response.json();
		})
		.catch((error) => {
			log.error(error);
		});
};

app.use(bodyParser.json());
app.use(corsWithReady);
app.use(liferayJWT);

app.get(config.readyPath, (req, res) => {
	res.send('READY');
});

app.post('/marketplace/test', async (req, res) => {
	const {body, jwt} = req;

	log.info(`post /marketplace/test: ${JSON.stringify(body, null, '\t')}`);
	log.info('User %s is authorized', jwt.username);
	log.info('User scope %s', jwt.scope);

	res.status(200).send(body);
});

app.delete('/marketplace/trial', async (req, res) => {
	const {body} = req;

	log.info(`delete /marketplace/trial: ${JSON.stringify(body, null, '\t')}`);

	const uri =
		SSA_BASE_URL +
		'/o/provisioning/trial?provisioningId=' +
		body.provisioningId;

	fetch(uri, {
		headers: {
			Authorization: `Bearer ${getSSABearer()}`,
		},
		method: 'DELETE',
	})
		.then((response) => response.json())
		.then((data) => log.info('response', data))
		.catch((error) => log.error(error));

	res.status(200).send(body);
});

app.get('/marketplace/trial', async (req, res) => {
	const {body} = req;

	log.info(`get /marketplace/trial: ${JSON.stringify(body, null, '\t')}`);

	const uri =
		SSA_BASE_URL +
		'/o/provisioning/trial?provisioningId=' +
		body.provisioningId;

	fetch(uri, {
		headers: {
			Authorization: `Bearer ${getSSABearer()}`,
		},
		method: 'GET',
	})
		.then((response) => response.json())
		.then((data) => log.info('response', data))
		.catch((error) => log.error(error));

	res.status(200).send(body);
});

app.get('/marketplace/trials', async (req, res) => {
	const {body} = req;

	log.info(`get /marketplace/trials: ${JSON.stringify(body, null, '\t')}`);

	const uri =
		SSA_BASE_URL + '/o/provisioning/trials?accountId=' + body.accountId;

	fetch(uri, {
		headers: {
			Authorization: `Bearer ${getSSABearer()}`,
		},
	})
		.then((response) => {
			log.info('response', response);
		})
		.catch((error) => {
			log.error(error);
		});

	res.status(200).send(body);
});

app.get('/marketplace/trials/count', async (req, res) => {
	const {body} = req;

	log.info(
		`get /marketplace/trials/count: ${JSON.stringify(body, null, '\t')}`
	);

	const uri =
		SSA_BASE_URL +
		'/o/provisioning/trials/count?accountId=' +
		body.accountId;

	fetch(uri, {
		headers: {
			Authorization: `Bearer ${getSSABearer()}`,
		},
	})
		.then((response) => {
			log.info('response', response);
		})
		.catch((error) => {
			log.error(error);
		});

	res.status(200).send(body);
});

app.post('/marketplace/trial', async (req, res) => {
	try {
		const {body} = req;
		setTimeout(async () => {
			const bearerToken = req.headers.authorization;
			const data = {};
			const token = await getSSABearer();
			const uri = SSA_BASE_URL + '/o/provisioning/trial';

			const getUserInfoResponse = await fetch(
				`${lxcDXPServerProtocol}://${lxcDXPMainDomain}/o/headless-admin-user/v1.0/user-accounts/${body.userId}`,
				{
					headers: {
						Authorization: bearerToken,
					},
				}
			);

			if (!getUserInfoResponse.ok) {
				throw new Error('Failed to fetch user information');
			}

			const userInformation = await getUserInfoResponse.json();

			const getCustomFieldsResponse = await fetch(
				`${lxcDXPServerProtocol}://${lxcDXPMainDomain}/o/headless-commerce-admin-order/v1.0/orders/${body.modelDTOOrder.id}?fields=customFields`,
				{
					headers: {
						Authorization: bearerToken,
					},
				}
			);

			if (!getCustomFieldsResponse.ok) {
				throw new Error('Failed to fetch custom fields');
			}

			const {customFields} = await getCustomFieldsResponse.json();
			const accountId = body.modelDTOOrder.accountId;
			const projectName = null;
			const siteInitializer = customFields['Site Initializer'];

			data.emailAddress = userInformation.emailAddress;
			data.firstName = userInformation.givenName;
			data.lastName = userInformation.familyName;

			if (accountId !== '') {
				data.accountId = Number(accountId);
			}

			if (projectName !== '') {
				data.projectName = projectName;
			}

			if (siteInitializer !== '') {
				data.siteInitializer = siteInitializer;
			}

			data.duration =
				trialTypes[body.modelDTOOrder.orderTypeExternalReferenceCode] ||
				Number(SSA_DURATION);

			data.userId = Number(SSA_SERVICE_USER_ID);

			const response = await fetch(uri, {
				body: JSON.stringify(data),
				headers: {
					'Authorization': `Bearer ${token.access_token}`,
					'Content-Type': 'application/json',
				},
				method: 'POST',
			});

			if (!response.ok) {
				throw new Error(
					`Trial request failed with status: ${response.status}`
				);
			}

			const responseData = await response.json();

			log.info(
				'Trail request sent for order: ' + JSON.stringify(responseData)
			);
		}, 300);
		res.status(200).send(body);
	}
	catch (error) {
		log.error(error);

		res.status(500).send('Internal Server Error');
	}
});

app.post('/marketplace/trial/extend', async (req, res) => {
	const {body} = req;

	log.info(
		`post /marketplace/trial/extend: ${JSON.stringify(body, null, '\t')}`
	);

	let uri = SSA_BASE_URL + '/o/provisioning/trial/extend';

	const duration = body.duration;
	const provisioningId = body.provisioningId;

	uri += '?duration=' + duration;
	uri += '&provisioiningId=' + provisioningId;

	fetch(uri, {
		headers: {
			Authorization: `Bearer ${getSSABearer()}`,
		},
		method: 'POST',
	})
		.then((response) => log.info('response', response))
		.catch((error) => log.error(error));

	res.status(200).send(body);
});

const serverPort = config['server.port'];

app.listen(serverPort, () => {
	log.info('App listening on %s', serverPort);
});
