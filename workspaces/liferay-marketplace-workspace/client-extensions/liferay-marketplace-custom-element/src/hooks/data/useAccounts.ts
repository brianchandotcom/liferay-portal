/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import SearchBuilder from '../../core/SearchBuilder';
import {Liferay} from '../../liferay/liferay';
import HeadlessAdminUserImpl from '../../services/rest/HeadlessAdminUser';
import useInfiniteSearch from '../useInfiniteSearch';

const useAccount = () => {
	const accountId = Liferay.CommerceContext.account?.accountId ?? 0;

	return useSWR(`/account/${accountId}`, () =>
		HeadlessAdminUserImpl.getAccount(accountId)
	);
};

const useAccounts = () =>
	useInfiniteSearch('accounts', ({pageIndex, search}) =>
		HeadlessAdminUserImpl.getAccounts(
			new URLSearchParams({
				fields: 'id,name,logoURL',
				filter: SearchBuilder.contains('name', search),
				page: pageIndex + 1,
				pageSize: '20',
				sort: 'name:asc',
			})
		)
	);

export {useAccount, useAccounts};

export default useAccounts;
