/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {Liferay} from '../../liferay/liferay';
import HeadlessCommerceDeliveryCatalogImpl from '../../services/rest/HeadlessCommerceDeliveryCatalog';
import {
	getAccountInfoFromCommerce,
	getCart,
	getCartItems,
} from '../../utils/api';

const useNextSteps = (orderId: string) => {
	const {data = [], isLoading: cartLoading} = useSWR(
		`/next-steps/cart/${orderId}`,
		() => {
			return Promise.all([
				getCart(Number(orderId)),
				getCartItems(Number(orderId)),
			]);
		}
	);

	const [cart, cartItems] = data ?? [];
	const {accountId} = cart ?? {};
	const firstCartItem = cartItems?.items[0];

	const {productId} = firstCartItem ?? {};

	const {data: product, isLoading: productLoading} = useSWR(
		productId
			? `/next-steps/product/${productId}_${firstCartItem.id}`
			: null,
		() =>
			HeadlessCommerceDeliveryCatalogImpl.getProduct(
				Liferay.CommerceContext.commerceChannelId,
				productId,
				new URLSearchParams({
					accountId,
					nestedFields: 'attachments,productSpecifications',
				})
			)
	);

	const {
		data: accountCommerce,
		isLoading: accountCommerceLoading,
	} = useSWR(
		accountId ? `/next-steps/account-commerce/${accountId}` : null,
		() => getAccountInfoFromCommerce(accountId)
	);

	return {
		accountCommerce,
		cart,
		cartItems,
		firstCartItem,
		isLoading: cartLoading || productLoading || accountCommerceLoading,
		product,
	};
};

export default useNextSteps;
