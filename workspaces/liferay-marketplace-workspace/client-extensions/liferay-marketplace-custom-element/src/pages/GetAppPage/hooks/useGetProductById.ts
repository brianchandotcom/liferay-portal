/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {getProductById} from '../../../utils/api';

const useGetProductById = (nestedFields: string, productId?: string) => {
	const {data: product} = useSWR(`/product/${productId}}`, async () => {
		if (!productId) {
			return;
		}

		return getProductById({
			nestedFields,
			productId,
		});
	});

	return {product};
};

export default useGetProductById;
