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

export default function useGetOpportunitiesListFromRFP(
	page: number,
	pageSize: number,
	rfpOpportunities?: string
) {
	const swrResponse = useGet<LiferayItems<DealRegistrationDTO[]>>(
		rfpOpportunities &&
			`/o/${LiferayAPIs.OBJECT}/${ResourceName.OPPORTUNITIES_PARTNER_ROLE_SALESFORCE}?&page=${page}&pageSize=${pageSize}`
	);

	const listOpportunitesErc = useMemo(() => {
		const opportunites: string[] = [];

		swrResponse.data?.items.map((item) => {
			item.opportunity && opportunites.push(item.opportunity);
		});

		return [...new Set(opportunites)].slice(0, 20);
	}, [swrResponse.data?.items]);

	return {
		...swrResponse,
		dataRFP: {
			...swrResponse.data,
			items: listOpportunitesErc,
		},
	};
}
