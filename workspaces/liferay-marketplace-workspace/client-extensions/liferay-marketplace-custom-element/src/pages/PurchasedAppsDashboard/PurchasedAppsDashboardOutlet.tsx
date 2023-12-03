/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';
import {Outlet} from 'react-router-dom';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {getProductAttachments} from '../../utils/api';
import {
	getAccountImage,
	getThumbnailByProductAttachment,
} from '../../utils/util';
import {initialDashboardNavigationItems as dashboardNavigationItems} from './PurchasedDashboardPageUtil';

import './PurchasedAppsDashboard.scss';

import useSWR from 'swr';

import useAccounts from '../../hooks/data/useAccounts';
import {Liferay} from '../../liferay/liferay';
import HeadlessAdminUserImpl from '../../services/rest/HeadlessAdminUser';
import {usePurchasedOrders} from './usePurchasedOrders';

const useAccountCached = (accounts: any[], accountId: string | null) => {
	const {data: account} = useSWR(`/account/${accountId}`, async () => {
		if (!accountId) {
			return;
		}

		const cacheAccount = accounts?.find(
			({id}: Account) => id === Number(accountId)
		);

		if (cacheAccount) {
			return cacheAccount;
		}

		const account = await HeadlessAdminUserImpl.getAccount(
			accountId as string
		);

		return account;
	});

	return account ?? accounts[0];
};

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
	const {accountId} = Liferay.CommerceContext.account || {};
	const channelId = Number(Liferay.CommerceContext.commerceChannelId);

	const [page, setPage] = useState(1);
	const {data: accounts = []} = useAccounts();
	const selectedAccount = useAccountCached(accounts, accountId as string);

	const {
		data: placedOrders = {items: [], totalCount: 0},
		key,
	} = usePurchasedOrders({
		accountId: selectedAccount?.id,
		channelId,
		orderTypeExternalReferenceCodes: ['CLOUDAPP', 'DXPAPP'],
		page,
		pageSize: 10,
	});

	const {
		data: placedOrdersWithAttachements = {items: [], totalCount: 0},
	} = useSWR(
		`/${key}/with-attachments/${placedOrders.totalCount}`,
		async () => {
			if (!selectedAccount?.id && channelId) {
				return {items: [], totalCount: 0};
			}

			const orders = await Promise.all(
				placedOrders.items.map(async (order) => {
					const [placeOrderItem] = order.placedOrderItems;

					const attachments = await getProductAttachments(
						selectedAccount.id,
						channelId,
						placeOrderItem.productId
					);

					return {
						...order,
						name: placeOrderItem.name,
						productId: order.placedOrderItems[0].productId,
						thumbnail: getThumbnailByProductAttachment(attachments),
						type: placeOrderItem.subscription
							? 'Subscription'
							: 'Perpetual',
						virtualURL: placeOrderItem?.virtualItemURLs,
					};
				})
			);

			return {
				items: orders,
				totalCount: placedOrders.totalCount,
			};
		}
	);

	return (
		<div className="purchased-apps-dashboard-page-container">
			<DashboardNavigation
				accountAppsNumber={placedOrdersWithAttachements.items.length}
				accountIcon={getAccountImage(selectedAccount?.logoURL)}
				accounts={(accounts as unknown) as Account[]}
				currentAccount={selectedAccount}
				dashboardNavigationItems={dashboardNavigationItems}
			/>

			<Outlet
				context={{
					dashboardNavigationItems,
					page,
					purchasedAppTable: placedOrdersWithAttachements,
					selectedAccount,
					setPage,
				}}
			/>
		</div>
	);
};

export default PurchasedAppsDashboardOutlet;
