/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaimDTO from '../../../interfaces/dto/mdfClaimDTO';
import MDFRequestDTO from '../../../interfaces/dto/mdfRequestDTO';
import MDFClaim from '../../../interfaces/mdfClaim';

export function getDTOFromMDFClaim(
	mdfClaim: MDFClaim,
	mdfRequest: MDFRequestDTO,
	externalReferenceCodeFromSF?: string
): MDFClaimDTO {
	return {
		companyName: mdfRequest.r_accToMDFReqs_accountEntry?.name,
		currency: mdfClaim.currency,
		externalReferenceCode: externalReferenceCodeFromSF,
		mdfClaimStatus: mdfClaim.mdfClaimStatus,
		mdfRequestExternalReferenceCode: mdfRequest?.externalReferenceCode,
		mdfRequestTotalCostOfExpense: mdfRequest.totalCostOfExpense,
		partial: mdfClaim.partial,
		r_accToMDFClms_accountEntryId:
			mdfRequest.r_accToMDFReqs_accountEntry?.id,
		r_mdfReqToMDFClms_c_mdfRequestId:
			mdfClaim.r_mdfReqToMDFClms_c_mdfRequestId,
		reimbursementInvoice: mdfClaim.reimbursementInvoice?.documentId,
		totalClaimAmount: mdfClaim.totalClaimAmount,
		totalMDFRequestedAmount: mdfClaim.totalMDFRequestedAmount,
	};
}
