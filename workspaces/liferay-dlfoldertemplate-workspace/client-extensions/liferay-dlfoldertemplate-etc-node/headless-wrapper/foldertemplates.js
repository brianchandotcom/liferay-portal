/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetch from 'node-fetch';

import config from '../util/configTreePath.js';
const lxcDXPMainDomain =
	config['com.liferay.lxc.dxp.mainDomain'] ||
	config['com.liferay.sh.dxp.mainDomain'];
const lxcDXPServerProtocol =
	config['com.liferay.lxc.dxp.server.protocol'] ||
	config['com.liferay.sh.dxp.server.protocol'];
const oauth2JWKSURI = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}`;

export function foldertemplatesService(_token) {
	const token = _token;
	function getFolderTemplatesPage(templateID) {
		const prom = new Promise((resolve, reject) => {
			const requestHeaders = new Headers();
			requestHeaders.append('Authorization', `Bearer ${token}`);
			const requestOptions = {
				headers: requestHeaders,
				method: 'GET',
				redirect: 'follow',
			};
			fetch(
				`${oauth2JWKSURI}/o/c/t4t14foldertemplatenodes/?filter=templateID eq ${templateID}&page=0`,
				requestOptions
			)
				.then((response) => response.json())
				.then((result) => resolve(result))
				.catch((error) => reject(error));
		});

		return prom;
	}
	function postFolderTemplate(FolderTemplate) {
		const prom = new Promise((resolve, reject) => {
			const requestHeaders = new Headers();
			requestHeaders.append('Authorization', `Bearer ${token}`);
			requestHeaders.append('Content-Type', 'application/json');
			const requestOptions = {
				body: JSON.stringify(FolderTemplate),
				headers: requestHeaders,
				method: 'POST',
				redirect: 'follow',
			};
			fetch(`${oauth2JWKSURI}/o/c/foldertemplates/`, requestOptions)
				.then((response) => response.json())
				.then((result) => resolve(result))
				.catch((error) => reject(error));
		});

		return prom;
	}
	function getFolderTemplate(
		folderTemplateId,
		fields = null,
		nestedFields = null,
		restrictFields = null
	) {
		const prom = new Promise((resolve, reject) => {
			const requestHeaders = new Headers();
			requestHeaders.append('Authorization', `Bearer ${token}`);
			const requestOptions = {
				headers: requestHeaders,
				method: 'GET',
				redirect: 'follow',
			};
			fetch(
				`${oauth2JWKSURI}/o/c/foldertemplates/${folderTemplateId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`,
				requestOptions
			)
				.then((response) => response.json())
				.then((result) => resolve(result))
				.catch((error) => reject(error));
		});

		return prom;
	}

	return Object.freeze({
		getFolderTemplate,
		getFolderTemplatesPage,
		postFolderTemplate,
	});
}
