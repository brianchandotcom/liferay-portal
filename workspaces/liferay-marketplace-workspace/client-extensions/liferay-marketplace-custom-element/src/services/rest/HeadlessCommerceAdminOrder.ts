/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';

class HeadlessCommerceAdminOrder {
	deleteOrder(orderId: string) {
		return fetcher.delete(
			`o/headless-commerce-admin-order/v1.0/orders/${orderId}`
		);
	}

	getOrders(searchParams = new URLSearchParams()) {
		return fetcher<APIResponse>(
			`o/headless-commerce-admin-order/v1.0/orders?${searchParams.toString()}`
		);
	}
}

const HeadlessCommerceAdminOrderImpl = new HeadlessCommerceAdminOrder();

export default HeadlessCommerceAdminOrderImpl;
