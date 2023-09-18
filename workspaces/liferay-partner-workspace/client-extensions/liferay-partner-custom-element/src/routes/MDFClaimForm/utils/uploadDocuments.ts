/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayFile from '../../../common/interfaces/liferayFile';
import uploadDocument from './uploadDocument';

const uploadDocuments = async (
	documents: LiferayFile[],
	claimParentFolderId: number
) => {
	const liferayFiles: LiferayFile[] & number[] = [];

	for (const document of documents) {
		if (!document.documentId) {
			document.documentId = await uploadDocument(
				document,
				claimParentFolderId
			);
		}
		liferayFiles.push(document);
	}

	return liferayFiles;
};

export default uploadDocuments;
