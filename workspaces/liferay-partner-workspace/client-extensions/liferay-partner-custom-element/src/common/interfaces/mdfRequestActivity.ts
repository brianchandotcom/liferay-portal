/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';
import MDFRequestActivityDescription from './mdfRequestActivityDescription';
import MDFRequestBudget from './mdfRequestBudget';

export default interface MDFRequestActivity extends Partial<LiferayObject> {
	activityDescription?: MDFRequestActivityDescription;
	activityStatus?: LiferayPicklist;
	budgets: MDFRequestBudget[];
	claimPercent: number;
	currency?: LiferayPicklist;
	endDate?: string;
	externalReferenceCode?: string;
	externalReferenceCodeSF?: string;
	mdfRequestAmount: number;
	name: string;
	removed?: boolean;
	startDate?: string;
	submitted?: boolean;
	tactic: LiferayPicklist;
	totalCostOfExpense: number;
	typeActivity: LiferayPicklist;
}
