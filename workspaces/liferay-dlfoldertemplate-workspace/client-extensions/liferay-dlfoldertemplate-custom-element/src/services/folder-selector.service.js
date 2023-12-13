import axios from 'axios';

import {ApplicationUtil} from '../utils/appUtil';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export async function getSiteDocumentFoldersPage(siteId) {
	const requestConfig = {
		headers: {
			'x-csrf-token': ApplicationUtil.getLiferay().authToken,
		},
		maxBodyLength: Infinity,
		method: 'get',
		url: `${ApplicationUtil.getHostUrl()}/o/headless-delivery/v1.0/sites/${siteId}/document-folders?page=0`,
	};
	const prom = new Promise((resolve, reject) => {
		axios
			.request(requestConfig)
			.then((response) => {
				resolve(response.data);
			})
			.catch((error) => {
				reject(error);
			});
	});

	return prom;
}
export async function getDocumentFolderDocumentFoldersPage(parentFolderId) {
	const requestConfig = {
		headers: {
			'x-csrf-token': ApplicationUtil.getLiferay().authToken,
		},
		maxBodyLength: Infinity,
		method: 'get',
		url: `${ApplicationUtil.getHostUrl()}/o/headless-delivery/v1.0/document-folders/${parentFolderId}/document-folders?page=0`,
	};
	const prom = new Promise((resolve, reject) => {
		axios
			.request(requestConfig)
			.then((response) => {
				resolve(response.data);
			})
			.catch((error) => {
				reject(error);
			});
	});

	return prom;
}
