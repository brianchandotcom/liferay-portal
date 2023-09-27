/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {Liferay} from '../..';
import Opportunity from '../../../../interfaces/opportunity';
import {LiferayAPIs} from '../../common/enums/apis';
import LiferayItems from '../../common/interfaces/liferayItems';
import liferayFetcher from '../../common/utils/fetcher';

export default function useGetOpportunities(parameters?: string) {
	return useSWR(
		parameters
			? [
					`/o/${LiferayAPIs.OBJECT}/opportunitysfs${parameters}`,
					Liferay.authToken,
			  ]
			: null,
		(url, token) => liferayFetcher<LiferayItems<Opportunity[]>>(url, token)
	);
}
