/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AccountEntry from '../accountEntry';
import DealRegistration from '../dealRegistration';
import LiferayPicklist from '../liferayPicklist';
import MDFRequestActivity from '../mdfRequestActivity';

export default interface DealRegistrationDTO
	extends Omit<
		DealRegistration,
		| 'projectCategories'
		| 'mdfActivityAssociated'
		| 'partnerAccount'
		| 'projectNeed'
		| 'prospect'
		| 'primaryProspect'
	> {
	accountExternalReferenceCode?: string;
	accountName?: string;
	additionalContactEmailAddress?: string;
	additionalContactFirstName?: string;
	additionalContactLastName?: string;
	additionalContacts?: string;
	additionalInformationAboutTheOpportunity?: string;
	amount?: number;
	closeDate?: string;
	leadExternalReferenceCode?: string;
	leadQualificationExternalReferenceCode?: string;
	leadStatus?: string;
	leadStatusDetail?: string;
	leadType?: string;
	mdfActivityExternalReferenceCode?: string;
	mdfActivityName?: string;
	opportunityOwner?: string;
	ownerName?: string;
	partnerAccountName?: string;
	partnerEmail?: string;
	primaryProspectBusinessUnit?: string;
	primaryProspectDepartment?: string;
	primaryProspectEmailAddress?: string;
	primaryProspectFirstName?: string;
	primaryProspectJobRole?: string;
	primaryProspectLastName?: string;
	primaryProspectPhone?: string;
	projectCategories?: string;
	projectNeed?: string;
	projectSubscriptionEndDate?: string;
	projectSubscriptionStartDate?: string;
	projectTimeline: string;
	prospectAccountName?: string;
	prospectAddress?: string;
	prospectCity?: string;
	prospectCountry?: LiferayPicklist;
	prospectCountryCode?: string;
	prospectIndustry?: string;
	prospectPostalCode?: string;
	prospectState?: LiferayPicklist;
	prospectStateCode?: string;
	r_accToDealRegs_accountEntry?: AccountEntry;
	r_accToDealRegs_accountEntryId?: number;
	r_actToDealRegs_c_activity?: MDFRequestActivity;
	r_actToDealRegs_c_activityId?: number;
	stage?: string;
	type?: string;
}
