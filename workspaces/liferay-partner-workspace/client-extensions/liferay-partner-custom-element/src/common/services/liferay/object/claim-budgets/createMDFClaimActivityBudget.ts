/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../..';
import MDFClaimBudget from '../../../../interfaces/mdfClaimBudget';
import getDTOFromMDFClaimBudget from '../../../../utils/dto/mdf-claim-budget/getDTOFromMDFClaimBudget';
import {LiferayAPIs} from '../../common/enums/apis';
import liferayFetcher from '../../common/utils/fetcher';

export default async function createMDFClaimActivityBudget(
	mdfClaimBudget: MDFClaimBudget,
	mdfClaimActivityId: number,
	companyId: number
) {
	return await liferayFetcher.post(
		`/o/${LiferayAPIs.OBJECT}/mdfclaimbudgets`,
		Liferay.authToken,
		getDTOFromMDFClaimBudget(mdfClaimBudget, mdfClaimActivityId, companyId)
	);
}
