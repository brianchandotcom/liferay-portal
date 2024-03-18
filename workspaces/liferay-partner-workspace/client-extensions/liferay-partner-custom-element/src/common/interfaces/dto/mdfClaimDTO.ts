/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AccountEntry from '../accountEntry';
import LiferayPicklist from '../liferayPicklist';
import MDFClaim from '../mdfClaim';
import MDFClaimActivityDTO from './mdfClaimActivityDTO';
import MDFClaimDocument from './mdfClaimDocument';

export default interface MDFClaimDTO extends MDFClaim {
	claimPaid?: number;
	companyName?: string;
	currency: LiferayPicklist;
	externalReferenceCode?: string;
	externalReferenceCodeSF?: string;
	mdfClaimStatus: LiferayPicklist;
	mdfClmToMDFClmActs?: MDFClaimActivityDTO[];
	mdfClmToMDFClmDocs?: MDFClaimDocument[];
	mdfRequestExternalReferenceCode?: string;
	mdfRequestTotalCostOfExpense?: number;
	partial?: boolean;
	paymentDate?: string;
	r_accToMDFClms_accountEntry?: AccountEntry;
	r_accToMDFClms_accountEntryId?: number;
	r_mdfReqToMDFClms_c_mdfRequestId: number;
	totalClaimAmount?: number;
}
