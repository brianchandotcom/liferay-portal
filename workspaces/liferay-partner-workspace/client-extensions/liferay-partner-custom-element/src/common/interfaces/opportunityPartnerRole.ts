/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OpportunityType} from '../enums/opportunityType';
import LiferayObject from './liferayObject';

export default interface OpportunityPartnerRole extends LiferayObject {
	accountExternalReferenceCode: string;
	accountName: string;
	active: boolean;
	amount: number;
	closeDate: string;
	currency: string;
	dateCreated: string;
	expirationDays: number;
	externalReferenceCode: string;
	growthArr: number;
	hasRenewal: boolean;
	opportunity: string;
	opportunityName: string;
	opportunityOwner: string;
	ownerName: string;
	partnerAccountName: string;
	partnerEmail: string;
	partnerFirstName: string;
	partnerLastName: string;
	projectSubscriptionEndDate: string;
	projectSubscriptionStartDate: string;
	renewalArr: number;
	stage: string;
	status: string;
	subscriptionArr: number;
	subscriptionTerm: string;
	type: OpportunityType;
}
