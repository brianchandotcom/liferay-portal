/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function buildNewCart({
	billingAddress,
	channel,
	email,
	isFreeApp,
	orderType,
	product,
	purchaseOrderNumber,
	selectedAccount,
	selectedPaymentMethod,
	selectedSKU,
	sku,
}: {
	billingAddress: BillingAddress;
	channel: Channel;
	email: string;
	isFreeApp: boolean;
	orderType: OrderType;
	product?: DeliveryProduct;
	purchaseOrderNumber: string;
	selectedAccount?: Account;
	selectedPaymentMethod: PaymentMethodSelector;
	selectedSKU?: DeliverySKU;
	sku: DeliverySKU;
}) {
	const cart: Partial<Cart> = {
		accountId: selectedAccount?.id as number,
		cartItems: [
			{
				price: {
					currency: channel.currencyCode,
					discount: 0,
				},
				productId: product?.productId,
				quantity: 1,
				settings: {
					maxQuantity: 1,
				},
				skuId: (selectedSKU?.id as number) || sku?.id,
			},
		],
		currencyCode: channel.currencyCode,
		orderTypeExternalReferenceCode: orderType.externalReferenceCode,
	};

	if (isFreeApp) {
		return {...cart};
	}

	const newCart = {
		free: {
			...cart,
			billingAddress,
		},
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
