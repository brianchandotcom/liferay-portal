/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {Liferay} from '../../liferay/liferay';
import HeadlessCommerceDeliveryCatalog from '../../services/rest/HeadlessCommerceDeliveryCatalog';
import HeadlessCommerceDeliveryOrder from '../../services/rest/HeadlessCommerceDeliveryOrder';
import {getAccountInfoFromCommerce} from '../../utils/api';

const useNextSteps = (orderId: string) => {
	const {data: placedOrder = {} as PlacedOrder, isLoading: cartLoading} =
		useSWR(`/next-steps/cart/${orderId}`, () =>
			HeadlessCommerceDeliveryOrder.getPlacedOrder(orderId)
		);

	const {accountId, placedOrderItems = []} = placedOrder as PlacedOrder;

	const firstPlacedOrder = placedOrderItems[0];

	const {productId} = firstPlacedOrder ?? {};

	const {data: product, isLoading: productLoading} = useSWR(
		productId
			? `/next-steps/product/${productId}/${firstPlacedOrder?.id}`
			: null,
		() =>
			HeadlessCommerceDeliveryCatalog.getProduct(
				Liferay.CommerceContext.commerceChannelId,
				productId as number,
				new URLSearchParams({
					'accountId': '-1',
					'attachments.accountId': '-1',
					'images.accountId': '-1',
					'nestedFields': 'attachments,images,productSpecifications',
					'skus.accountId': '-1',
				})
			)
	);

	const {data: accountCommerce, isLoading: accountCommerceLoading} = useSWR(
		accountId ? `/next-steps/account-commerce/${accountId}` : null,
		() => getAccountInfoFromCommerce(accountId)
	);

	return {
		accountCommerce,
		firstPlacedOrder,
		isLoading: cartLoading || productLoading || accountCommerceLoading,
		placedOrder,
		product,
	};
};

export default useNextSteps;
