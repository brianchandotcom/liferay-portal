/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import SearchBuilder from '../../core/SearchBuilder';
import {Liferay} from '../../liferay/liferay';
import HeadlessAdminUserImpl from '../../services/rest/HeadlessAdminUser';

const useSupplierAccounts = () => {
	return useSWR('/supplier-accounts', () =>
		HeadlessAdminUserImpl.getAccounts(
			new URLSearchParams({
				filter: SearchBuilder.eq('type', 'supplier'),
				pageSize: '-1',
				sort: 'name:asc',
			})
		).then((response) => response.items)
	);
};

const useSupplierAccount = () => {
	const accountId = Liferay.CommerceContext.account?.accountId ?? 0;

	return useSWR(`/supplier-account/${accountId}`, () =>
		HeadlessAdminUserImpl.getAccount(accountId)
	);
};

export {useSupplierAccount, useSupplierAccounts};
