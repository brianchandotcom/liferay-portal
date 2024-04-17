/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';
import MDFClaimActivity from './mdfClaimActivity';
import MDFClaimDocument from './mdfClaimDocument';

export default interface MDFClaim extends Partial<LiferayObject> {
	activities?: MDFClaimActivity[];
	claimPaid?: number;
	convertedClaimPaid?: number;
	convertedTotalClaimAmount?: number;
	currency: LiferayPicklist;
	externalReferenceCode?: string;
	externalReferenceCodeSF?: string;
	mdfClaimStatus: LiferayPicklist;
	partial?: boolean;
	r_mdfReqToMDFClms_c_mdfRequestId: number;
	r_usrToMDFClms_userId?: number;
	reimbursementInvoices?: MDFClaimDocument[];
	submitDate?: string;
	submitted?: boolean;
	totalClaimAmount?: number;
	totalMDFRequestedAmount?: number;
}
