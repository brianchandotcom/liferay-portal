/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../utils/constants';
import {request} from '../utils/request';
import {getHostUrl, showError} from '../utils/util';

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
		showError(error);
	}
}

export async function deleteFolderTemplate(nodeId) {
	return request({
		method: 'delete',
		url: `${getHostUrl()}/${config.templateNodeApi}/${nodeId}`,
	});
}

export async function deleteFolderTemplateBatch(data) {
	return request({
		data,
		method: 'delete',
		url: `${getHostUrl()}/${config.templateNodeApi}/batch`,
	});
}

export async function getAvailableTemplatesNodesPage(templateID) {
	return request({
		url: `${getHostUrl()}/${
			config.templateNodeApi
		}?page=0&filter=templateID eq ${templateID}`,
	});
}

export async function postFolderTemplate(FolderTemplate) {
	return request({
		data: FolderTemplate,
		method: 'post',
		url: `${getHostUrl()}/${config.templateNodeApi}`,
	});
}

export async function updateFolderTemplate(nodeId, FolderTemplate) {
	return request({
		data: FolderTemplate,
		method: 'patch',
		url: `${getHostUrl()}/${config.templateNodeApi}/${nodeId}`,
	});
}
