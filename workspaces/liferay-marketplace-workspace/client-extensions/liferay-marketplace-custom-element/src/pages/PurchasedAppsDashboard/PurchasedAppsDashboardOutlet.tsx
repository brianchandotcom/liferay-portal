/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Outlet} from 'react-router-dom';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {getAccountImage} from '../../utils/util';
import {initialDashboardNavigationItems as dashboardNavigationItems} from './PurchasedDashboardPageUtil';

import './PurchasedAppsDashboard.scss';
import useAccounts, {useAccount} from '../../hooks/data/useAccounts';

export type PurchasedAppProps = {
	name: string;
	orderId: number;
	orderTypeExternalReferenceCode: string;
	productId: number;
	project?: string;
	provisioning: string;
	provisioningLabel: string;
	purchasedBy?: string;
	purchasedDate: string;
	thumbnail: string;
	type: string;
	version: string;
	virtualURL: string;
};

const PurchasedAppsDashboardOutlet = () => {
	const accountsSearch = useAccounts();
	const {data: selectedAccount} = useAccount();

	return (
		<div className="purchased-apps-dashboard-page-container">
			<DashboardNavigation
				accountIcon={getAccountImage(selectedAccount?.logoURL)}
				accountsSearch={accountsSearch}
				currentAccount={selectedAccount as any}
				dashboardNavigationItems={dashboardNavigationItems}
			/>

			<Outlet
				context={{
					dashboardNavigationItems,
					selectedAccount,
				}}
			/>
		</div>
	);
};

export default PurchasedAppsDashboardOutlet;
