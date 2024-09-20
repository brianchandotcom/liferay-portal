/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo, useState} from 'react';
import {useOutletContext} from 'react-router-dom';

import Page from '../../../../components/Page';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import {ORDER_TYPES, ORDER_WORKFLOW_STATUS_CODE} from '../../../../enums/Order';
import PurchasedSolutionsTable from '../../components/PurchasedSolutionsTable';
import {usePurchasedOrders} from '../../usePurchasedOrders';

const ACTIVE_REFRESH_INTERVAL = 60 * 1000;
const DEFAULT_REFRESH_INTERVAL = 240 * 1000;

const Solutions = () => {
	const [refreshInterval, setRefreshInterval] = useState(
		DEFAULT_REFRESH_INTERVAL
	);
	const {channel} = useMarketplaceContext();
	const {selectedAccount} = useOutletContext<any>();

	const {
		data: placedOrders = {items: []},
		error,
		isLoading,
	} = usePurchasedOrders({
		accountId: selectedAccount?.id,
		channelId: channel?.id,
		orderTypeExternalReferenceCodes: [
			ORDER_TYPES.SOLUTIONS7,
			ORDER_TYPES.SOLUTIONS30,
			ORDER_TYPES.ADDONS,
		],
		page: 1,
		pageSize: 20,
		swrConfig: {refreshInterval},
	});

	const orderItems = useMemo(
		() => placedOrders.items ?? [],
		[placedOrders.items]
	);

	useEffect(() => {
		const isProcessing = orderItems.some(({orderStatusInfo}) =>
			[
				ORDER_WORKFLOW_STATUS_CODE.PROCESSING,
				ORDER_WORKFLOW_STATUS_CODE.ON_HOLD,
			].includes(orderStatusInfo.code)
		);

		setRefreshInterval(
			isProcessing ? ACTIVE_REFRESH_INTERVAL : DEFAULT_REFRESH_INTERVAL
		);
	}, [orderItems]);

	return (
		<Page
			description="Manage solution trial and purchases from the Marketplace"
			pageRendererProps={{error, isLoading}}
			title="My Solutions"
		>
			<PurchasedSolutionsTable
				items={placedOrders.items as PlacedOrder[]}
			/>
		</Page>
	);
};

export default Solutions;
