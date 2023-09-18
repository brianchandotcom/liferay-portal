/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../..';
import DealRegistration from '../../../../interfaces/dealRegistration';
import {getDTOFromDealRegistration} from '../../../../utils/dto/deal-registration/getDTOfromDealRegistration';
import {LiferayAPIs} from '../../common/enums/apis';
import liferayFetcher from '../../common/utils/fetcher';
import {ResourceName} from '../enum/resourceName';

export default async function createDealRegistration(
	apiOption: ResourceName,
	dealRegistration: DealRegistration,
	leadExternalReferenceCode?: string
) {
	return await liferayFetcher.post(
		`/o/${LiferayAPIs.OBJECT}/${apiOption}`,
		Liferay.authToken,
		getDTOFromDealRegistration(
			apiOption,
			dealRegistration,
			leadExternalReferenceCode
		)
	);
}
