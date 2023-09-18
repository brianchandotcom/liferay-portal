/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaimActivityDocumentDTO from '../../../interfaces/dto/mdfClaimActivityDocumentDTO';
import LiferayFile from '../../../interfaces/liferayFile';
import LiferayPicklist from '../../../interfaces/liferayPicklist';

export default function getDocumentDTOFromLiferayFile(
	liferayFile: LiferayFile,
	proofOfPerformanceType: LiferayPicklist,
	companyId: number,
	dtoMDFClaimActivityId: number
): MDFClaimActivityDocumentDTO {
	return {
		id: liferayFile.activityDocumentId,
		proofOfPerformanceFile: liferayFile.documentId,
		proofOfPerformanceType,
		r_accToMDFClmActDocs_accountEntryId: companyId,
		r_mdfClmActToMDFActDocs_c_mdfClaimActivityId: dtoMDFClaimActivityId,
	};
}
