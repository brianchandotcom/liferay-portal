/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaimBudgetDTO from '../../../interfaces/dto/mdfClaimBudgetDTO';
import MDFClaimBudget from '../../../interfaces/mdfClaimBudget';

export default function getDTOFromMDFClaimBudget(
	mdfClaimBudget: MDFClaimBudget,
	mdfClaimActivityId: number,
	companyId: number
): MDFClaimBudgetDTO {
	return {
		expenseName: mdfClaimBudget.expenseName,
		invoice: mdfClaimBudget.invoice?.documentId,
		invoiceAmount: mdfClaimBudget.invoiceAmount,
		r_accToMDFClmBgts_accountEntryId: companyId,
		r_bgtToMDFClmBgts_c_budgetId:
			mdfClaimBudget.r_bgtToMDFClmBgts_c_budgetId,
		r_mdfClmActToMDFClmBgts_c_mdfClaimActivityId: mdfClaimActivityId,
		selected: mdfClaimBudget.selected,
	};
}
