/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {Liferay} from '../..';
import MDFRequestActivity from '../../../../interfaces/mdfRequestActivity';
import {LiferayAPIs} from '../../common/enums/apis';
import LiferayItems from '../../common/interfaces/liferayItems';
import liferayFetcher from '../../common/utils/fetcher';

export default function useGetMDFActivity(accountEntryERC?: string) {
	return useSWR(
		accountEntryERC
			? [
					`/o/${LiferayAPIs.OBJECT}/activities?filter=r_accToActs_accountEntryERC eq '${accountEntryERC}'`,
					Liferay.authToken,
			  ]
			: null,
		(url, token) =>
			liferayFetcher<LiferayItems<MDFRequestActivity[]>>(url, token)
	);
}
