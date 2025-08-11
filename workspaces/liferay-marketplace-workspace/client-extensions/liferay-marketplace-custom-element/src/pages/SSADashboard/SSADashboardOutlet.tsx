/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Outlet, useOutletContext} from 'react-router-dom';
import useSWR, {KeyedMutator} from 'swr';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {PageRenderer} from '../../components/Page';
import {useMarketplaceContext} from '../../context/MarketplaceContext';
import HeadlessAdminUser from '../../services/rest/HeadlessAdminUser';
import {useSSATrialsExtend} from './useSSATrialsExtend';

const SSADashboardOutlet = () => {
	const {properties} = useMarketplaceContext();

	const {data: ssaAccount, isLoading: isSSALoading} = useSWR(
		'/ssa-account',
		() =>
			HeadlessAdminUser.getAccountByExternalReferenceCode(
				properties.accountExternalReferenceCode
			)
	);

	const {
		data: ssaTrialExtend,
		error,
		isLoading,
		mutate: ssaTrialExtendMutate,
	} = useSSATrialsExtend(ssaAccount!);

	return (
		<PageRenderer error={error} isLoading={isLoading || isSSALoading}>
			<div className="published-apps-dashboard-page-container">
				<DashboardNavigation
					currentAccount={ssaAccount}
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
							selectedAccountId: ssaAccount?.id,
							ssaAccount,
							ssaTrialExtend,
							ssaTrialExtendMutate,
						}}
					/>
				</span>
			</div>
		</PageRenderer>
	);
};

const useSSADashboardOutlet = () => {
	return useOutletContext<{
		selectedAccountId: number;
		ssaAccount: Account;
		ssaTrialExtend: APIResponse<TrialExtend>;
		ssaTrialExtendMutate: KeyedMutator<APIResponse<TrialExtend>>;
	}>();
};

export {useSSADashboardOutlet};

export default SSADashboardOutlet;
