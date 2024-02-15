/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';
import {Outlet} from 'react-router-dom';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {getAccountImage} from '../../utils/util';
import {initialDashboardNavigationItems as dashboardNavigationItems} from './PurchasedDashboardPageUtil';

import './PurchasedAppsDashboard.scss';
import useAccounts, {useAccount} from '../../hooks/data/useAccounts';
import {Liferay} from '../../liferay/liferay';
import {usePurchasedOrders} from './usePurchasedOrders';

export type PurchasedAppProps = {
	name: string;
	orderId: number;
	orderTypeExternalReferenceCode: string;
	productId: number;
	project?: string;
	provisioning: string;
	provisioningLabel: string;
	purchasedBy?: string;
	purchasedDate: string;
	thumbnail: string;
	type: string;
	version: string;
	virtualURL: string;
};

const PurchasedAppsDashboardOutlet = () => {
	const channelId = Number(Liferay.CommerceContext.commerceChannelId);

	const [page, setPage] = useState(1);
	const accountsSearch = useAccounts();
	const {data: selectedAccount} = useAccount();

	const {
		data: placedOrders = {items: [], totalCount: 0},
	} = usePurchasedOrders({
		accountId: selectedAccount?.id as number,
		channelId,
		orderTypeExternalReferenceCodes: ['CLOUDAPP', 'DXPAPP'],
		page,
		pageSize: 10,
	});

	return (
		<div className="purchased-apps-dashboard-page-container">
			<DashboardNavigation
				accountAppsNumber={placedOrders.totalCount}
				accountIcon={getAccountImage(selectedAccount?.logoURL)}
				accountsSearch={accountsSearch}
				currentAccount={selectedAccount as any}
				dashboardNavigationItems={dashboardNavigationItems}
			/>

			<Outlet
				context={{
					dashboardNavigationItems,
					page,
					purchasedAppTable: {
						...placedOrders,
						items: placedOrders.items.map((order) => {
							const [placeOrderItem] = order.placedOrderItems;

							return {
								...order,
								name: placeOrderItem.name,
								productId: order.placedOrderItems[0].productId,
								thumbnail: placeOrderItem.thumbnail,
								type: placeOrderItem.subscription
									? 'Subscription'
									: 'Perpetual',
								virtualURL: placeOrderItem?.virtualItemURLs,
							};
						}),
					},
					selectedAccount,
					setPage,
				}}
			/>
		</div>
	);
};

export default PurchasedAppsDashboardOutlet;
