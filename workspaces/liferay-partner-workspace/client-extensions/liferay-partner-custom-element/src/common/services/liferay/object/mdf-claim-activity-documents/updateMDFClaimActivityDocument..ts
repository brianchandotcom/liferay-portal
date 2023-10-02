/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../..';
import MDFClaimActivityDocumentDTO from '../../../../interfaces/dto/mdfClaimActivityDocumentDTO';
import {LiferayAPIs} from '../../common/enums/apis';
import liferayFetcher from '../../common/utils/fetcher';

export default async function updateMDFClaimActivityDocument(
	mdfClaimActivityDocumentDTO: MDFClaimActivityDocumentDTO[]
) {
	return await liferayFetcher.put(
		`/o/${LiferayAPIs.OBJECT}/mdfclaimactivitydocuments/batch`,
		Liferay.authToken,
		mdfClaimActivityDocumentDTO
	);
}
