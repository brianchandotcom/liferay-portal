/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaimBudget from '../mdfClaimBudget';

export default interface MDFClaimBudgetDTO extends MDFClaimBudget {
	r_accToMDFClmBgts_accountEntryId?: number;
	r_bgtToMDFClmBgts_c_budgetId?: number;
	r_mdfClmActToMDFClmBgts_c_mdfClaimActivityId?: number;
}
