/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';

import {getProductById} from '../../../utils/api';
import {getUrlParam} from '../../../utils/getUrlParam';

const useGetProduct = (
	selectedProduct: Product | undefined,
	setProduct: (value: Product) => void
) => {
	const [productId, setProductId] = useState<number | string | null>();

	const getProductInformation = useCallback(async () => {
		const urlProductId = getUrlParam('productId');
		setProductId(selectedProduct?.productId || urlProductId);

		if (productId) {
			const fetchProduct = await getProductById({
				nestedFields: 'attachments,productSpecifications,skus,catalog',
				productId,
			});

			setProduct(fetchProduct);
		}
	}, [productId, selectedProduct?.productId, setProduct]);

	useEffect(() => {
		getProductInformation();
	}, [getProductInformation]);

	return {productId};
};

export default useGetProduct;
