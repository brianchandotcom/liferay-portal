/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {request} from '../../utils/request';
import {getCurrentSite} from '../../utils/util';

export async function getRootFolders(page = 0, pageSize = 0) {
	const endPoint = `/o/headless-delivery/v1.0/sites/${getCurrentSite()}/document-folders?page=${page}&pageSize=${pageSize}`;

	return request({
		method: 'GET',
		url: `${endPoint}`,
	});
}

export async function getRootFiles(page = 0, pageSize = 0) {
	const endPoint = `/o/headless-delivery/v1.0/sites/${getCurrentSite()}/documents?page=${page}&pageSize=${pageSize}`;

	return request({
		method: 'GET',
		url: `${endPoint}`,
	});
}

export async function getFolderFiles(folderId, page = 0, pageSize = 0) {
	const endPoint = `/o/headless-delivery/v1.0/document-folders/${folderId}/documents?page=${page}&pageSize=${pageSize}`;

	return request({
		method: 'GET',
		url: `${endPoint}`,
	});
}

export async function getFolderSubfolders(folderId, page = 0, pageSize = 0) {
	const endPoint = `/o/headless-delivery/v1.0/document-folders/${folderId}/document-folders?page=${page}&pageSize=${pageSize}`;

	return request({
		method: 'GET',
		url: `${endPoint}`,
	});
}
