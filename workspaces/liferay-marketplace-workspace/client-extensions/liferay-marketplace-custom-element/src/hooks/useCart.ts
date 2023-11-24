/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {createCart, deleteCart, updateCart} from '../utils/api';

type CartItem = {
	productId: number;
	quantity: number;
	skuId: number;
};

const useCart = ({
	accountId,
	channelId,
	orderType,
}: {
	accountId: number;
	channelId?: number;
	orderType?: OrderType;
}) => {
	const [cart, setCart] = useState<Cart>();

	const [cartItems, setCartItems] = useState<CartItem[]>([]);

	const addCart = async (productId: number, skuId: number) => {
		if (!cart?.id) {
			const response = await createCart({
				accountId,
				channelId: Number(channelId),
				orderTypeExternalReferenceCode:
					orderType?.externalReferenceCode as string,
			});

			setCart(response);
		}

		const existingItem = cartItems.find((item) => item?.skuId === skuId);

		if (existingItem) {
			setCartItems((prevCart) =>
				prevCart.map((item) =>
					item.skuId === skuId
						? {...item, quantity: item.quantity + 1}
						: item
				)
			);
		}
		else {
			setCartItems((prevCart) => [
				...prevCart,
				{productId, quantity: 1, skuId},
			]);
		}
	};

	const removeFromCart = (skuId: number) => {
		setCartItems((prevCart) =>
			prevCart
				.map((item) =>
					item.skuId === skuId
						? {...item, quantity: item.quantity - 1}
						: item
				)
				.filter((item) => item.quantity > 0)
		);
	};

	const updateCartItems = async (cartId: number, data: any) => {
		const response = await updateCart(cartId, data);

		return response;
	};

	useEffect(() => {
		(async () => {
			if (cart?.id) {
				const response = await updateCartItems(cart?.id, {
					cartItems,
				});

				setCart(response);
			}
		})();
	}, [cart?.id, cartItems]);

	const removeCart = (cartId: number) => {
		deleteCart(cartId);
		setCart(undefined);
		setCartItems([]);
	};

	return {
		addCart,
		cart,
		cartItems,
		removeCart,
		removeFromCart,
		setCart,
		updateCart,
		updateCartItems,
	};
};

export default useCart;
