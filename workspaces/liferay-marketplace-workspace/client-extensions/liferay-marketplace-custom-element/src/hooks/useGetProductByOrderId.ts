/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import HeadlessCommerceDeliveryOrderImpl from '../services/rest/HeadlessCommerceDeliveryOrder';
import {getProductById} from '../utils/api';

const useGetProductByOrderId = (orderId?: string) => {
	const {data, error, isLoading} = useSWR(
		`/placed-order/${orderId}`,
		async () => {
			const placedOrder = await HeadlessCommerceDeliveryOrderImpl.getPlacedOrder(
				orderId as string
			);

			const productId = placedOrder.placedOrderItems[0].productId;

			const product = await getProductById({
				nestedFields: 'attachments',
				productId,
			});

			return {
				placedOrder,
				product,
			};
		}
	);

	return {data, error, isLoading};
};

export default useGetProductByOrderId;
