/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayAccountBrief from './liferayAccountBrief';
import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';
import MDFRequestActivity from './mdfRequestActivity';

export default interface MDFRequest extends Partial<LiferayObject> {
	accountExternalReferenceCode?: string;
	activities: MDFRequestActivity[];
	additionalOption: LiferayPicklist;
	claimPercent: number;
	company?: LiferayAccountBrief;
	convertedTotalCostOfExpense: number;
	convertedTotalMDFRequestAmount: number;
	currency: LiferayPicklist;
	currencyExchangeRate: number;
	emailAddress?: string;
	externalReferenceCode?: string;
	liferayBusinessSalesGoals?: string[];
	liferayBusinessSalesGoalsOther?: string;
	liferaysUserIdSF?: number;
	maxDateActivity?: string;
	mdfRequestStatus: LiferayPicklist;
	minDateActivity?: string;
	overallCampaignDescription: string;
	overallCampaignName: string;
	partnerCountries: LiferayPicklist[];
	r_usrToMDFReqs_userId?: number;
	submitDate?: string;
	submitted?: boolean;
	targetAudienceRoles?: string[];
	targetMarkets?: string[];
	totalClaimedRequest?: string;
	totalCostOfExpense: number;
	totalMDFRequestAmount: number;
	totalPaidAmount?: string;
	totalRequested?: number;
}
