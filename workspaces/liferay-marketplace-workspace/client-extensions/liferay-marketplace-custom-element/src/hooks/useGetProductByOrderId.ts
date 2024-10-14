/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR, {SWRConfiguration} from 'swr';

import {PRODUCT_IMAGE_FALLBACK_CATEGORIES} from '../enums/Product';
import {Liferay} from '../liferay/liferay';
import HeadlessCommerceDeliveryCatalogImpl from '../services/rest/HeadlessCommerceDeliveryCatalog';
import HeadlessCommerceDeliveryOrderImpl from '../services/rest/HeadlessCommerceDeliveryOrder';
import {
	getProductFallback,
	getProductImageFallback,
} from '../utils/productUtils';

const useGetProductByOrderId = (
	orderId: string,
	swrOptions?: SWRConfiguration
) => {
	return useSWR(
		`/placed-order/${orderId}`,
		async () => {
			const placedOrder =
				await HeadlessCommerceDeliveryOrderImpl.getPlacedOrder(orderId);

			if (placedOrder.placedOrderBillingAddressId > 0) {
				placedOrder.placedOrderBillingAddress =
					await HeadlessCommerceDeliveryOrderImpl.getPlacedOrderBillingAddress(
						orderId
					);
			}

			let product;

			try {
				product = await HeadlessCommerceDeliveryCatalogImpl.getProduct(
					Liferay.CommerceContext.commerceChannelId,
					placedOrder.placedOrderItems[0].productId,
					new URLSearchParams({
						'accountId': '-1',
						'attachments.accountId': '-1',
						'images.accountId': '-1',
						'nestedFields':
							'attachments,categories,images,productSpecifications',
						'skus.accountId': '-1',
					})
				);
			}
			catch (error) {
				console.error('Failed to fetch product:', error);

				product = getProductFallback();
				placedOrder.placedOrderItems[0].thumbnail =
					getProductImageFallback(
						PRODUCT_IMAGE_FALLBACK_CATEGORIES.PRODUCT_IMAGE
					);
			}

			return {
				placedOrder,
				product,
			};
		},
		swrOptions
	);
};

export default useGetProductByOrderId;
