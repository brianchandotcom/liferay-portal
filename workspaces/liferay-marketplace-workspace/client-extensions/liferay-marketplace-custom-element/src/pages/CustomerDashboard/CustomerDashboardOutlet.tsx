/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {Outlet, useNavigate, useOutletContext} from 'react-router-dom';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {PageRenderer} from '../../components/Page';
import {useAccount} from '../../hooks/data/useAccounts';
import i18n from '../../i18n';

import './CustomerDashboard.scss';

const navigationItems = [
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

const CustomerDashboardOutlet = () => {
	const {data: selectedAccount, error, isLoading} = useAccount();

	const navigate = useNavigate();

	useEffect(() => {
		const searchParams = new URLSearchParams(window.location.search);

		const redirectTo = searchParams.get('redirectTo');

		if (!redirectTo) {
			return;
		}

		const url = new URL(window.location.href);

		url.searchParams.delete('redirectTo');

		window.history.replaceState({}, '', url);

		navigate(redirectTo, {replace: true});
	}, [navigate]);

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
