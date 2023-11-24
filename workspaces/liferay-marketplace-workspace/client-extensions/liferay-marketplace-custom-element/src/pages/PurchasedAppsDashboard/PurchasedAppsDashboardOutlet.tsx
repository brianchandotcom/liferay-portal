/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {Outlet} from 'react-router-dom';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {
	getAccountInfoFromCommerce,
	getAccounts,
	getCustomFieldExpandoValue,
	getProductAttachments,
} from '../../utils/api';
import {
	getAccountImage,
	getThumbnailByProductAttachment,
} from '../../utils/util';
import {initialDashboardNavigationItems as dashboardNavigationItems} from './PurchasedDashboardPageUtil';

import './PurchasedAppsDashboard.scss';

import useSWR from 'swr';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import {Liferay} from '../../liferay/liferay';
import {useAccountCached} from '../PublishedAppsDashboard/PublishedAppsDashboardOutlet';
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
	const {accountId} = Liferay.CommerceContext.account || {};
	const [commerceAccount, setCommerceAccount] = useState<CommerceAccount>();

	const [page, setPage] = useState(1);
	const {channel} = useMarketplaceContext();

	const {data: accounts = []} = useSWR('/purchased/accounts', async () => {
		const accounts = await getAccounts();

		return accounts.items ?? [];
	});

	const selectedAccount = useAccountCached(
		accounts ?? [],
		accountId as string
	);

	useEffect(() => {
		const getAccountCommerce = async () => {
			const commerceAccountResponse = await getAccountInfoFromCommerce(
				selectedAccount?.id
			);

			setCommerceAccount(commerceAccountResponse);
		};

		getAccountCommerce();
	}, [selectedAccount?.id]);

	const {data: placedOrders = {items: [], totalCount: 0}, key} =
		usePurchasedOrders({
			accountId: selectedAccount?.id,
			channelId: channel?.id,
			orderTypeExternalReferenceCodes: ['CLOUDAPP', 'DXPAPP'],
			page,
			pageSize: 10,
		});

	const {data: placedOrdersWithAttachements = {items: [], totalCount: 0}} =
		useSWR(
			`/${key}/with-attachments/${placedOrders.totalCount}`,
			async () => {
				if (!selectedAccount?.id && channel?.id) {
					return {items: [], totalCount: 0};
				}

				const orders = await Promise.all(
					placedOrders.items.map(async (order) => {
						const [placeOrderItem] = order.placedOrderItems;

						const version = await getCustomFieldExpandoValue({
							className:
								'com.liferay.commerce.product.model.CPInstance',
							classPK: placeOrderItem.skuId,
							columnName: 'version',
							companyId: Number(
								Liferay.ThemeDisplay.getCompanyId()
							),
							tableName: 'CUSTOM_FIELDS',
						});

						const attachments = await getProductAttachments(
							selectedAccount.id,
							channel.id as number,
							placeOrderItem.productId
						);

						return {
							...order,
							name: placeOrderItem.name,
							productId: order.placedOrderItems[0].productId,
							thumbnail:
								getThumbnailByProductAttachment(attachments),
							type: placeOrderItem.subscription
								? 'Subscription'
								: 'Perpetual',
							version: Object.keys(version).length ? version : '',
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
				accountIcon={getAccountImage(commerceAccount?.logoURL)}
				accounts={accounts as Account[]}
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
