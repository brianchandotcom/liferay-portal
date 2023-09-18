/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../..';
import MDFRequestActivityDTO from '../../../../interfaces/dto/mdfRequestActivityDTO';
import MDFRequest from '../../../../interfaces/mdfRequest';
import MDFRequestBudget from '../../../../interfaces/mdfRequestBudget';
import getDTOFromMDFRequestBudget from '../../../../utils/dto/mdf-request-budget/getDTOFromMDFRequestBudget';
import {LiferayAPIs} from '../../common/enums/apis';
import liferayFetcher from '../../common/utils/fetcher';
import {ResourceName} from '../enum/resourceName';

export default async function updateMDFRequestActivityBudget(
	apiOption: ResourceName,
	budget: MDFRequestBudget,
	activityDTO: MDFRequestActivityDTO,
	mdfRequest: MDFRequest
) {
	return await liferayFetcher.put(
		`/o/${LiferayAPIs.OBJECT}/${apiOption}/by-external-reference-code/${budget.externalReferenceCode}`,
		Liferay.authToken,
		getDTOFromMDFRequestBudget(budget, activityDTO, mdfRequest)
	);
}
