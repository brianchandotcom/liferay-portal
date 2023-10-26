/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';

import {getProductById} from '../../../utils/api';

const useGetProductById = (nestedFields: string, productId?: string) => {
	const [product, setProduct] = useState<Product>();

	const getProductInformation = useCallback(async () => {
		if (productId) {
			const fetchProduct = await getProductById({
				nestedFields,
				productId,
			});

			setProduct(fetchProduct);
		}
	}, [productId, setProduct, nestedFields]);

	useEffect(() => {
		getProductInformation();
	}, [getProductInformation]);

	return {product};
};

export default useGetProductById;
