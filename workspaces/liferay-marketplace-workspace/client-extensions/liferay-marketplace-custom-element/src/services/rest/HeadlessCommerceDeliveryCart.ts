/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../../liferay/liferay';
import fetcher from '../fetcher';

class HeadlessCommerceDeliveryCart {
	async createCart(channelId: number | string, cart: Partial<Cart>) {
		return fetcher.post(
			`/o/headless-commerce-delivery-cart/v1.0/channels/${channelId}/carts`,
			cart
		);
	}

	async checkoutCart(cartId: number) {
		return fetcher.post(
			`/o/headless-commerce-delivery-cart/v1.0/carts/${cartId}/checkout`
		);
	}

	async deleteCart(id: number | string) {
		return fetcher.delete(
			`/o/headless-commerce-delivery-cart/v1.0/carts/${id}`
		);
	}

	async getPaymentURL(orderId: string, callbackURL: string) {
		const response = await Liferay.Util.fetch(
			`/o/headless-commerce-delivery-cart/v1.0/carts/${orderId}/payment-url?callbackURL=${callbackURL}`
		);

		return response.text();
	}

	async updateCart(id: number | string, data: Partial<Cart>) {
		return fetcher.patch(
			`/o/headless-commerce-delivery-cart/v1.0/carts/${id}`,
			data
		);
	}
}

const headlessCommerceDeliveryCart = new HeadlessCommerceDeliveryCart();

export default headlessCommerceDeliveryCart;
