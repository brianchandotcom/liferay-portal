/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DealRegistration from '../../../common/interfaces/dealRegistration';
import createDealRegistration from '../../../common/services/liferay/object/deal-registration/createDealRegistration';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';

export default async function createDealRegistrationProxyAPI(
	values: DealRegistration
) {
	const dtoLeadSFResponse = await createDealRegistration(
		ResourceName.LEADS_SALESFORCE,
		values
	);

	if (dtoLeadSFResponse.externalReferenceCode) {
		const dtoLeadProjectSFResponse = await createDealRegistration(
			ResourceName.PROJECT_SALESFORCE,
			values,
			dtoLeadSFResponse.externalReferenceCode
		);

		if (dtoLeadProjectSFResponse) {
			await createDealRegistration(
				ResourceName.LEAD_NOTIFICATION,
				values,
				dtoLeadSFResponse.externalReferenceCode
			);
		}
	}
}
