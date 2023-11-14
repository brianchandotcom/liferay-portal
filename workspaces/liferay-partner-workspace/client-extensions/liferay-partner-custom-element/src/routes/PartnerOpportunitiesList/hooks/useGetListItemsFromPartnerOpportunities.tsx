/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import DealRegistrationDTO from '../../../common/interfaces/dto/dealRegistrationDTO';
import {LiferayAPIs} from '../../../common/services/liferay/common/enums/apis';
import LiferayItems from '../../../common/services/liferay/common/interfaces/liferayItems';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import useGet from '../../../common/services/liferay/object/useGet';
import getItemPartnerOpportunity from '../utils/getItemPartnerOpportunity';

export default function useGetListItemsFromPartnerOpportunities(
	page: number,
	pageSize: number,
	filtersTerm: string,
	sort: string
) {
	const swrResponse = useGet<LiferayItems<DealRegistrationDTO[]>>(
		filtersTerm &&
			`/o/${LiferayAPIs.OBJECT}/${ResourceName.OPPORTUNITIES_PARTNER_ROLE_SALESFORCE}?&filter=${filtersTerm}&page=${page}&pageSize=${pageSize}&sort=${sort}`
	);

	const listItems = useMemo(
		() =>
			swrResponse.data?.items.map((item) =>
				getItemPartnerOpportunity(item)
			),
		[swrResponse.data?.items]
	);

	return {
		...swrResponse,
		data: {
			...swrResponse.data,
			items: listItems,
		},
	};
}
