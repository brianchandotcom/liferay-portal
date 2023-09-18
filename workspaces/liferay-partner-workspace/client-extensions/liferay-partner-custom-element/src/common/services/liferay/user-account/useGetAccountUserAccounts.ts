/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '..';
import useSWR from 'swr';

import UserAccount from '../../../interfaces/userAccount';
import {LiferayAPIs} from '../common/enums/apis';
import LiferayItems from '../common/interfaces/liferayItems';
import liferayFetcher from '../common/utils/fetcher';

export default function useGetAccountUserAccounts(
	accountExternalReferenceCode: string | undefined
) {
	return useSWR(
		accountExternalReferenceCode
			? [
					`/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/accounts/by-external-reference-code/${accountExternalReferenceCode}/user-accounts`,
					Liferay.authToken,
			  ]
			: null,
		(url, token) => liferayFetcher<LiferayItems<UserAccount[]>>(url, token)
	);
}
