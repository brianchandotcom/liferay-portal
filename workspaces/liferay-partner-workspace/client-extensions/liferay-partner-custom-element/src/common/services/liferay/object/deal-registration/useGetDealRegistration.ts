/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {Liferay} from '../..';
import DealRegistrationDTO from '../../../../interfaces/dto/dealRegistrationDTO';
import {LiferayAPIs} from '../../common/enums/apis';
import LiferayItems from '../../common/interfaces/liferayItems';
import liferayFetcher from '../../common/utils/fetcher';
import {ResourceName} from '../enum/resourceName';

export default function useGetDealRegistration(
	apiOption: ResourceName,
	page: number,
	pageSize: number,
	filtersTerm: string,
	opportunityFilter: string,
	sort: string
) {
	return useSWR(
		[
			`/o/${LiferayAPIs.OBJECT}/${apiOption}?${filtersTerm}&page=${page}&pageSize=${pageSize}&sort=${sort}${opportunityFilter}
			 `,
			Liferay.authToken,
		],
		(url, token) =>
			liferayFetcher<LiferayItems<DealRegistrationDTO[]>>(url, token)
	);
}
