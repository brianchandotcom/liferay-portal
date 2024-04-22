/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Outlet} from 'react-router-dom';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {initialAdministratorDashboardNavigationItems} from './AdministratorDashboardPageUtil';

const AdministratorDashboardOutlet = () => (
	<div className="d-flex">
		<div className="d-flex dashboard-navigation-container">
			<div className="dashboard-navigation-body">
				<DashboardNavigation
					dashboardNavigationItems={
						initialAdministratorDashboardNavigationItems
					}
				/>
			</div>
		</div>

		<span className="h-vh-100 ml-6 w-100">
			<Outlet />
		</span>
	</div>
);

export default AdministratorDashboardOutlet;
