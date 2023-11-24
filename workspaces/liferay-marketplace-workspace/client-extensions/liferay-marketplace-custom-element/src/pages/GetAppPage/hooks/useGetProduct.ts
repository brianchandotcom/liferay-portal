/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';

import {getDeliveryProductById} from '../../../utils/api';
import {getUrlParam} from '../../../utils/getUrlParam';
import { useMarketplaceContext } from '../../../context/MarketplaceContext';
import { Liferay } from '../../../liferay/liferay';

const useGetProduct = (
	selectedProduct: DeliveryProduct | undefined,
	setProduct: (value: DeliveryProduct) => void
) => {
	const {channel} = useMarketplaceContext();
	const [productId, setProductId] = useState<number | string | null>();

	const getProductInformation = useCallback(async () => {
		const urlProductId = getUrlParam('productId');
		setProductId(selectedProduct?.productId || urlProductId);

		if (productId) {
			const fetchProduct = await getDeliveryProductById(
				Liferay.CommerceContext?.account?.accountId || 0,
				channel.id,
				productId,
				'attachments,productSpecifications,skus'
			);

			setProduct(fetchProduct);
		}
	}, [productId, selectedProduct?.productId, setProduct]);

	useEffect(() => {
		getProductInformation();
	}, [getProductInformation]);

	return {productId};
};

export default useGetProduct;
