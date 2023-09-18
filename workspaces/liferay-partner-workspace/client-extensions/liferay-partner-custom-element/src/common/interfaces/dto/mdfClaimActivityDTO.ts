/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaimActivity from '../mdfClaimActivity';
import MDFClaimActivityDocumentDTO from './mdfClaimActivityDocumentDTO';
import MDFClaimBudgetDTO from './mdfClaimBudgetDTO';
import MDFClaimDTO from './mdfClaimDTO';

export default interface MDFClaimActivityDTO extends MDFClaimActivity {
	mdfClmActToMDFActDocs?: MDFClaimActivityDocumentDTO[];
	mdfClmActToMDFClmBgts?: MDFClaimBudgetDTO[];
	r_accToMDFClmActs_accountEntryId?: number;
	r_mdfClmToMDFClmActs_c_mdfClaim?: MDFClaimDTO;
}
