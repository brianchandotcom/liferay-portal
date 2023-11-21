/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {getAccounts} from '../../../utils/api';

const useGetProductCreatorAccount = (product?: Product) => {
	const {data: productCreatorAccount} = useSWR(
		`/publisher-account/${product?.catalogId}`,
		async () => {
			const {items: accountList} = await getAccounts();

			return accountList.find((account) => {
				const customField = account.customFields?.find(
					(customField) => customField.name === 'CatalogId'
				);

				return (
					customField?.customValue.data ===
					product?.catalogId.toString()
				);
			});
		}
	);

	return productCreatorAccount;
};

export default useGetProductCreatorAccount;
