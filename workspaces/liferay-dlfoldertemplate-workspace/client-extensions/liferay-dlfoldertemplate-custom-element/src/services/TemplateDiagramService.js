/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../utils/constants';
import {request} from '../utils/request';
import {getHostUrl, showError} from '../utils/util';

export async function addFolderTemplateNode(
	parentNode,
	root = false,
	name = 'Unnamed',
	template
) {
	try {
		const result = await postFolderTemplateNode({
			description: '',
			name,
			parentId: parentNode,
			root,
			templateId: template,
		});

		const node = {
			description: result.description,
			id: result.id,
			name: result.name,
			pid: result.parentId,
			root,
			templateId: result.templateId,
		};

		return node;
	}
	catch (error) {
		showError('Error', error);
	}
}

export async function deleteFolderTemplateNodesBatch(data) {
	return request({
		data,
		method: 'delete',
		url: `${getHostUrl()}/${config.templateNodeApi}/batch`,
	});
}

export async function getAvailableTemplatesNodesPage(templateId) {
	return request({
		url: `${getHostUrl()}/${
			config.templateNodeApi
		}?page=0&filter=templateId eq ${templateId}`,
	});
}

export async function postFolderTemplateNode(FolderTemplate) {
	return request({
		data: FolderTemplate,
		method: 'post',
		url: `${getHostUrl()}/${config.templateNodeApi}`,
	});
}

export async function updateFolderTemplateNode(nodeId, FolderTemplate) {
	return request({
		data: FolderTemplate,
		method: 'patch',
		url: `${getHostUrl()}/${config.templateNodeApi}/${nodeId}`,
	});
}
