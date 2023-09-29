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
	purchaseOrderNumber: string,
	selectedPaymentMethod: PaymentMethodSelector,
	sku: SKU,
	product?: Product,
	selectedAccount?: Account
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

	const newCart = {
		order: {
			...cart,
			author: email,
			billingAddress,
			purchaseOrderNumber,
		},
		pay: {
			...cart,
			billingAddress,
			paymentMethod: 'paypal',
		},
		trial: {
			...cart,
			billingAddress,
		},
	};

	return newCart[selectedPaymentMethod];
}
