/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaimDTO from '../../../interfaces/dto/mdfClaimDTO';
import MDFClaim from '../../../interfaces/mdfClaim';
import getInvoiceFromMDFClmDocs from '../../getInvoiceFromMDFClmDocs';
import getPOPFromMDFActDocs from '../../getPOPFromMDFActDocs';
import {getLiferayFileFromAttachment} from './getLiferayFileFromAttachment';

export function getMDFClaimFromDTO(mdfClaim: MDFClaimDTO): MDFClaim {
	return {
		...mdfClaim,
		activities:
			mdfClaim?.mdfClmToMDFClmActs?.map((activityItem) => {
				const {
					currency,
					eventProgramFile,
					externalReferenceCode,
					id,
					listOfQualifiedLeadsFile,
					metrics,
					r_actToMDFClmActs_c_activityId,
					r_mdfClmToMDFClmActs_c_mdfClaimId,
					selected,
					telemarketingMetrics,
					telemarketingScriptFile,
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
								invoiceAmount,
								invoiceFile,
								r_bgtToMDFClmBgts_c_budgetId,
								selected,
							} = budgetItem;

							return {
								expenseName,
								externalReferenceCode,
								id,
								invoiceAmount,
								invoiceFile:
									invoiceFile &&
									getLiferayFileFromAttachment(invoiceFile),
								r_bgtToMDFClmBgts_c_budgetId,
								selected,
							};
						}
					),
					currency,
					eventProgramFile:
						eventProgramFile &&
						getLiferayFileFromAttachment(eventProgramFile),
					externalReferenceCode,
					id,
					listOfQualifiedLeadsFile:
						listOfQualifiedLeadsFile &&
						getLiferayFileFromAttachment(listOfQualifiedLeadsFile),
					metrics,
					proofOfPerformance: getPOPFromMDFActDocs(activityItem),
					r_actToMDFClmActs_c_activityId,
					r_mdfClmToMDFClmActs_c_mdfClaimId,
					selected,
					telemarketingMetrics,
					telemarketingScriptFile:
						telemarketingScriptFile &&
						getLiferayFileFromAttachment(telemarketingScriptFile),
					totalCost,
					typeActivity,
					videoLink,
				};
			}) || [],
		reimbursementInvoices: getInvoiceFromMDFClmDocs(mdfClaim),
	};
}
