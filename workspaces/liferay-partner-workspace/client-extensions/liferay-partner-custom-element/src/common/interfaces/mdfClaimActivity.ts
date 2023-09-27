/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayFile from './liferayFile';
import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';
import MDFClaimActivityDocument from './mdfClaimActivityDocument';
import MDFClaimBudget from './mdfClaimBudget';

export default interface MDFClaimActivity extends Partial<LiferayObject> {
	activityStatus?: LiferayPicklist;
	budgets?: MDFClaimBudget[];
	claimed?: boolean;
	currency?: LiferayPicklist;
	eventProgram?: LiferayFile & number;
	listOfQualifiedLeads?: LiferayFile & number;
	metrics: string;
	name?: string;
	proofOfPerformance?: MDFClaimActivityDocument;
	r_actToMDFClmActs_c_activityId?: number;
	r_mdfClmToMDFClmActs_c_mdfClaimId?: number;
	selected: boolean;
	telemarketingMetrics?: string;
	telemarketingScript?: LiferayFile & number;
	totalCost: number;
	typeActivity: LiferayPicklist;
	videoLink?: string;
}
