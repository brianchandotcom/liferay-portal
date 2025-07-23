/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Outlet} from 'react-router-dom';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {PageRenderer} from '../../components/Page';
import {useAccount} from '../../hooks/data/useAccounts';
import {useSSATRialsExtend} from './useSSATrialsExtend';

const SSADashboardOutlet = () => {
	const {data: selectedAccount, error, isLoading} = useAccount();
	const ssaTrialExtend = useSSATRialsExtend({
		accountId: selectedAccount?.id ?? 0,
	});

	return (
		<PageRenderer error={error} isLoading={isLoading}>
			<div className="published-apps-dashboard-page-container">
				<DashboardNavigation
					dashboardNavigationItems={[
						{
							itemTitle: 'SaaS Demos',
							path: '/',
							symbol: 'nodes',
						},
					]}
				/>
				<span className="h-vh-100 ml-6 w-100">
					<Outlet
						context={{
							selectedAccount,
							ssaTrialExtend,
						}}
					/>
				</span>
			</div>
		</PageRenderer>
	);
};

export default SSADashboardOutlet;
