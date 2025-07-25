/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import HeadlessCommerceDeliveryOrder from '../../../services/rest/HeadlessCommerceDeliveryOrder';

const useSSAProduct = (orderId?: string) => {
	const [placedOrder, setplacedOrder] = useState<PlacedOrder>();

	useEffect(() => {
		if (!orderId) {
			return;
		}

		const fetchProduct = async () => {
			const result =
				await HeadlessCommerceDeliveryOrder.getPlacedOrder(orderId);

			setplacedOrder(result);
		};

		fetchProduct();
	}, [orderId]);

	return {placedOrder};
};

export default useSSAProduct;
