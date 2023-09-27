/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../..';
import MDFRequestDTO from '../../../../interfaces/dto/mdfRequestDTO';
import MDFClaim from '../../../../interfaces/mdfClaim';
import {getDTOFromMDFClaim} from '../../../../utils/dto/mdf-claim/getDTOFromMDFClaim';
import {LiferayAPIs} from '../../common/enums/apis';
import liferayFetcher from '../../common/utils/fetcher';
import {ResourceName} from '../enum/resourceName';

export default async function updateMDFClaim(
	apiOption: ResourceName,
	mdfClaim: MDFClaim,
	mdfRequest: MDFRequestDTO,
	externalReferenceCodeFromSF?: string
) {
	return await liferayFetcher.put(
		`/o/${LiferayAPIs.OBJECT}/${apiOption}/by-external-reference-code/${mdfClaim.externalReferenceCode}`,
		Liferay.authToken,
		getDTOFromMDFClaim(mdfClaim, mdfRequest, externalReferenceCodeFromSF)
	);
}
