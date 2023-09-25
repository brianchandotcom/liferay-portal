/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function buildNewCart(
	billingAddress: BillingAddress,
	channel: Channel,
	email: string,
	isFreeApp: boolean,
	orderType: OrderType,
	product: Product | undefined,
	purchaseOrderNumber: string,
	selectedAccount: Account | undefined,
	selectedPaymentMethod: string | null,
	sku: SKU
) {
	const cart: Partial<Cart> = {
		accountId: selectedAccount?.id as number,
		cartItems: [
			{
				price: {
					currency: channel.currencyCode,
					discount: 0,
					finalPrice: product?.finalPrice,
					price: product?.price,
				},
				productId: product?.productId,
				quantity: 1,
				settings: {
					maxQuantity: 1,
				},
				skuId: sku?.id as number,
			},
		],
		currencyCode: channel.currencyCode,
		orderTypeExternalReferenceCode: orderType.externalReferenceCode,
		orderTypeId: orderType.id as number,
	};

	if (isFreeApp) {
		return {...cart};
	}

	let newCart = {};

	if (selectedPaymentMethod === 'pay') {
		newCart = {
			...cart,
			billingAddress,
			paymentMethod: 'paypal',
		};
	}

	if (selectedPaymentMethod === 'order') {
		newCart = {
			...cart,
			author: email,
			billingAddress,
			purchaseOrderNumber,
		};
	}

	if (selectedPaymentMethod === 'trial') {
		newCart = {
			...cart,
			billingAddress,
		};
	}

	return newCart;
}
