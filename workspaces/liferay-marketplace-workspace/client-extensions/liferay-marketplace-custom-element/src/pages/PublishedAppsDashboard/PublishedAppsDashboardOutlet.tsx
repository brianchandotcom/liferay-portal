/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo, useState} from 'react';
import {Outlet} from 'react-router-dom';
import useSWR from 'swr';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {AppProps} from '../../components/DashboardTable/DashboardTable';
import HeadlessAdminUserImpl from '../../services/rest/HeadlessAdminUser';

import './PublishedAppsDashboard.scss';

import ClayLoadingIndicator from '@clayui/loading-indicator';

import SearchBuilder from '../../core/SearchBuilder';
import {Liferay} from '../../liferay/liferay';
import HeadlessCommerceAdminCatalogImpl from '../../services/rest/HeadlessCommerceAdminCatalog';
import {getAccountInfoFromCommerce, getAccounts} from '../../utils/api';
import {getAccountImage} from '../../utils/util';
import {initialDashboardNavigationItems} from './PublishedDashboardPageUtil';

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

	const {data: publishedProductTable = {}, isLoading} = useSWR(
		catalogId ? `/user-published-apps/${selectedAccount?.id}/${page}` : null,
		 () =>
			HeadlessCommerceAdminCatalogImpl.getProducts(
				new URLSearchParams({
					filter: new SearchBuilder()
						.eq('catalogId', catalogId as number, {unquote: true})
						.and()
						.lambda('categoryNames', 'App')
						.build(),
						nestedFields:
						'attachments,productChannels,productSpecifications',
						page: page.toString(),
				})
			)
	);

	return (
		<div className="published-apps-dashboard-page-container">
			<DashboardNavigation
				accountAppsNumber={publishedProductTable.totalCount}
				accountIcon={getAccountImage(commerceAccount?.logoURL)}
				accounts={accounts ?? []}
				currentAccount={selectedAccount}
				dashboardNavigationItems={initialDashboardNavigationItems}
			/>

			{isLoading ? (
				<ClayLoadingIndicator />
			) : (
				<Outlet
					context={{
						accountId,
						appsTotalCount: publishedProductTable.totalCount,
						catalogId,
						commerceAccount,
						page,
						publishedProductTable,
						selectedAccount,
						selectedApp,
						setCommerceAccount,
						setPage,
						setSelectedApp,
						setShowDashboardNavigation,
						showDashboardNavigation,
					}}
				/>
			)}
		</div>
	);
};

export {useAccountCached};

export default PublishedAppsDashboardOutlet;
