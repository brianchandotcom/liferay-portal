/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../..';
import MDFRequestDTO from '../../../../interfaces/dto/mdfRequestDTO';
import MDFRequest from '../../../../interfaces/mdfRequest';
import MDFRequestActivity from '../../../../interfaces/mdfRequestActivity';
import getDTOFromMDFRequestActivity from '../../../../utils/dto/mdf-request-activity/getDTOFromMDFRequestActivity';
import {LiferayAPIs} from '../../common/enums/apis';
import liferayFetcher from '../../common/utils/fetcher';
import {ResourceName} from '../enum/resourceName';

export default async function createMDFRequestActivity(
	apiOption: ResourceName,
	mdfRequestActivity: MDFRequestActivity,
	mdfRequest: MDFRequest,
	mdfRequestDTO?: MDFRequestDTO,
	externalReferenceCodeFromSF?: string
) {
	return await liferayFetcher.post(
		`/o/${LiferayAPIs.OBJECT}/${apiOption}`,
		Liferay.authToken,
		getDTOFromMDFRequestActivity(
			mdfRequestActivity,
			mdfRequest,
			mdfRequestDTO,
			externalReferenceCodeFromSF
		)
	);
}
