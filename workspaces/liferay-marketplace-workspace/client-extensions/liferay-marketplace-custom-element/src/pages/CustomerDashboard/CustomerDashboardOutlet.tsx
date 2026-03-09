/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {Outlet, useOutletContext} from 'react-router-dom';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {PageRenderer} from '../../components/Page';
import {useMarketplaceContext} from '../../context/MarketplaceContext';
import {useAccount} from '../../hooks/data/useAccounts';
import i18n from '../../i18n';

import './CustomerDashboard.scss';

const CustomerDashboardOutlet = () => {
	const {data: selectedAccount, error, isLoading} = useAccount();
	const {properties} = useMarketplaceContext();

	const navigationItems = useMemo(() => {
		const items = [
			{
				itemTitle: i18n.translate('my-products'),
				path: '/products',
				symbol: 'display-content',
			},
			{
				itemTitle: i18n.translate('my-apps'),
				path: '/',
				symbol: 'grid',
			},
			{
				itemTitle: i18n.translate('my-solutions'),
				path: '/solutions',
				symbol: 'polls',
			},
			{
				itemTitle: i18n.translate('dxp-connections'),
				path: '/connections',
				symbol: 'liferay-ac',
			},
		];

		return properties.featurePreview.includes('new-product-section')
			? items
			: items.slice(1);
	}, [properties.featurePreview]);

	return (
		<PageRenderer error={error} isLoading={isLoading}>
			<div className="purchased-apps-dashboard-page-container">
				<DashboardNavigation
					dashboardNavigationItems={navigationItems}
				/>

				<Outlet
					context={{
						selectedAccount,
					}}
				/>
			</div>
		</PageRenderer>
	);
};

const useCustomerDashboardOutletContext = () =>
	useOutletContext<{selectedAccount: Account}>();

export {useCustomerDashboardOutletContext};

export default CustomerDashboardOutlet;
