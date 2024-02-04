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
	project,
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
	project: string;
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
		...(project && {
			customFields: {
				'Cloud Project Name': project,
				'Project Name': project,
			},
		}),
		orderTypeExternalReferenceCode: orderType.externalReferenceCode,
	};

	if (isFreeApp) {
		return cart;
	}

	const baseCart = {...cart, billingAddress, shippingAddress: billingAddress};

	const newCart = {
		free: {
			...cart,
			...baseCart,
		},
		order: {
			...cart,
			...baseCart,
			author: email,
			purchaseOrderNumber,
		},
		pay: {
			...cart,
			...baseCart,
			paymentMethod: 'paypal-integration',
		},
		trial: baseCart,
	};

	return newCart[selectedPaymentMethod];
}
