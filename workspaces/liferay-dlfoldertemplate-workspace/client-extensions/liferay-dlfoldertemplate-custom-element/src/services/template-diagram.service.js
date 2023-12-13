/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import axios from 'axios';

import {ApplicationUtil} from '../utils/appUtil';

export async function getAvailableTemplatesNodesPage(templateID) {
	const requestConfig = {
		headers: {
			'x-csrf-token': ApplicationUtil.getLiferay().authToken,
		},
		maxBodyLength: Infinity,
		method: 'get',
		url: `${ApplicationUtil.getHostUrl()}/${
			ApplicationUtil.getConfig().templateNodeApi
		}?page=0&filter=templateID eq ${templateID}`,
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
export async function addNode(
	parentNode,
	root = false,
	name = 'Unnamed',
	template
) {
	try {
		const result = await postFolderTemplate({
			description: '',
			name,
			parentID: parentNode,
			root,
			templateID: template,
		});
		const node = {
			description: result.description,
			id: result.id,
			name: result.name,
			pid: result.parentID,
			root,
			templateID: result.templateID,
		};

		return node;
	}
	catch (error) {
		ApplicationUtil.ShowError(error);
	}
}
export async function updateFolderTemplate(nodeId, FolderTemplate) {
	const requestConfig = {
		data: FolderTemplate,
		headers: {
			'x-csrf-token': ApplicationUtil.getLiferay().authToken,
		},
		maxBodyLength: Infinity,
		method: 'patch',
		url: `${ApplicationUtil.getHostUrl()}/${
			ApplicationUtil.getConfig().templateNodeApi
		}/${nodeId}`,
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
export async function postFolderTemplate(FolderTemplate) {
	const requestConfig = {
		data: FolderTemplate,
		headers: {
			'x-csrf-token': ApplicationUtil.getLiferay().authToken,
		},
		maxBodyLength: Infinity,
		method: 'post',
		url: `${ApplicationUtil.getHostUrl()}/${
			ApplicationUtil.getConfig().templateNodeApi
		}`,
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
export async function deleteFolderTemplateBatch(data) {
	const requestConfig = {
		data,
		headers: {
			'x-csrf-token': ApplicationUtil.getLiferay().authToken,
		},
		maxBodyLength: Infinity,
		method: 'delete',
		url: `${ApplicationUtil.getHostUrl()}/${
			ApplicationUtil.getConfig().templateNodeApi
		}/batch`,
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
export async function deleteFolderTemplate(nodeId) {
	const requestConfig = {
		headers: {
			'x-csrf-token': ApplicationUtil.getLiferay().authToken,
		},
		maxBodyLength: Infinity,
		method: 'delete',
		url: `${ApplicationUtil.getHostUrl()}/${
			ApplicationUtil.getConfig().templateNodeApi
		}/${nodeId}`,
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
