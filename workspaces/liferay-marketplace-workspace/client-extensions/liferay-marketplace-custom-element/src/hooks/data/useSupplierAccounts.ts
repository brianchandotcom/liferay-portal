/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import SearchBuilder from '../../core/SearchBuilder';
import HeadlessAdminUserImpl from '../../services/rest/HeadlessAdminUser';
import useInfiniteSearch from '../useInfiniteSearch';

const useSupplierAccounts = () =>
	useInfiniteSearch('supplier-accounts', ({pageIndex, search}) =>
		HeadlessAdminUserImpl.getAccounts(
			new URLSearchParams({
				fields: 'id,logoURL,name',
				filter: new SearchBuilder()
					.contains('name', search)
					.and()
					.eq('type', 'supplier')
					.build(),
				page: pageIndex + 1,
				pageSize: '20',
				sort: 'name:asc',
			})
		)
	);

export {useSupplierAccounts};
