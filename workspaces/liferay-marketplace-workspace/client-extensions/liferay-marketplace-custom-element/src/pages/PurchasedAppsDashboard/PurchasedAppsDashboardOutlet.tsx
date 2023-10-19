/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {Outlet, useSearchParams} from 'react-router-dom';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {getCompanyId} from '../../liferay/constants';
import {
	getAccountInfoFromCommerce,
	getAccounts,
	getCustomFieldExpandoValue,
	getPlacedOrders,
	getProductAttachments,
} from '../../utils/api';
import {showAccountImage} from '../../utils/util';
import {initialDashboardNavigationItems} from './PurchasedDashboardPageUtil';

import './PurchasedAppsDashboard.scss';

import useSWR from 'swr';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import {useAccountCached} from '../PublishedAppsDashboard/PublishedAppsDashboardOutlet';

export interface PurchasedAppProps {
	name: string;
	orderId: number;
	orderTypeExternalReferenceCode: string;
	project?: string;
	provisioning: string;
	provisioningLabel: string;
	purchasedBy: string;
	purchasedDate: string;
	thumbnail: string;
	type: string;
	version: string;
	virtualURL: string;
}

interface PurchasedAppTable {
	items: [];
	pageSize: number;
	totalCount: number;
}

const PurchasedAppsDashboardOutlet = () => {
	const [searchParams] = useSearchParams();
	const accountId = searchParams.get('accountId');
	const [commerceAccount, setCommerceAccount] = useState<CommerceAccount>();

	const [purchasedAppTable, setPurchasedAppTable] = useState<
		PurchasedAppTable
	>({items: [], pageSize: 7, totalCount: 1});
	const [page, setPage] = useState<number>(1);
	const dashboardNavigationItems = initialDashboardNavigationItems;
	const {channel} = useMarketplaceContext();

	const [solutionsItems, setSolutionsItems] = useState<PlacedOrder[]>([]);

	const {data: accounts = []} = useSWR('/purchased/accounts', async () => {
		const accounts = await getAccounts();

		return accounts.items ?? [];
	});

	const selectedAccount = useAccountCached(accounts ?? [], accountId);

	useEffect(() => {
		const makeFetch = async () => {
			if (!selectedAccount?.id && channel?.id) {
				return;
			}

			const placedOrders = await getPlacedOrders(
				selectedAccount?.id || 50307,
				channel.id,
				page,
				purchasedAppTable.pageSize
			);

			const commerceAccountResponse = await getAccountInfoFromCommerce(
				selectedAccount.id
			);

			setCommerceAccount(commerceAccountResponse);

			const filteredAppOrders = placedOrders.items.filter(
				({orderTypeExternalReferenceCode}) =>
					orderTypeExternalReferenceCode === 'CLOUDAPP' ||
					orderTypeExternalReferenceCode === 'DXPAPP'
			);

			const filteredSolutionsOrders = placedOrders.items.filter(
				({orderTypeExternalReferenceCode}) =>
					orderTypeExternalReferenceCode === 'SOLUTION30'
			);

			const newAppOrderItems = await Promise.all(
				filteredAppOrders.map(async (order) => {
					const [placeOrderItem] = order.placedOrderItems;

					const date = new Date(order.createDate);
					const options: Intl.DateTimeFormatOptions = {
						day: 'numeric',
						month: 'short',
						year: 'numeric',
					};
					const formattedDate = date.toLocaleDateString(
						'en-US',
						options
					);

					const version = await getCustomFieldExpandoValue({
						className:
							'com.liferay.commerce.product.model.CPInstance',
						classPK: placeOrderItem.skuId,
						columnName: 'version',
						companyId: Number(getCompanyId()),
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
											companyId: Number(getCompanyId()),
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
						provisioning: order.orderStatusInfo.label_i18n,
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

			setSolutionsItems(filteredSolutionsOrders);

			setPurchasedAppTable((previousPurchasedAppTable: any) => {
				return {
					...previousPurchasedAppTable,
					items: newAppOrderItems,
					totalCount: placedOrders.totalCount,
				};
			});
		};

		makeFetch();
	}, [channel?.id, page, purchasedAppTable.pageSize, selectedAccount]);

	return (
		<div className="purchased-apps-dashboard-page-container">
			<DashboardNavigation
				accountAppsNumber={purchasedAppTable.items.length}
				accountIcon={showAccountImage(commerceAccount?.logoURL)}
				accounts={accounts as Account[]}
				currentAccount={selectedAccount}
				dashboardNavigationItems={dashboardNavigationItems}
			/>

			<Outlet
				context={{
					dashboardNavigationItems,
					page,
					purchasedAppTable,
					selectedAccount,
					setPage,
					solutionsItems,
				}}
			/>
		</div>
	);
};

export default PurchasedAppsDashboardOutlet;
