/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '..';

import LiferayDocument from '../../../interfaces/liferayDocument';
import {LiferayAPIs} from '../common/enums/apis';
import liferayFetcher from '../common/utils/fetcher';

export default async function createDocumentFolderDocument(
	documentFolderId: number,
	file: Blob
) {
	const formData = new FormData();
	formData.append('file', file);

	return liferayFetcher.post<LiferayDocument>(
		`/o/${LiferayAPIs.HEADERLESS_DELIVERY}/document-folders/${documentFolderId}/documents`,
		Liferay.authToken,
		{},
		{
			body: formData,
			headers: {},
		}
	);
}
