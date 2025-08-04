/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {Outlet} from 'react-router-dom';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {PageRenderer} from '../../components/Page';
import {useMarketplaceContext} from '../../context/MarketplaceContext';
import {Liferay} from '../../liferay/liferay';
import {useSSATrialsExtend} from './useSSATrialsExtend';

const SSADashboardOutlet = () => {
	const {properties} = useMarketplaceContext();
	const selectedAccountId = Number(properties.accountId);

	useEffect(() => {
		if (Liferay.CommerceContext.account) {
			Liferay.CommerceContext.account.accountId = selectedAccountId;
		}
	}, [selectedAccountId]);

	const {
		data: ssaTrialExtend,
		error,
		isLoading,
		mutate: ssaTrialExtendMutate,
	} = useSSATrialsExtend({
		accountId: selectedAccountId,
	});

	return (
		<PageRenderer error={error} isLoading={isLoading}>
			<div className="published-apps-dashboard-page-container">
				<DashboardNavigation
					currentAccount={selectedAccountId as any}
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
							selectedAccountId,
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
