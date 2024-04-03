/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Outlet} from 'react-router-dom';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {getAccountImage} from '../../utils/util';

import './CustomerDashboard.scss';
import {PageRenderer} from '../../components/Page';
import useAccounts, {useAccount} from '../../hooks/data/useAccounts';

export const dashboardNavigationItems = [
	{
		itemTitle: 'My Apps',
		path: '/',
		symbol: 'grid',
	},
	{
		itemTitle: 'My Solutions',
		path: '/solutions',
		symbol: 'polls',
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

export default CustomerDashboardOutlet;
