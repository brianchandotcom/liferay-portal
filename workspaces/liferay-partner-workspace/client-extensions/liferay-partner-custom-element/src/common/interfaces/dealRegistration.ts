/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestActivityDTO from './dto/mdfRequestActivityDTO';
import LiferayAccountBrief from './liferayAccountBrief';
import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';

interface PrimaryProspect {
	businessUnit: string;
	department: LiferayPicklist;
	emailAddress: string;
	firstName: string;
	jobRole: LiferayPicklist;
	lastName: string;
	phone: string;
}

interface Prospect {
	accountName: string;
	address: string;
	city: string;
	country: LiferayPicklist;
	industry: LiferayPicklist;
	postalCode: string;
	state: LiferayPicklist;
}

interface AdditionalContact {
	emailAddress: string;
	firstName: string;
	lastName: string;
}

export default interface DealRegistration extends Partial<LiferayObject> {
	accountExternalReferenceCode?: string;
	additionalContact?: AdditionalContact;
	additionalInformationAboutTheOpportunity?: string;
	currency: LiferayPicklist;
	leadStatusDetails?: string;
	mdfActivityAssociated: Partial<MDFRequestActivityDTO>;
	partnerAccount: LiferayAccountBrief;
	partnerFirstName: string;
	partnerLastName: string;
	primaryContactEmail?: string;
	primaryContactFirstName?: string;
	primaryContactLastName?: string;
	primaryProspect: PrimaryProspect;
	projectCategories: string[];
	projectNeed: string[];
	projectTimeline: string;
	prospect: Prospect;
}
