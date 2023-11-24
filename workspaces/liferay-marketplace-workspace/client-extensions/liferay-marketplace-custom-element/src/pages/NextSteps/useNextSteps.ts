/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {
	getAccountInfoFromCommerce,
	getCart,
	getCartItems,
	getDeliveryProductById,
	getProductById,
} from '../../utils/api';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {useMarketplaceContext} from '../../context/MarketplaceContext';

const useNextSteps = (orderId: string) => {
	const {channel} = useMarketplaceContext();
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
		() => {
			return getDeliveryProductById(
				accountId,
				channel.id,
				productId,
				'attachments,productSpecifications'
			);
		}
	);

	const {data: accountCommerce, isLoading: accountCommerceLoading} = useSWR(
		accountId ? `/next-steps/account-commerce/${accountId}` : null,
		() => {
			return getAccountInfoFromCommerce(accountId);
		}
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
