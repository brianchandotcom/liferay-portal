/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import HeadlessAdminUserImpl from '../../services/rest/HeadlessAdminUser';

const useAccounts = () => {
	return useSWR('/accounts', () =>
		HeadlessAdminUserImpl.getAccounts(
			new URLSearchParams({pageSize: '-1'})
		)?.then((response) => response.items)
	);
};

export default useAccounts;
