/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import axios from 'axios';

import {ApplicationUtil} from '../utils/appUtil';
import {
	deleteFolderTemplateBatch,
	getAvailableTemplatesNodesPage,
} from './template-diagram.service';

export async function getAvailableTemplatesPage(page, pageSize) {
	const requestConfig = {
		headers: {
			'x-csrf-token': ApplicationUtil.getLiferay().authToken,
		},
		maxBodyLength: Infinity,
		method: 'get',
		url: `${ApplicationUtil.getHostUrl()}/${
			ApplicationUtil.getConfig().templateInfoApi
		}?page=${page}&pageSize=${pageSize}`,
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
export async function postFolderTemplateInformation(FolderTemplateInformation) {
	const requestConfig = {
		data: FolderTemplateInformation,
		headers: {
			'x-csrf-token': ApplicationUtil.getLiferay().authToken,
		},
		maxBodyLength: Infinity,
		method: 'post',
		url: `${ApplicationUtil.getHostUrl()}/${
			ApplicationUtil.getConfig().templateInfoApi
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
export async function deleteFolderTemplateInformationItem(
	FolderTemplateInformationId
) {
	const requestConfig = {
		headers: {
			'x-csrf-token': ApplicationUtil.getLiferay().authToken,
		},
		maxBodyLength: Infinity,
		method: 'delete',
		url: `${ApplicationUtil.getHostUrl()}/${
			ApplicationUtil.getConfig().templateInfoApi
		}/${FolderTemplateInformationId}`,
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
export async function deleteFolderTemplateInformation(
	FolderTemplateInformationId
) {
	try {
		const templateNodes = await getAvailableTemplatesNodesPage(
			FolderTemplateInformationId
		);
		if (templateNodes.items.length) {
			await deleteFolderTemplateBatch(templateNodes.items);
		}
		await deleteFolderTemplateInformationItem(FolderTemplateInformationId);
		ApplicationUtil.ShowSuccess(
			`Template ${FolderTemplateInformationId} has been deleted!`
		);
	}
	catch (error) {
		ApplicationUtil.ShowError(error.message);
	}
}
