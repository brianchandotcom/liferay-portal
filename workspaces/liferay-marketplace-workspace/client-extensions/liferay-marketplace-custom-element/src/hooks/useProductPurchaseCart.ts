/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect} from 'react';
import {useSelector} from '@xstate/store/react';

import {Liferay} from '../liferay/liferay';
import headlessCommerceDeliveryCart from '../services/rest/HeadlessCommerceDeliveryCart';
import {createCart} from '../utils/api';
import {cartStore} from '../pages/ProductPurchase/store';

const channelId = Liferay.CommerceContext.commerceChannelId;

const useProductPurchaseCart = (
	accountId: number,
	product: DeliveryProduct,
	orderTypeExternalReferenceCode: string
) => {
	const {cart, cartItems} = useSelector(cartStore, (state) => state.context);

	const cartId = cart?.id;

	const setCart = useCallback(
		(cart: Cart) => cartStore.send({cart, type: 'setCart'}),
		[]
	);

	const setCartItems = useCallback(
		(cartItems: CartItem[]) =>
			cartStore.send({cartItems, type: 'setCartItems'}),
		[]
	);

	const addCart = async (productId: number, skuId: number) => {
		if (!cartId) {
			const response = await createCart({
				accountId,
				channelId,
				orderTypeExternalReferenceCode,
			});

			setCart(response);
		}

		const existingItem = cartItems.find((item) => item?.skuId === skuId);

		if (existingItem) {
			const newCartItems = cartItems.map((item) =>
				item.skuId === skuId
					? {...item, quantity: item.quantity + 1}
					: item
			);

			return setCartItems(newCartItems);
		}

		setCartItems([
			...cartItems,
			{productId, quantity: 1, skuId} as CartItem,
		]);
	};

	const removeFromCart = (skuId: number) =>
		setCartItems(
			cartItems
				.map((item) =>
					item.skuId === skuId
						? {...item, quantity: item.quantity - 1}
						: item
				)
				.filter((item) => item.quantity > 0)
		);

	const removeCart = useCallback(
		(id: number) =>
			headlessCommerceDeliveryCart
				.deleteCart(id)
				.then(() => cartStore.send({type: 'reset'}))
				.catch(console.error),
		[setCart, setCartItems]
	);

	useEffect(() => {
		(async () => {
			const {items: carts} =
				await headlessCommerceDeliveryCart.getAccountCarts(
					accountId,
					channelId
				);

			if (!carts?.length) {
				return;
			}

			const [cart] = carts;

			setCart(cart);

			const openOrders = carts.some(
				(cart: Cart) => cart?.orderStatusInfo?.label === 'open'
			);

			if (!openOrders) {
				return;
			}

			const {items: cartItems} =
				await headlessCommerceDeliveryCart.getCartItems(cart.id);

			if (
				!cartItems.some(
					(cartItem: CartItem) =>
						cartItem.productId === product.productId
				)
			) {
				return removeCart(cart.id);
			}

			setCartItems(cartItems);
		})();
	}, [accountId, product?.id, removeCart, setCart, setCartItems]);

	return {
		addCart,
		cart,
		cartItems,
		removeCart,
		removeFromCart,
		setCart,
		updateCart: headlessCommerceDeliveryCart.updateCart.bind(
			headlessCommerceDeliveryCart
		),
	};
};

export default useProductPurchaseCart;
