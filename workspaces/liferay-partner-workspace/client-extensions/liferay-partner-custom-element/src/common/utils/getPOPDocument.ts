/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaimActivityDocumentDTO from '../interfaces/dto/mdfClaimActivityDocumentDTO';
import LiferayFile from '../interfaces/liferayFile';
import getNameFromMDFClaimDocument from './getNameFromMDFClaimDocument';

export function getPOPDocument(
	mdfClaimActivityDocumentDTO: MDFClaimActivityDocumentDTO
): LiferayFile {
	return {
		documentId: mdfClaimActivityDocumentDTO.proofOfPerformanceFiles?.id,
		link: mdfClaimActivityDocumentDTO.proofOfPerformanceFiles?.link,
		name:
			mdfClaimActivityDocumentDTO.proofOfPerformanceFiles?.name &&
			getNameFromMDFClaimDocument(
				mdfClaimActivityDocumentDTO.proofOfPerformanceFiles.name
			),
		objectId: mdfClaimActivityDocumentDTO.id,
	};
}
