/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {getAccountAddressesFromCommerce} from '../utils/api';

const useGetAddresses = (selectedAccountId: number | undefined) => {
	const [addresses, setAddresses] = useState<BillingAddress[]>([]);

	const getAddresses = async (selectedAccountId: number | undefined) => {
		if (selectedAccountId) {
			const billingAddresses = await getAccountAddressesFromCommerce(
				selectedAccountId as number
			);

			setAddresses(billingAddresses.items);
		}
	};

	useEffect(() => {
		getAddresses(selectedAccountId);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedAccountId]);

	return {
		addresses,
	};
};

export default useGetAddresses;
