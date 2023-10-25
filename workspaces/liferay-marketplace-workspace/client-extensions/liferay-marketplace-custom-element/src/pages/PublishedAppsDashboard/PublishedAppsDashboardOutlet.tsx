/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo, useState} from 'react';
import {Outlet, useSearchParams} from 'react-router-dom';
import useSWR from 'swr';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {AppProps} from '../../components/DashboardTable/DashboardTable';
import {Liferay} from '../../liferay/liferay';
import HeadlessAdminUserImpl from '../../services/rest/HeadlessAdminUser';

import './PublishedAppsDashboard.scss';
import {
	getAccountInfoFromCommerce,
	getAccounts,
	getProducts,
} from '../../utils/api';
import {
	getProductVersionFromSpecifications,
	showAccountImage,
} from '../../utils/util';
import {
	formatDate,
	getAppListProductIds,
	getAppListProductSpecifications,
	getProductTypeFromSpecifications,
	initialDashboardNavigationItems,
} from './PublishedDashboardPageUtil';

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

const PublishedAppsDashboardOutlet = () => {
	const [searchParams] = useSearchParams();
	const accountId = searchParams.get('accountId');
	const [commerceAccount, setCommerceAccount] = useState<CommerceAccount>();
	const [selectedApp, setSelectedApp] = useState<AppProps>();
	const [showDashboardNavigation, setShowDashboardNavigation] = useState(
		true
	);
	const [apps, setApps] = useState<any[]>([]);
	const [page, setPage] = useState(1);
	const [publishedAppTable, setPublishedAppTable] = useState<any>({
		items: [],
		pageSize: 7,
		totalCount: 1,
	});
	const [appsTotalCount, setAppTotalCount] = useState<number>(0);

	const [loading, setLoading] = useState(false);

	const buttonRedirectURL = Liferay.ThemeDisplay.getCanonicalURL().replaceAll(
		'/publisher-dashboard',
		'/create-new-app'
	);

	const {data: accounts = []} = useSWR('/published/accounts', async () => {
		const accounts = await getAccounts();

		return accounts.items ?? [];
	});

	const selectedAccount = useAccountCached(accounts ?? [], accountId);

	const catalogId = useMemo(() => {
		const accountCustomField = selectedAccount?.customFields?.find(
			(customField: any) => customField.name === 'CatalogId'
		);

		if (accountCustomField) {
			const accountCatalogId = Number(
				accountCustomField.customValue.data
			);

			return accountCatalogId;
		}
	}, [selectedAccount?.customFields]);

	useEffect(() => {
		const makeFetch = async () => {
			if (!catalogId) {
				return;
			}

			setLoading(true);

			const {items: products} = await getProducts(
				'attachments,productChannels'
			);

			const appListProductSpecifications = await getAppListProductSpecifications(
				getAppListProductIds(products)
			);

			const _apps: AppProps[] = [];

			products.forEach((product, index: number) => {
				const marketPlaceChannel = !!product.productChannels.find(
					(channel) => channel.name === 'Marketplace Channel'
				);

				const isApp = product.categories.find(
					(category) => category.name === 'App'
				);

				if (
					isApp &&
					marketPlaceChannel &&
					product.catalogId === catalogId
				) {
					_apps.push({
						attachments: product.attachments,
						catalogId: product.catalogId,
						externalReferenceCode: product.externalReferenceCode,
						name: product.name.en_US,
						productId: product.productId,
						status: product.workflowStatusInfo.label.replace(
							/(^\w|\s\w)/g,
							(m: string) => m.toUpperCase()
						),
						thumbnail: product.thumbnail,
						type: getProductTypeFromSpecifications(
							appListProductSpecifications[index]
						),
						updatedDate: formatDate(product.modifiedDate),
						version: getProductVersionFromSpecifications(
							appListProductSpecifications[index]
						),
					});
				}
			});

			const commerceAccountResponse = await getAccountInfoFromCommerce(
				selectedAccount?.id
			);

			setCommerceAccount(commerceAccountResponse);

			setApps(_apps);
			setAppTotalCount(_apps.length);
			setPublishedAppTable({
				items: _apps.slice(
					publishedAppTable.pageSize * (page - 1),
					publishedAppTable.pageSize * (page - 1) +
						publishedAppTable.pageSize
				),
				pageSize: publishedAppTable.pageSize,
				totalCount: _apps.length,
			});

			setLoading(false);
		};

		makeFetch();
	}, [catalogId, page, publishedAppTable.pageSize, selectedAccount]);

	return (
		<div className="published-apps-dashboard-page-container">
			<DashboardNavigation
				accountAppsNumber={appsTotalCount}
				accountIcon={showAccountImage(commerceAccount?.logoURL)}
				accounts={accounts ?? []}
				currentAccount={selectedAccount}
				dashboardNavigationItems={initialDashboardNavigationItems.map(
					(navigationItems) => {
						if (navigationItems.itemName === 'apps') {
							return {
								...navigationItems,
								items: apps.slice(0, 4),
							};
						}

						return navigationItems;
					}
				)}
			/>

			<Outlet
				context={{
					accountId,
					appsTotalCount,
					buttonRedirectURL,
					catalogId,
					commerceAccount,
					loading,
					publishedAppTable,
					selectedAccount,
					selectedApp,
					setCommerceAccount,
					setPage,
					setSelectedApp,
					setShowDashboardNavigation,
					showDashboardNavigation,
				}}
			/>
		</div>
	);
};

export {useAccountCached};

export default PublishedAppsDashboardOutlet;
