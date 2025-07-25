/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Outlet} from 'react-router-dom';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {PageRenderer} from '../../components/Page';
import useAccounts, {useAccount} from '../../hooks/data/useAccounts';
import {useSSATRialsExtend} from './useSSATrialsExtend';
import {getAccountImage} from '../../utils/util';

const SSADashboardOutlet = () => {
	const accountsSearch = useAccounts();
	const {
		data: selectedAccount,
		error: errorAccount,
		isLoading: isLoadingAccount,
	} = useAccount();

	const {
		data: ssaTrialExtend,
		error: errorTrialsExtend,
		isLoading: isLoadingTrialsExtend,
		mutate: ssaTrialExtendMutate,
	} = useSSATRialsExtend({
		accountId: selectedAccount?.id,
	});

	const error = errorAccount || errorTrialsExtend;
	const isLoading = isLoadingAccount || isLoadingTrialsExtend;

	return (
		<PageRenderer error={error} isLoading={isLoading}>
			<div className="published-apps-dashboard-page-container">
				<DashboardNavigation
					accountIcon={getAccountImage(selectedAccount?.logoURL)}
					accountsSearch={accountsSearch}
					currentAccount={selectedAccount as any}
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
							ssaTrialExtendMutate,
						}}
					/>
				</span>
			</div>
		</PageRenderer>
	);
};

export default SSADashboardOutlet;
