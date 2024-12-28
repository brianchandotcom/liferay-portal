/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {getAccountAddressesFromCommerce} from '../utils/api';

const useAccountAddresses = (accountId?: number) => {
	return useSWR(accountId ? `/account-addresses/${accountId}` : null, () =>
		getAccountAddressesFromCommerce(accountId as number)
	);
};

export default useAccountAddresses;
