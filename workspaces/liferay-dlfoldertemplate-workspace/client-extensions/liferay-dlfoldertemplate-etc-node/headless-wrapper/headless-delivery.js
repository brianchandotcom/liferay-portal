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

export function headless_deliveryService(_token) {
	const token = _token;
	function getDocumentFolder(documentFolderId) {
		const prom = new Promise((resolve, reject) => {
			const requestHeaders = new Headers();
			requestHeaders.append('Authorization', `Bearer ${token}`);
			const requestOptions = {
				headers: requestHeaders,
				method: 'GET',
				redirect: 'follow',
			};
			fetch(
				`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}`,
				requestOptions
			)
				.then((response) => response.json())
				.then((result) => resolve(result))
				.catch((error) => reject(error));
		});

		return prom;
	}
	function postDocumentFolderDocumentFolder(
		parentDocumentFolderId,
		DocumentFolder
	) {
		const prom = new Promise((resolve, reject) => {
			const requestHeaders = new Headers();
			requestHeaders.append('Authorization', `Bearer ${token}`);
			requestHeaders.append('Content-Type', 'application/json');
			const requestOptions = {
				body: JSON.stringify(DocumentFolder),
				headers: requestHeaders,
				method: 'POST',
				redirect: 'follow',
			};
			fetch(
				`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${parentDocumentFolderId}/document-folders`,
				requestOptions
			)
				.then((response) => response.json())
				.then((result) => resolve(result))
				.catch((error) => reject(error));
		});

		return prom;
	}

	return Object.freeze({
		getDocumentFolder,
		postDocumentFolderDocumentFolder,
	});
}
