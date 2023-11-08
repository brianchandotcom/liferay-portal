/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {Outlet, useParams} from 'react-router-dom';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {
	getAccountInfoFromCommerce,
	getAccounts,
	getCustomFieldExpandoValue,
	getProductAttachments,
} from '../../utils/api';
import {getAccountImage} from '../../utils/util';
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

const options: Intl.DateTimeFormatOptions = {
	day: 'numeric',
	month: 'short',
	year: 'numeric',
};

const PurchasedAppsDashboardOutlet = () => {
	const {accountId} = useParams();
	const [commerceAccount, setCommerceAccount] = useState<CommerceAccount>();

	const [page, setPage] = useState<number>(1);
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
				selectedAccount.id
			);

			setCommerceAccount(commerceAccountResponse);
		};

		getAccountCommerce();
	}, [selectedAccount?.id]);

	const {
		data: placedOrders = {items: [], totalCount: 0},
		key,
	} = usePurchasedOrders({
		accountId: selectedAccount?.id,
		channelId: channel?.id,
		orderTypeExternalReferenceCodes: ['CLOUDAPP', 'DXPAPP'],
		page,
		pageSize: 10,
	});

	const {
		data: placedOrdersWithAttachements = {items: [], totalCount: 0},
	} = useSWR(`/${key}/with-attachments`, async () => {
		if (!selectedAccount?.id && channel?.id) {
			return {items: [], totalCount: 0};
		}

		const orders = await Promise.all(
			placedOrders.items.map(async (order) => {
				const [placeOrderItem] = order.placedOrderItems;

				const date = new Date(order.createDate);

				const formattedDate = date.toLocaleDateString('en-US', options);

				const version = await getCustomFieldExpandoValue({
					className: 'com.liferay.commerce.product.model.CPInstance',
					classPK: placeOrderItem.skuId,
					columnName: 'version',
					companyId: Number(Liferay.ThemeDisplay.getCompanyId()),
					tableName: 'CUSTOM_FIELDS',
				});

				const attachments = await getProductAttachments(
					selectedAccount.id,
					channel.id as number,
					placeOrderItem.productId
				);

				let orderThumbnail;

				if (attachments) {
					orderThumbnail = await (async () => {
						const promises = attachments.map(
							async (currentAttachment) => {
								const attachmentsCustomField = await getCustomFieldExpandoValue(
									{
										className:
											'com.liferay.commerce.product.model.CPAttachmentFileEntry',
										classPK: currentAttachment.id,
										columnName: 'App Icon',
										companyId: Number(
											Liferay.ThemeDisplay.getCompanyId()
										),
										tableName: 'CUSTOM_FIELDS',
									}
								);

								return attachmentsCustomField[0] === 'Yes'
									? currentAttachment
									: null;
							}
						);

						const results = await Promise.all(promises);

						return results.find(
							(attachment) => attachment !== null
						);
					})();
				}

				return {
					name: placeOrderItem.name,
					orderId: order.id,
					orderTypeExternalReferenceCode:
						order.orderTypeExternalReferenceCode,
					productId: order.placedOrderItems[0].productId,
					provisioning: order.orderStatusInfo.label,
					provisioningLabel: order.orderStatusInfo.label,
					purchasedBy: order.author,
					purchasedDate: formattedDate,
					thumbnail: orderThumbnail?.src as string,
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
	});

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
