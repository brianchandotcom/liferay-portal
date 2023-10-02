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
		activityDocumentId: mdfClaimActivityDocumentDTO.id,
		documentId: mdfClaimActivityDocumentDTO.proofOfPerformanceFile?.id,
		link: mdfClaimActivityDocumentDTO.proofOfPerformanceFile?.link,
		name:
			mdfClaimActivityDocumentDTO.proofOfPerformanceFile?.name &&
			getNameFromMDFClaimDocument(
				mdfClaimActivityDocumentDTO.proofOfPerformanceFile.name
			),
	};
}
