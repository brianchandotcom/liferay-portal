/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import SearchBuilder from '../../../core/SearchBuilder';
import {OrderTypes} from '../../../enums/Order';
import {Liferay} from '../../../liferay/liferay';
import HeadlessCommerceDeliveryOrder from '../../../services/rest/HeadlessCommerceDeliveryOrder';
import {getSiteURL} from '../../../utils/site';
import ProductPurchase from './ProductPurchase';

export class ProductPurchaseAIHubToken extends ProductPurchase {
	protected orderTypeExternalReferenceCode = OrderTypes.AI_HUB_TOKEN;

	protected getCart() {
		const baseCart = super.getCart();
		const cartItems = super.getCartItems();

		return {
			...baseCart,
			cartItems,
			customFields: {
				...baseCart?.customFields,
			},
			orderTypeExternalReferenceCode: this.orderTypeExternalReferenceCode,
		} as Cart;
	}

	public get calculateTax() {
		return false;
	}

	public async createOrder(cart: Cart) {
		const order = await super.createOrder(cart);

		return order;
	}

	public async getNextStepsLink(cart: Cart) {
		const channelId = Liferay.CommerceContext.commerceChannelId;

		const accountId = this.account?.id;

		const parameters = new URLSearchParams({
			filter: SearchBuilder.eq(
				'orderTypeExternalReferenceCode',
				OrderTypes.AI_HUB
			),
			pageSize: '1',
		});

		const response = await HeadlessCommerceDeliveryOrder.getPlacedOrders(
			channelId,
			accountId,
			parameters
		);

		const aiHubOrder = response?.items?.[0];

		let callbackURL = `${window.location.origin}${getSiteURL()}/customer-dashboard?redirectTo=products?tokenPurchaseSuccess`;

		if (aiHubOrder?.id) {
			callbackURL = `${window.location.origin}${getSiteURL()}/customer-dashboard?redirectTo=/products/${aiHubOrder.id}?tokenPurchaseSuccess`;
		}

		const url = await this.HeadlessCommerceDeliveryCart.getPaymentMethodURL(
			cart.id,
			callbackURL
		);

		return url || callbackURL;
	}
}
