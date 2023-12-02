/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import HeadlessCommerceDeliveryOrderImpl from '../services/rest/HeadlessCommerceDeliveryOrder';
import {getProductById} from '../utils/api';

const useGetProductByOrderId = (orderId: string) => {
	return useSWR(`/placed-order/${orderId}`, async () => {
		const placedOrder = await HeadlessCommerceDeliveryOrderImpl.getPlacedOrder(
			orderId
		);

		if (placedOrder.placedOrderBillingAddressId > 0) {
			placedOrder.placedOrderBillingAddress = await HeadlessCommerceDeliveryOrderImpl.getPlacedOrderBillingAddress(
				orderId
			);
		}

		const product = await getProductById({
			nestedFields: 'attachments,productSpecifications',
			productId: placedOrder.placedOrderItems[0].productId,
		});

		return {
			placedOrder,
			product,
		};
	});
};

export default useGetProductByOrderId;
