/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayFile from './liferayFile';
import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';
import MDFClaimActivity from './mdfClaimActivity';

export default interface MDFClaim extends Partial<LiferayObject> {
	activities?: MDFClaimActivity[];
	currency: LiferayPicklist;
	externalReferenceCode?: string;
	externalReferenceCodeSF?: string;
	mdfClaimStatus: LiferayPicklist;
	partial?: boolean;
	r_mdfReqToMDFClms_c_mdfRequestId: number;
	reimbursementInvoice?: LiferayFile & number;
	submitted?: boolean;
	totalClaimAmount?: number;
	totalMDFRequestedAmount?: number;
}
