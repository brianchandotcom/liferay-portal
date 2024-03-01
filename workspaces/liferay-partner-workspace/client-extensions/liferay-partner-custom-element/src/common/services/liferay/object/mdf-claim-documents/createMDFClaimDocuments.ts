/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../..';
import LiferayFile from '../../../../interfaces/liferayFile';
import {LiferayAPIs} from '../../common/enums/apis';
import liferayFetcher from '../../common/utils/fetcher';

export default async function createMDFClaimDocuments(
	mdfClaimDocumentDTOs: LiferayFile[]
) {
	return await liferayFetcher.post(
		`/o/${LiferayAPIs.OBJECT}/mdfclaimdocuments/batch`,
		Liferay.authToken,
		mdfClaimDocumentDTOs
	);
}
