/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestBudget from '../mdfRequestBudget';

export default interface MDFRequestBudgetDTO extends MDFRequestBudget {
	r_accToBgts_accountEntryERC?: string;
	r_accToBgts_accountEntryId?: number;
	r_actToBgts_c_activityERC?: string;
	r_actToBgts_c_activityId?: number;
}
