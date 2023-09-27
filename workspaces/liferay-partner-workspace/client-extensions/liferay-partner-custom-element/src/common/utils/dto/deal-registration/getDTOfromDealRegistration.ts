/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DealRegistration from '../../../interfaces/dealRegistration';
import DealRegistrationDTO from '../../../interfaces/dto/dealRegistrationDTO';
import {ResourceName} from '../../../services/liferay/object/enum/resourceName';

export function getDTOFromDealRegistration(
	apiOption: ResourceName,
	dealRegistration: DealRegistration,
	leadExternalReferenceCode?: string
): DealRegistrationDTO {
	const dealObject: DealRegistrationDTO = {
		accountExternalReferenceCode:
			dealRegistration.partnerAccount.externalReferenceCode,
		additionalContactEmailAddress:
			dealRegistration.additionalContact?.emailAddress,
		additionalContactFirstName:
			dealRegistration.additionalContact?.firstName,
		additionalContactLastName: dealRegistration.additionalContact?.lastName,
		additionalContacts: `${dealRegistration.additionalContact?.firstName} ${dealRegistration.additionalContact?.lastName} ${dealRegistration.additionalContact?.emailAddress}`,
		additionalInformationAboutTheOpportunity:
			dealRegistration.additionalInformationAboutTheOpportunity,
		currency: dealRegistration.currency,
		leadExternalReferenceCode,
		leadStatusDetail: dealRegistration.leadStatusDetails,
		mdfActivityExternalReferenceCode:
			dealRegistration.mdfActivityAssociated.externalReferenceCode,
		mdfActivityName: dealRegistration.mdfActivityAssociated.name,
		partnerFirstName: dealRegistration.partnerFirstName,
		partnerLastName: dealRegistration.partnerLastName,
		primaryProspectBusinessUnit:
			dealRegistration.primaryProspect.businessUnit,
		primaryProspectDepartment:
			dealRegistration.primaryProspect.department.name,
		primaryProspectEmailAddress:
			dealRegistration.primaryProspect.emailAddress,
		primaryProspectFirstName: dealRegistration.primaryProspect.firstName,
		primaryProspectJobRole: dealRegistration.primaryProspect.jobRole.name,
		primaryProspectLastName: dealRegistration.primaryProspect.lastName,
		primaryProspectPhone: dealRegistration.primaryProspect.phone,
		projectCategories: dealRegistration.projectCategories.join('; '),
		projectNeed: dealRegistration.projectNeed.join('; '),
		projectTimeline: dealRegistration.projectTimeline,
		prospectAccountName: dealRegistration.prospect.accountName,
		prospectAddress: dealRegistration.prospect.address,
		prospectCity: dealRegistration.prospect.city,
		prospectCountry: dealRegistration.prospect.country,
		prospectCountryCode: dealRegistration.prospect.country.key,
		prospectIndustry: dealRegistration.prospect.industry.name,
		prospectPostalCode: dealRegistration.prospect.postalCode,
		prospectState: dealRegistration.prospect.state,
		prospectStateCode: dealRegistration.prospect.state.key,
		r_accToDealRegs_accountEntryId: dealRegistration.partnerAccount.id,
		r_actToDealRegs_c_activityId: dealRegistration.mdfActivityAssociated.id,
	};

	if (apiOption === ResourceName.LEAD_NOTIFICATION) {
		dealObject.partnerAccountName = dealRegistration.partnerAccount.name;
	}

	return dealObject;
}
