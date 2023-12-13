/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import axios from 'axios';

import {ApplicationUtil} from '../utils/appUtil';

export async function createFolder(templateId, containerId, rootName) {
	const requestConfig = {
		headers: {
			Authorization: `Bearer ${await ApplicationUtil.getOAuthToken()}`,
		},
		maxBodyLength: Infinity,
		method: 'post',
		url: `${ApplicationUtil.suggestServerUrl()}/${
			ApplicationUtil.getConfig()['folder.generate.service.url']
		}/${templateId}/${containerId}/${rootName}`,
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
