/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../utils/constants';
import {request} from '../utils/request';
import {getHostUrl, showError, showSuccess} from '../utils/util';
import {
	deleteFolderTemplateNodesBatch,
	getAvailableTemplatesNodesPage,
} from './TemplateDiagramService';

export async function deleteFolderTemplateInformation(
	FolderTemplateInformationId
) {
	try {
		const templateNodes = await getAvailableTemplatesNodesPage(
			FolderTemplateInformationId
		);

		if (templateNodes.items.length) {
			await deleteFolderTemplateNodesBatch(templateNodes.items);
		}

		await deleteFolderTemplateInformationItem(FolderTemplateInformationId);

		showSuccess(
			`Template ${FolderTemplateInformationId} has been deleted!`
		);
	}
	catch (error) {
		showError('Error', error.message);
	}
}

export async function deleteFolderTemplateInformationItem(
	folderTemplateInformationId
) {
	return request({
		method: 'delete',
		url: `${getHostUrl()}/${
			config.templateInfoApi
		}/${folderTemplateInformationId}`,
	});
}

export async function getAvailableTemplatesPage(page, pageSize) {
	return request({
		url: `${getHostUrl()}/${
			config.templateInfoApi
		}?page=${page}&pageSize=${pageSize}`,
	});
}

export async function postFolderTemplateInformation(folderTemplateInformation) {
	return request({
		data: folderTemplateInformation,
		method: 'post',
		url: `${getHostUrl()}/${config.templateInfoApi}`,
	});
}
