/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaimDTO from '../../../interfaces/dto/mdfClaimDTO';
import MDFClaim from '../../../interfaces/mdfClaim';
import getPOPFromMDFActDocs from '../../getPOPFromMDFActDocs';
import {getLiferayFileFromAttachment} from './getLiferayFileFromAttachment';

export function getMDFClaimFromDTO(mdfClaim: MDFClaimDTO): MDFClaim {
	return {
		...mdfClaim,
		activities:
			mdfClaim?.mdfClmToMDFClmActs?.map((activityItem) => {
				const {
					currency,
					eventProgram,
					externalReferenceCode,
					id,
					listOfQualifiedLeads,
					metrics,
					r_actToMDFClmActs_c_activityId,
					r_mdfClmToMDFClmActs_c_mdfClaimId,
					selected,
					telemarketingMetrics,
					telemarketingScript,
					totalCost,
					typeActivity,
					videoLink,
				} = activityItem;

				return {
					budgets: activityItem.mdfClmActToMDFClmBgts?.map(
						(budgetItem) => {
							const {
								expenseName,
								externalReferenceCode,
								id,
								invoice,
								invoiceAmount,
								r_bgtToMDFClmBgts_c_budgetId,
								selected,
							} = budgetItem;

							return {
								expenseName,
								externalReferenceCode,
								id,
								invoice:
									invoice &&
									getLiferayFileFromAttachment(invoice),
								invoiceAmount,
								r_bgtToMDFClmBgts_c_budgetId,
								selected,
							};
						}
					),
					currency,
					eventProgram:
						eventProgram &&
						getLiferayFileFromAttachment(eventProgram),
					externalReferenceCode,
					id,
					listOfQualifiedLeads:
						listOfQualifiedLeads &&
						getLiferayFileFromAttachment(listOfQualifiedLeads),
					metrics,
					proofOfPerformance: getPOPFromMDFActDocs(activityItem),
					r_actToMDFClmActs_c_activityId,
					r_mdfClmToMDFClmActs_c_mdfClaimId,
					selected,
					telemarketingMetrics,
					telemarketingScript:
						telemarketingScript &&
						getLiferayFileFromAttachment(telemarketingScript),
					totalCost,
					typeActivity,
					videoLink,
				};
			}) || [],
		reimbursementInvoice:
			mdfClaim.reimbursementInvoice &&
			getLiferayFileFromAttachment(mdfClaim.reimbursementInvoice),
	};
}
