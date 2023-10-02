/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {Liferay} from '../..';
import MDFClaimDTO from '../../../../interfaces/dto/mdfClaimDTO';
import {LiferayAPIs} from '../../common/enums/apis';
import liferayFetcher from '../../common/utils/fetcher';

export default function useGetMDFClaimById(id: number | undefined) {
	return useSWR(
		id
			? [
					`/o/${LiferayAPIs.OBJECT}/mdfclaims/${id}?nestedFields=mdfClmToMDFClmActs,mdfClmActToMDFClmBgts,mdfClmActToMDFActDocs&nestedFieldsDepth=2`,
					Liferay.authToken,
			  ]
			: null,
		(url, token) => liferayFetcher<MDFClaimDTO>(url, token)
	);
}
