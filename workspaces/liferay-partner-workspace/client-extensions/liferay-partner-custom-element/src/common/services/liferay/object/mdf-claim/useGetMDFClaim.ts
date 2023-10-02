/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {Liferay} from '../..';
import MDFClaimDTO from '../../../../interfaces/dto/mdfClaimDTO';
import {LiferayAPIs} from '../../common/enums/apis';
import LiferayItems from '../../common/interfaces/liferayItems';
import liferayFetcher from '../../common/utils/fetcher';

export default function useGetMDFClaim(
	page: number,
	pageSize: number,
	filtersTerm: string
) {
	return useSWR(
		[
			`/o/${LiferayAPIs.OBJECT}/mdfclaims?&filter=${filtersTerm}&page=${page}&pageSize=${pageSize}&sort=dateCreated:desc`,
			Liferay.authToken,
		],
		(url, token) => liferayFetcher<LiferayItems<MDFClaimDTO[]>>(url, token)
	);
}
