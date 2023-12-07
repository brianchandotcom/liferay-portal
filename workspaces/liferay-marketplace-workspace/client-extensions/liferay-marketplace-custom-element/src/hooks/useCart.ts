/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {UseFormSetValue} from 'react-hook-form';

import {Liferay} from '../liferay/liferay';
import {GetAppForm} from '../pages/GetAppPage/GetAppPage';
import fetcher from '../services/fetcher';
import {createCart, deleteCart, updateCart} from '../utils/api';

type CartItem = {
	productId: number;
	quantity: number;
	skuId: number;
};

const useCart = ({
	accountId,
	orderType,
	product,
	setValue,
}: {
	accountId: number;
	channelId?: number;
	orderType?: OrderType;
	product: DeliveryProduct;
	setValue: UseFormSetValue<GetAppForm>;
}) => {
	const channelId = Liferay.CommerceContext.commerceChannelId;
	const [cart, setCart] = useState<Cart>();

	const [cartItems, setCartItems] = useState<CartItem[]>([]);

	const addCart = async (productId: number, skuId: number) => {
		if (!cart?.id) {
			const response = await createCart({
				accountId,
				channelId: Number(channelId),
				orderTypeExternalReferenceCode: orderType?.externalReferenceCode as string,
			});

			setCart(response);
		}

		const existingItem = cartItems.find(
			(item: CartItem) => item?.skuId === skuId
		);

		if (existingItem) {
			setCartItems((prevCart: CartItem[]) =>
				prevCart.map((item) =>
					item.skuId === skuId
						? {...item, quantity: item.quantity + 1}
						: item
				)
			);
		}
		else {
			setCartItems((prevCart: CartItem[]) => [
				...prevCart,
				{productId, quantity: 1, skuId},
			]);
		}
	};

	const removeFromCart = (skuId: number) => {
		setCartItems((prevCart: CartItem[]) =>
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

	const removeCart = (cartId: number) => {
		deleteCart(cartId);
		setCart(undefined);
		setCartItems([]);
	};

	useEffect(() => {
		(async () => {
			if (cart?.id && cartItems.length) {
				const response = await updateCartItems(cart?.id, {
					cartItems,
				});

				setCart(response);
			}
		})();
	}, [cart?.id, cartItems]);

	useEffect(() => {
		(async () => {
			if (accountId && channelId && product) {
				const {items: orderList} = await fetcher(
					`o/headless-commerce-delivery-cart/v1.0/channels/${channelId}/account/${accountId}/carts`
				);

				if (orderList?.length && product.id) {
					setCart(orderList[0]);

					const openCartList = await orderList?.filter(
						(order: Order) =>
							order?.orderStatusInfo?.label === 'open'
					);

					if (openCartList) {
						const cartItemsResponse = await fetcher(
							`o/headless-commerce-delivery-cart/v1.0/carts/${openCartList[0]?.id}/items`
						);

						const test = cartItemsResponse.items.some(
							(cartItem: CartItem) =>
								cartItem.productId === product.id + 1
						);

						if (test) {
							const cartItemsList = await cartItemsResponse?.items?.map(
								(item: CartItem) => {
									return {
										productId: item.productId,
										quantity: item.quantity,
										skuId: item.skuId,
									};
								}
							);

							setCartItems(cartItemsList);
							setValue('selectedTimeline', 'paid');

							return;
						}

						removeCart(orderList[0].id);
					}
				}
			}
		})();
	}, [accountId, channelId, product, setValue]);

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
