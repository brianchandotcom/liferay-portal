/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Outlet} from 'react-router-dom';
import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {initialAdministratorDashboardNavigationItems} from './AdministratorDashboardPageUtil';

const AdministratorDashboardOutlet = () => {
	return (
		<div className="d-flex">
			<DashboardNavigation
				dashboardNavigationItems={
					initialAdministratorDashboardNavigationItems
				}
			/>
			<span className="ml-6 w-100 h-vh-100">
				<Outlet />
			</span>
		</div>
	);
};

export default AdministratorDashboardOutlet;
