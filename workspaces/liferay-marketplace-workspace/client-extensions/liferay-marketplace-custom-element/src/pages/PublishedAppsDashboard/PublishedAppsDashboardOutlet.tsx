/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo, useState} from 'react';
import {Outlet} from 'react-router-dom';
import useSWR from 'swr';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';

import './PublishedAppsDashboard.scss';

import ClayLoadingIndicator from '@clayui/loading-indicator';

import SearchBuilder from '../../core/SearchBuilder';
import {useCatalogs} from '../../hooks/data/useCatalogs';
import {
	useSupplierAccount,
	useSupplierAccounts,
} from '../../hooks/data/useSupplierAccounts';
import {Liferay} from '../../liferay/liferay';
import HeadlessCommerceAdminCatalogImpl from '../../services/rest/HeadlessCommerceAdminCatalog';
import {getAccountImage} from '../../utils/util';
import {initialDashboardNavigationItems} from './PublishedDashboardPageUtil';

const PublishedAppsDashboardOutlet = () => {
	const {accountId} = Liferay.CommerceContext.account || {};
	const [page, setPage] = useState(1);
	const [showDashboardNavigation, setShowDashboardNavigation] = useState(
		true
	);

	const {data: catalogs = []} = useCatalogs();
	const {data: supplierAccount} = useSupplierAccount();
	const {data: supplierAccounts = []} = useSupplierAccounts();

	const catalogId = useMemo(
		() => catalogs.find((catalog) => catalog.accountId === accountId)?.id,
		[accountId, catalogs]
	);

	const {data: publishedProductTable = {}, isLoading} = useSWR(
		catalogId
			? `/user-published-apps/${supplierAccount?.id}/${page}`
			: null,
		() =>
			HeadlessCommerceAdminCatalogImpl.getProducts(
				new URLSearchParams({
					filter: new SearchBuilder()
						.eq('catalogId', catalogId as number, {unquote: true})
						.and()
						.lambda('categoryNames', 'App')
						.build(),
					nestedFields:
						'attachments,images,productChannels,productSpecifications',
					page: page.toString(),
				})
			)
	);

	return (
		<div className="published-apps-dashboard-page-container">
			<DashboardNavigation
				accountAppsNumber={publishedProductTable.totalCount}
				accountIcon={getAccountImage(supplierAccount?.logoURL)}
				accounts={(supplierAccounts as unknown) as Account[]}
				currentAccount={(supplierAccount as unknown) as Account}
				dashboardNavigationItems={initialDashboardNavigationItems}
			/>

			{isLoading ? (
				<ClayLoadingIndicator />
			) : (
				<Outlet
					context={{
						appsTotalCount: publishedProductTable.totalCount,
						catalogId,
						publishedProductTable,
						selectedAccount: supplierAccount,
						setPage,
						setShowDashboardNavigation,
						showDashboardNavigation,
					}}
				/>
			)}
		</div>
	);
};

export default PublishedAppsDashboardOutlet;
