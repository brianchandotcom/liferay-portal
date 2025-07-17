/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR, {SWRConfiguration} from 'swr';

import HeadlessCommerceDeliveryOrder from '../../services/rest/HeadlessCommerceDeliveryOrder';

type Props = {
	accountId: number;
	channelId: number | string;
	filter?: string;
	page: number;
	pageSize: number;
	refreshInterval?: number;
	swrConfig?: SWRConfiguration;
};

const useSSATrials = ({
	accountId,
	channelId,
	filter = '',
	page,
	pageSize,
	swrConfig,
}: Props) => {
	const key = `/placed-orders/${accountId}/${channelId}/${page}/${pageSize}`;

	const swr = useSWR(
		accountId && channelId
			? `/placed-orders/${accountId}/${channelId}/${page}/${pageSize}`
			: null,
		async () => {
			const placedOrders =
				await HeadlessCommerceDeliveryOrder.getPlacedOrders(
					channelId,
					accountId,
					new URLSearchParams({
						filter,
						nestedFields: 'placedOrderItems',
						page: page.toString(),
						pageSize: pageSize.toString(),
						sort: 'createDate:desc',
					})
				);

			return placedOrders;
		},
		swrConfig
	);

	return {key, ...swr};
};

export {useSSATrials};
