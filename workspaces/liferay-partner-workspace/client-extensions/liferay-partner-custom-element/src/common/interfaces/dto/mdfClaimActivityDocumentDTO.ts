/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayFile from '../liferayFile';
import MDFClaimActivityDocument from '../mdfClaimActivityDocument';

export default interface MDFClaimActivityDocumentDTO
	extends Omit<
		MDFClaimActivityDocument,
		| 'allContents'
		| 'eventCollaterals'
		| 'eventInvitations'
		| 'eventPhotos'
		| 'images'
	> {
	allContents?: LiferayFile & number;
	eventCollaterals?: LiferayFile & number;
	eventInvitations?: LiferayFile & number;
	eventPhotos?: LiferayFile & number;
	id?: number;
	images?: LiferayFile & number;
	proofOfPerformanceFile?: LiferayFile & number;
	r_accToMDFClmActDocs_accountEntryId?: number;
	r_mdfClmActToMDFActDocs_c_mdfClaimActivityId?: number;
}
