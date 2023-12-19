/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {request} from '../utils/request';
import {getHostUrl} from '../utils/util';

export async function getDocumentFolderDocumentFoldersPage(parentFolderId) {
	return request({
		url: `${getHostUrl()}/o/headless-delivery/v1.0/document-folders/${parentFolderId}/document-folders?page=0`,
	});
}

export async function getSiteDocumentFoldersPage(siteId) {
	return request({
		url: `${getHostUrl()}/o/headless-delivery/v1.0/sites/${siteId}/document-folders?page=0`,
	});
}
