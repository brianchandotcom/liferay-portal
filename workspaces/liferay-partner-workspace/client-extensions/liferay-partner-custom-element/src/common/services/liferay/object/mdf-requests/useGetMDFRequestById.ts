/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {Liferay} from '../..';
import MDFRequestDTO from '../../../../interfaces/dto/mdfRequestDTO';
import {LiferayAPIs} from '../../common/enums/apis';
import liferayFetcher from '../../common/utils/fetcher';

export default function useGetMDFRequestById(id: number | undefined) {
	return useSWR(
		id
			? [
					`/o/${LiferayAPIs.OBJECT}/mdfrequests/${id}?nestedFields=accountEntry,mdfReqToActs,actToBgts,actToMDFClmActs,r_mdfClmToMDFClmActs_c_mdfClaimId,mdfReqToMDFClms&nestedFieldsDepth=5`,
					Liferay.authToken,
			  ]
			: null,
		(url, token) => liferayFetcher<MDFRequestDTO>(url, token)
	);
}
