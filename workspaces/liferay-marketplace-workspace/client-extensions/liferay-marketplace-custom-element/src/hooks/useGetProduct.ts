/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';

import {useMarketplaceContext} from '../context/MarketplaceContext';
import HeadlessCommerceDeliveryCatalogImpl from '../services/rest/HeadlessCommerceDeliveryCatalog';
import {getUrlParam} from '../utils/getUrlParam';

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
			const fetchProduct =
				await HeadlessCommerceDeliveryCatalogImpl.getProduct(
					channel.id,
					productId,
					new URLSearchParams({
						'accountId': '-1',
						'attachments.accountId': '-1',
						'images.accountId': '-1',
						'nestedFields':
							'attachments,images,productSpecifications,skus',
						'skus.accountId': '-1',
					})
				);

			setProduct(fetchProduct);
		}
	}, [channel?.id, productId, selectedProduct?.productId, setProduct]);

	useEffect(() => {
		getProductInformation();
	}, [getProductInformation]);

	return {productId};
};

export default useGetProduct;
