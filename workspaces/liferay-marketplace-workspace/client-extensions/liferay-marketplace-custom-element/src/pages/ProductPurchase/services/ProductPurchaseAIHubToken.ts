/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { OrderTypes } from '../../../enums/Order';
import { Liferay } from '../../../liferay/liferay';
import HeadlessAIHubBetaRequestAccess from '../../../services/rest/HeadlessAIHubBetaRequestAccess';
import HeadlessCommerceDeliveryOrder from '../../../services/rest/HeadlessCommerceDeliveryOrder';
import { getSiteURL } from '../../../utils/site';
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
		} as Cart;
	}

	public async createOrder(cart: Cart) {
		const order = await super.createOrder(cart);

		await HeadlessAIHubBetaRequestAccess.createAIHubBetaRequestAccess({
			r_orderToAIHubBetaPrivateAccessRequest_commerceOrderId: order?.id,
		}).catch(console.error);

		return order;
	}

	public async getNextStepsLink(cart: Cart) {
		if (cart.orderTypeExternalReferenceCode !== OrderTypes.AI_HUB_TOKEN) {
			return super.getNextStepsLink(cart);
		}

		try {
			const channelId = Liferay.CommerceContext.commerceChannelId;
			const accountId = this.account?.id;
			if (accountId) {
				const parameters = new URLSearchParams({
					filter: "orderTypeExternalReferenceCode eq 'AI_HUB'",
					pageSize: '1',
				});
				const response = await HeadlessCommerceDeliveryOrder.getPlacedOrders(
					channelId,
					accountId,
					parameters
				);
				const aiHubOrder = response?.items?.[0];
				if (aiHubOrder?.id) {
					return `${window.location.origin}${getSiteURL()}/customer-dashboard#/products/${aiHubOrder.id}`;
				}
			}
		} catch (error) {
			console.error('Failed to resolve AI Hub order ID:', error);
		}

		return `${window.location.origin}${getSiteURL()}/customer-dashboard#/products`;
	}
}
