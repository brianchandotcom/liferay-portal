/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DealRegistrationDTO from '../../../common/interfaces/dto/dealRegistrationDTO';
import {Liferay} from '../../../common/services/liferay';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import {retry} from '../../../common/utils/retry';

export default async function getOpportunityByErc(opportunityERC: string) {
	return await retry<DealRegistrationDTO>(() =>
		fetch(
			`/o/c/${ResourceName.OPPORTUNITIES_SALESFORCE}/by-external-reference-code/${opportunityERC}`,
			{
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			}
		)
	);
}
