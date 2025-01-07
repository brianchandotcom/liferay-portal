/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Outlet, useOutletContext} from 'react-router-dom';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {PageRenderer} from '../../components/Page';
import useAccounts, {useAccount} from '../../hooks/data/useAccounts';
import i18n from '../../i18n';
import {getAccountImage} from '../../utils/util';

import './CustomerDashboard.scss';

export const dashboardNavigationItems = [
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
	const accountsSearch = useAccounts();

	return (
		<PageRenderer error={error} isLoading={isLoading}>
			<div className="purchased-apps-dashboard-page-container">
				<DashboardNavigation
					accountIcon={getAccountImage(selectedAccount?.logoURL)}
					accountsSearch={accountsSearch}
					currentAccount={selectedAccount as any}
					dashboardNavigationItems={dashboardNavigationItems}
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
