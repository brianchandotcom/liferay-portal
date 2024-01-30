/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';

class HeadlessCommerceDeliveryCart {
	async deleteCart(id: number | string) {
		return fetcher.delete(
			`/o/headless-commerce-delivery-cart/v1.0/carts/${id}`
		);
	}

	async updateCart(id: number | string, data: unknown) {
		return fetcher.patch(
			`/o/headless-commerce-delivery-cart/v1.0/carts/${id}`,
			data
		);
	}
}

const headlessCommerceDeliveryCart = new HeadlessCommerceDeliveryCart();

export default headlessCommerceDeliveryCart;
