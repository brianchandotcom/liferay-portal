/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Outlet} from 'react-router-dom';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';

import './PublishedAppsDashboard.scss';

import ClayLoadingIndicator from '@clayui/loading-indicator';

import useAccounts, {useAccount} from '../../hooks/data/useAccounts';
import {getAccountImage} from '../../utils/util';
import {initialDashboardNavigationItems} from './PublishedDashboardPageUtil';

type PublishedAppsDashboardOutletProps = {
	accountsSearch: ReturnType<typeof useAccounts>;
	catalogId: any;
};

const PublishedDashboardOutlet: React.FC<PublishedAppsDashboardOutletProps> = ({
	accountsSearch,
	catalogId,
}) => {
	const {
		data: supplierAccount,
		isLoading: isLoadingSupplierAccount,
	} = useAccount();

	return (
		<div className="published-apps-dashboard-page-container">
			<DashboardNavigation
				accountIcon={getAccountImage(supplierAccount?.logoURL)}
				accountsSearch={accountsSearch}
				currentAccount={(supplierAccount as unknown) as Account}
				dashboardNavigationItems={initialDashboardNavigationItems}
			/>

			{isLoadingSupplierAccount ? (
				<ClayLoadingIndicator />
			) : (
				<Outlet
					context={{
						catalogId,
						selectedAccount: supplierAccount,
					}}
				/>
			)}
		</div>
	);
};

export default PublishedDashboardOutlet;
