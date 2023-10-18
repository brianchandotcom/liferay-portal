/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';

class HeadlessCommerceDeliveryCatalog {
	async getProductsByChannelId(
		channelId: number,
		searchParams = new URLSearchParams()
	) {
		return fetcher<APIResponse<Product>>(
			`o/headless-commerce-delivery-catalog/v1.0/channels/${channelId}/products?${encodeURI(
				searchParams.toString()
			)}`
		);
	}

	async getChannels(searchParams: URLSearchParams = new URLSearchParams()) {
		return fetcher<APIResponse<Channel>>(
			`o/headless-commerce-delivery-catalog/v1.0/channels?${searchParams.toString()}`
		);
	}
}

const HeadlessCommerceDeliveryCatalogImpl = new HeadlessCommerceDeliveryCatalog();

export default HeadlessCommerceDeliveryCatalogImpl;
