/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetch from 'node-fetch';

import {getConfigByKey} from '../util/config-util.js';
import {
	applicationSpecificConfigKeys,
	environmentConfigKeys,
} from '../util/constants.js';
import {getServerToken} from '../util/silent-authorization.js';

const lxcDXPMainDomain = getConfigByKey(
	environmentConfigKeys.COM_LIFERAY_LXC_DXP_MAIN_DOMAIN
);

const lxcDXPServerProtocol = getConfigByKey(
	environmentConfigKeys.COM_LIFERAY_LXC_DXP_SERVER_PROTOCOL
);

const oauth2JWKSURI = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}`;

export async function getFolderTemplateNodesPage(templateID) {
	const token = await getServerToken();

	const prom = new Promise((resolve, reject) => {
		const requestHeaders = new Headers();

		requestHeaders.append('Authorization', `Bearer ${token}`);

		const requestOptions = {
			headers: requestHeaders,
			method: 'GET',
			redirect: 'follow',
		};

		fetch(
			`${oauth2JWKSURI}${getConfigByKey(
				applicationSpecificConfigKeys.FOLDER_TEMPLATE_NODES_END_POINT
			)}?filter=templateId eq ${templateID}&page=0`,
			requestOptions
		)
			.then((response) => response.json())
			.then((result) => resolve(result.items))
			.catch((error) => reject(error));
	});

	return prom;
}

export async function postDocumentFolder(
	parentDocumentFolderId,
	DocumentFolder
) {
	const token = await getServerToken();

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
