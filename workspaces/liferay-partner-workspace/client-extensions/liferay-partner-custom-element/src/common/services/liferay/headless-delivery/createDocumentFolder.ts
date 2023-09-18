/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '..';

import LiferayDocumentFolder from '../../../interfaces/liferayDocumentFolder';
import {LiferayAPIs} from '../common/enums/apis';
import liferayFetcher from '../common/utils/fetcher';

export default async function createDocumentFolder(
	parentDocumentFolderId: number,
	folderName: string
) {
	return liferayFetcher.post<LiferayDocumentFolder>(
		`/o/${LiferayAPIs.HEADERLESS_DELIVERY}/document-folders/${parentDocumentFolderId}/document-folders`,
		Liferay.authToken,
		{
			name: folderName,
		}
	);
}
