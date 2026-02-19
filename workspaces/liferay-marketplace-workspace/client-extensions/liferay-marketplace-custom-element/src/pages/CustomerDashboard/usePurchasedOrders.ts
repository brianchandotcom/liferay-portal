/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR, {SWRConfiguration} from 'swr';

import HeadlessCommerceDeliveryOrder from '../../services/rest/HeadlessCommerceDeliveryOrder';
import SearchBuilder from '../../core/SearchBuilder';
import {Liferay} from '../../liferay/liferay';

type Props = {
	accountId: number;
	orderTypeExternalReferenceCodes: string[];
	page: number;
	pageSize: number;
	swrConfig?: SWRConfiguration;
};

const usePurchasedOrders = ({
	accountId,
	orderTypeExternalReferenceCodes,
	page,
	pageSize,
	swrConfig,
}: Props) => {
	return useSWR(
		accountId ? `/placed-orders/${accountId}/${page}/${pageSize}` : null,
		async () =>
			HeadlessCommerceDeliveryOrder.getPlacedOrders(
				Liferay.CommerceContext.commerceChannelId,
				accountId,
				new URLSearchParams({
					filter: SearchBuilder.in(
						'orderTypeExternalReferenceCode',
						orderTypeExternalReferenceCodes
					),
					nestedFields: 'placedOrderItems',
					page: page.toString(),
					pageSize: pageSize.toString(),
					sort: 'createDate:desc',
				})
			),
		swrConfig
	);
};

export {usePurchasedOrders};
