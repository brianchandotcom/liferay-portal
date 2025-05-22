/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type BillingAddress = {
	city?: string;
	country?: string;
	countryISOCode: string;
	description?: string;
	name?: string;
	phoneNumber?: string;
	regionISOCode?: string;
	saveAddress?: boolean;
	street1?: string;
	street2?: string;
	zip?: string;
};

type Cart = {
	accountId: number;
	author?: string;
	billingAddress: BillingAddress;
	cartItems: CartItem[];
	currencyCode: string;
	customFields: any;
	id: number;
	orderStatusInfo: {[key: string]: string};
	orderTypeExternalReferenceCode: string;
	orderTypeId: number;
	paymentMethod: string;
	paymentStatusInfo: {[key: string]: string};
	paymentStatusLabel: string;
	purchaseOrderNumber?: string;
	shippingAddress: BillingAddress;
	summary: {
		totalFormatted: string;
	};
};

type CartItem = {
	customFields?: {};
	price: {
		currency: string;
		discount: number;
		finalPrice?: number;
		price?: number;
	};
	productId?: number;
	quantity: number;
	settings: {
		maxQuantity: number;
	};
	skuId: number;
};

type Order = {
	account: {
		id: number;
		name: string;
		type: string;
	};
	accountExternalReferenceCode?: string;
	accountId: number;
	billingAddressId?: number;
	channel: {
		currencyCode?: string;
		id: number;
		type: string;
	};
	channelExternalReferenceCode?: string;
	channelId: number;
	createDate?: string;
	creatorEmailAddress?: string;
	currencyCode: string;
	customFields?: {[key: string]: string};
	externalReferenceCode?: string;
	id: number;
	marketplaceOrderType?: string;
	modifiedDate?: string;
	orderDate?: string;
	orderItems: [
		{
			id?: number;
			name?: {
				en_US: string;
			};
			quantity?: number;
			skuId: number;
			unitPriceWithTaxAmount?: number;
		},
	];
	orderStatus: number;
	orderStatusInfo: {
		code: number;
		label: string;
		label_i18n: string;
	};
	orderTypeExternalReferenceCode?: string;
	orderTypeId?: number;
	paymentStatusInfo: {
		code: number;
		label: string;
		label_i18n: string;
	};
	placedOrderItems?: any;
	shippingAmount?: number;
	shippingWithTaxAmount?: number;
	totalAmount?: number;
	totalFormatted: string;
};

type OrderType = {
	externalReferenceCode: string;
	id: number;
	name: {[key: string]: string};
};

type PaymentMethodSelector = 'order' | 'pay' | 'trial' | 'free';

type PlacedOrder = {
	account: string;
	accountId: number;
	author: string;
	createDate: string;
	customFields: {[key: string]: string};
	id: number;
	orderStatusInfo: {
		code: number;
		label: string;
		label_i18n: string;
	};
	orderTypeExternalReferenceCode: string;
	paymentStatus: number;
	placedOrderBillingAddress: any;
	placedOrderBillingAddressId: number;
	placedOrderItems: PlacedOrderItems[];
	workflowStatusInfo: {
		code: number;
		label: string;
		label_i18n: string;
	};
};

type PlacedOrderItems = {
	id: number;
	name: string;
	options: string;
	price: {
		price: number;
		priceFormatted: string;
	};
	productId: number;
	quantity: number;
	sku: string;
	skuId: number;
	subscription: boolean;
	thumbnail: string;
	version: string;
	virtualItemURLs: string;
	virtualItems: VirtualItem[];
};

type VirtualItem = {
	productVersion?: String;
	url: string;
	usages: number;
	version: string;
};