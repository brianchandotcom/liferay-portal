/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayPicklist from '../liferayPicklist';
import MDFRequestActivity from '../mdfRequestActivity';
import MDFRequestActivityDescription from '../mdfRequestActivityDescription';
import MDFClaimActivityDTO from './mdfClaimActivityDTO';
import MDFRequestBudgetDTO from './mdfRequestBudgetDTO';

type MDFRequestActivityDTO = Omit<
	MDFRequestActivity,
	'activityDescription' | 'budgets'
> &
	Omit<MDFRequestActivityDescription, 'leadFollowUpStrategies'> & {
		actToBgts?: MDFRequestBudgetDTO[];
		actToMDFClmActs?: MDFClaimActivityDTO[];
		currency?: LiferayPicklist;
		externalReferenceCode?: string;
		leadFollowUpStrategies?: string;
		mdfRequestExternalReferenceCode?: string;
		r_accToActs_accountEntryERC?: string;
		r_accToActs_accountEntryId?: number;
		r_mdfReqToActs_c_mdfRequestERC?: string;
		r_mdfReqToActs_c_mdfRequestId?: number;
		selected?: boolean;
	};

export default MDFRequestActivityDTO;
