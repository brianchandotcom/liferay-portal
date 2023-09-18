/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayFile from '../../../common/interfaces/liferayFile';
import createDocumentFolderDocument from '../../../common/services/liferay/headless-delivery/createDocumentFolderDocument';
import generateRandonNumber from './generateRandonNumber';
import renameFileKeepingExtention from './renameFileKeepingExtention';

const uploadDocument = async (
	document: LiferayFile,
	claimParentFolderId: number
) => {
	const allContentDocumentRenamed = renameFileKeepingExtention(
		document,
		`${document.name}#${generateRandonNumber()}`
	);

	if (allContentDocumentRenamed) {
		const dtoAllContentDocument = await createDocumentFolderDocument(
			claimParentFolderId,
			allContentDocumentRenamed
		);

		return dtoAllContentDocument.id;
	}
};

export default uploadDocument;
