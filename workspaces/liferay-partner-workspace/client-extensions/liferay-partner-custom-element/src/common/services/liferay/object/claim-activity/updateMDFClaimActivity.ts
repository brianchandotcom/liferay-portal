/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../..';
import MDFClaimActivity from '../../../../interfaces/mdfClaimActivity';
import getDTOFromMDFClaimActivity from '../../../../utils/dto/mdf-claim-activity/getDTOFromMDFClaimActivity';
import {LiferayAPIs} from '../../common/enums/apis';
import liferayFetcher from '../../common/utils/fetcher';

export default async function updateMDFClaimActivity(
	mdfClaimActivity: MDFClaimActivity,
	mdfClaimId: number,
	companyId: number
) {
	return await liferayFetcher.put(
		`/o/${LiferayAPIs.OBJECT}/mdfclaimactivities/by-external-reference-code/${mdfClaimActivity.externalReferenceCode}`,
		Liferay.authToken,
		getDTOFromMDFClaimActivity(mdfClaimActivity, mdfClaimId, companyId)
	);
}
