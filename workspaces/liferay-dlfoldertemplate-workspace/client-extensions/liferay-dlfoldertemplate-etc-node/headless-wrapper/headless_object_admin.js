/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import axios from 'axios';
import {URL} from 'url';

import config from '../util/configTreePath.js';

const lxcDXPMainDomain =
	config['com.liferay.lxc.dxp.mainDomain'] ||
	config['com.liferay.sh.dxp.mainDomain'];
const lxcDXPServerProtocol =
	config['com.liferay.lxc.dxp.server.protocol'] ||
	config['com.liferay.sh.dxp.server.protocol'];
const oauth2JWKSURI = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}`;
const objectDefinitionsEndPoint = 'o/object-admin/v1.0/object-definitions';
async function postDataToEndpoint(url, postData, bearerToken) {
	const prom = new Promise((resolve, reject) => {
		const config = {
			data: JSON.stringify(postData),
			headers: {
				'Accept': 'application/json',
				'Authorization': `Bearer ${bearerToken}`,
				'Content-Type': 'application/json',
			},
			maxBodyLength: Infinity,
			method: 'post',
			url,
		};

		axios
			.request(config)
			.then((response) => {
				resolve(response.data);
			})
			.catch((error) => {
				reject(error);
			});
	});

	return prom;
}
export async function postDefinition(schema, token) {
	const apiUrl = new URL(`${oauth2JWKSURI}/${objectDefinitionsEndPoint}`);
	const result = postDataToEndpoint(apiUrl.href, schema, token);

	return result;
}
export async function deleteProxyObject(externalReferenceCode, bearerToken) {
	try {
		const objectDefinitionId = await getObjectDefinition(
			externalReferenceCode,
			bearerToken
		);
		await deleteObjectDefinition(objectDefinitionId, bearerToken);

		return true;
	}
	catch (exp) {
		console.error(`Error while deleting Object ${externalReferenceCode}!`);

		return false;
	}
}
export function getObjectDefinition(externalReferenceCode, bearerToken) {
	const prom = new Promise((resolve, reject) => {
		const url = `${oauth2JWKSURI}/${objectDefinitionsEndPoint}/by-external-reference-code/${externalReferenceCode}`;
		const config = {
			headers: {
				Accept: 'application/json',
				Authorization: bearerToken,
			},
			maxBodyLength: Infinity,
			method: 'get',
			url,
		};
		axios
			.request(config)
			.then(async (response) => {
				const objectDefinitionId = response.data.id;
				resolve(objectDefinitionId);
			})
			.catch((error) => {
				reject(error.message);
			});
	});

	return prom;
}
function deleteObjectDefinition(objectDefinitionId, bearerToken) {
	const prom = new Promise((resolve, reject) => {
		const url = `${oauth2JWKSURI}/${objectDefinitionsEndPoint}/${objectDefinitionId}`;
		const config = {
			headers: {
				Accept: 'application/json',
				Authorization: bearerToken,
			},
			maxBodyLength: Infinity,
			method: 'delete',
			url,
		};
		axios
			.request(config)
			.then((response) => {
				resolve(response.data);
			})
			.catch((error) => {
				reject(error);
			});
	});

	return prom;
}
export function publishObjectDefinition(objectDefinitionId, bearerToken) {
	const prom = new Promise((resolve, reject) => {
		const config = {
			headers: {
				Authorization: `Bearer ${bearerToken}`,
			},
			maxBodyLength: Infinity,
			method: 'post',
			url: `${oauth2JWKSURI}/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/publish`,
		};
		axios
			.request(config)
			.then((response) => {
				resolve(response.data);
			})
			.catch((error) => {
				reject(error.message);
			});
	});

	return prom;
}
