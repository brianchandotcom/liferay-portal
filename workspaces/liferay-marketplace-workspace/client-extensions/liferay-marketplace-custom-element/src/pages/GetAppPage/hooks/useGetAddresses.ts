/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

import {getAccountAddressesFromCommerce} from '../../../utils/api';

const useGetAddresses = (selectedAccount: Account | undefined) => {
	const [addresses, setAddresses] = useState<BillingAddress[]>([]);
	const getAddresses = async () => {
		if (selectedAccount?.id) {
			const billingAddresses = await getAccountAddressesFromCommerce(
				selectedAccount?.id as number
			);

			setAddresses(billingAddresses.items);
		}
	};
	getAddresses();

	return {
		addresses,
	};
};

export default useGetAddresses;
