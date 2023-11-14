/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo, useState} from 'react';
import {Outlet, useParams} from 'react-router-dom';
import useSWR from 'swr';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {AppProps} from '../../components/DashboardTable/DashboardTable';
import HeadlessAdminUserImpl from '../../services/rest/HeadlessAdminUser';

import './PublishedAppsDashboard.scss';
import {
	getAccountInfoFromCommerce,
	getAccounts,
	getProducts,
} from '../../utils/api';
import {
	getAccountImage,
	getProductVersionFromSpecifications,
} from '../../utils/util';
import {
	formatDate,
	getAppListProductIds,
	getAppListProductSpecifications,
	getProductTypeFromSpecifications,
	initialDashboardNavigationItems,
} from './PublishedDashboardPageUtil';
import { Liferay } from '../../liferay/liferay';

const PAGE_SIZE = 10;

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
	const [commerceAccount, setCommerceAccount] = useState<CommerceAccount>();
	const [selectedApp, setSelectedApp] = useState<AppProps>();
	const [showDashboardNavigation, setShowDashboardNavigation] = useState(
		true
	);
	const {accountId} = Liferay.CommerceContext.account || {};
	const [page, setPage] = useState(1);

	const {data: accounts = []} = useSWR('/published/accounts', async () => {
		const accounts = await getAccounts();

		return accounts.items ?? [];
	});

	const selectedAccount = useAccountCached(
		accounts ?? [],
		accountId as string
	);

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
		const getAccountCommerce = async () => {
			const commerceAccountResponse = await getAccountInfoFromCommerce(
				selectedAccount.id
			);

			setCommerceAccount(commerceAccountResponse);
		};

		getAccountCommerce();
	}, [selectedAccount?.id]);

	const {
		data: publishedAppTable = {items: [], pageSize: 10, totalCount: 0},
		isLoading,
	} = useSWR(`/published-apps/${selectedAccount?.id}`, async () => {
		if (!catalogId) {
			return {
				items: [],
				totalCount: 0,
			};
		}

		const {items: products} = await getProducts(
			'attachments,productChannels'
		);

		const appListProductSpecifications = await getAppListProductSpecifications(
			getAppListProductIds(products)
		);

		const productSpecificationsMap = appListProductSpecifications.map(
			(productSpecification, index) => ({
				productId: products[index].id,
				specification: productSpecification,
			})
		);

		const producsFiltered = products
			.filter((product) => {
				const marketPlaceChannel = !!product.productChannels.find(
					(channel) => channel.name === 'Marketplace Channel'
				);

				const isApp = product.categories.find(
					(category) => category.name === 'App'
				);

				return (
					isApp &&
					marketPlaceChannel &&
					product.catalogId === catalogId
				);
			})
			.map((product) => ({
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
					productSpecificationsMap.find(
						({productId}) => productId === product.id
					)?.specification ?? []
				),
				updatedDate: formatDate(product.modifiedDate),
				version: getProductVersionFromSpecifications(
					productSpecificationsMap.find(
						({productId}) => productId === product.id
					)?.specification ?? []
				),
			}));

		return {
			items: producsFiltered.slice(
				PAGE_SIZE * (page - 1),
				PAGE_SIZE * (page - 1) + PAGE_SIZE
			),
			pageSize: PAGE_SIZE,
			totalCount: producsFiltered.length,
		};
	});

	return (
		<div className="published-apps-dashboard-page-container">
			<DashboardNavigation
				accountAppsNumber={publishedAppTable.totalCount}
				accountIcon={getAccountImage(commerceAccount?.logoURL)}
				accounts={accounts ?? []}
				currentAccount={selectedAccount}
				dashboardNavigationItems={initialDashboardNavigationItems.map(
					(navigationItems) => {
						if (navigationItems.itemName === 'apps') {
							return {
								...navigationItems,
								items: [].slice(0, 4),
							};
						}

						return navigationItems;
					}
				)}
			/>

			<Outlet
				context={{
					accountId,
					appsTotalCount: publishedAppTable.totalCount,
					catalogId,
					commerceAccount,
					loading: isLoading,
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
