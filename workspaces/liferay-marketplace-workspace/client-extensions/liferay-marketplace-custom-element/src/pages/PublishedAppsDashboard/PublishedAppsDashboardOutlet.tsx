/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';
import {Outlet} from 'react-router-dom';
import useSWR from 'swr';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';

import './PublishedAppsDashboard.scss';

import ClayLoadingIndicator from '@clayui/loading-indicator';

import SearchBuilder from '../../core/SearchBuilder';
import useAccounts, {useAccount} from '../../hooks/data/useAccounts';
import HeadlessCommerceAdminCatalogImpl from '../../services/rest/HeadlessCommerceAdminCatalog';
import {getAccountImage} from '../../utils/util';
import {initialDashboardNavigationItems} from './PublishedDashboardPageUtil';

type PublishedAppsDashboardOutletProps = {
	accountsSearch: ReturnType<typeof useAccounts>;
	catalogId: any;
};

const PublishedAppsDashboardOutlet: React.FC<PublishedAppsDashboardOutletProps> = ({
	accountsSearch,
	catalogId,
}) => {
	const [page, setPage] = useState(1);

	const {
		data: supplierAccount,
		isLoading: isLoadingSupplierAccount,
	} = useAccount();

	const {data: publishedProductTable = {}, isLoading, isValidating} = useSWR(
		`/user-published-apps/${supplierAccount?.id}/${page}/${catalogId}`,
		() => {
			if (!catalogId) {
				return {items: [], totalCount: 0};
			}

			return HeadlessCommerceAdminCatalogImpl.getProducts(
				new URLSearchParams({
					'accountId': '-1',
					'attachments.accountId': '-1',
					'filter': new SearchBuilder()
						.eq('catalogId', catalogId as number, {unquote: true})
						.and()
						.lambda('categoryNames', 'App')
						.build(),
					'images.accountId': '-1',
					'nestedFields':
						'attachments,images,productChannels,productSpecifications',
					'page': page.toString(),
					'skus.accountId': '-1',
				})
			);
		}
	);

	if (isLoadingSupplierAccount) {
		return <ClayLoadingIndicator />;
	}

	return (
		<div className="published-apps-dashboard-page-container">
			<DashboardNavigation
				accountAppsNumber={publishedProductTable.totalCount}
				accountIcon={getAccountImage(supplierAccount?.logoURL)}
				accountsSearch={accountsSearch}
				currentAccount={(supplierAccount as unknown) as Account}
				dashboardNavigationItems={initialDashboardNavigationItems}
			/>

			{isLoading || isValidating ? (
				<ClayLoadingIndicator />
			) : (
				<Outlet
					context={{
						appsTotalCount: publishedProductTable.totalCount,
						catalogId,
						page,
						publishedProductTable,
						selectedAccount: supplierAccount,
						setPage,
					}}
				/>
			)}
		</div>
	);
};

export default PublishedAppsDashboardOutlet;
