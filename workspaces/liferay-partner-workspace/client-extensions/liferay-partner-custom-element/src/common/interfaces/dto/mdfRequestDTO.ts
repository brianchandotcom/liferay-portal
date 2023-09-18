/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AccountEntry from '../accountEntry';
import MDFRequest from '../mdfRequest';
import User from '../user';
import MDFClaimDTO from './mdfClaimDTO';
import MDFRequestActivityDTO from './mdfRequestActivityDTO';

export default interface MDFRequestDTO
	extends Omit<
		MDFRequest,
		| 'activities'
		| 'liferayBusinessSalesGoals'
		| 'targetAudienceRoles'
		| 'targetMarkets'
		| 'company'
	> {
	companyName?: string;
	emailAddress?: string;
	externalReferenceCode?: string;
	externalReferenceCodeSF?: string;
	liferayBusinessSalesGoals?: string;
	liferayBusinessSalesGoalsOther?: string;
	liferaysUserIdSF?: number;
	mdfReqToActs?: MDFRequestActivityDTO[];
	mdfReqToMDFClms?: MDFClaimDTO[];
	r_accToMDFReqs_accountEntry?: AccountEntry;
	r_accToMDFReqs_accountEntryERC?: string;
	r_accToMDFReqs_accountEntryId?: number;
	r_usrToMDFReqs_user?: User;
	r_usrToMDFReqs_userId?: number;
	targetAudienceRoles?: string;
	targetMarkets?: string;
	totalClaimedRequest?: string;
	totalCostOfExpense: number;
	totalMDFRequestAmount: number;
	totalPaidAmount?: string;
	totalRequested?: number;
}
