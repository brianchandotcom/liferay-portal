/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Analytics} from '../../../core/Analytics';
import {ORDER_TYPES} from '../../../enums/Order';
import CommerceSelectAccountImpl from '../../../services/rest/CommerceSelectAccount';
import HeadlessCommerceDeliveryCart from '../../../services/rest/HeadlessCommerceDeliveryCart';

export default class ProductPurchase {
	protected orderTypeExternalReferenceCode?: ORDER_TYPES;
	protected HeadlessCommerceDeliveryCart = HeadlessCommerceDeliveryCart;

	constructor(
		protected account: Account,
		protected channel: Channel,
		protected product: DeliveryProduct | Product
	) {}

	protected getCartItems() {
		return [
			{
				price: {
					currency: this.channel.currencyCode,
					discount: 0,
				},
				productId: this.product.productId,
				quantity: 1,
				settings: {
					maxQuantity: 1,
				},
				skuId: this.product.skus[0]?.id,
			},
		];
	}

	public async createOrder(order?: Partial<Cart>): Promise<Cart> {
		const accountId = Number(this.account.id);

		const cart = await HeadlessCommerceDeliveryCart.createCart(
			this.channel.id,
			{
				...order,
				accountId,
				cartItems: this.getCartItems(),
				currencyCode: this.channel.currencyCode,
				orderTypeExternalReferenceCode:
					this.orderTypeExternalReferenceCode,
			}
		);

		await Promise.all([
			CommerceSelectAccountImpl.selectAccount(this.account.id),
			HeadlessCommerceDeliveryCart.checkoutCart(cart.id),
		]);

		Analytics.track('ORDER_CREATION', {
			accountId: this.account.id,
			orderTypeExternalReferenceCode: this.orderTypeExternalReferenceCode,
			productName: this.product.name,
		});

		return cart;
	}
}
