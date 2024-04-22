/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import withProviders from '../../hoc/withProviders';
import CustomerDashboardOutlet from './CustomerDashboardOutlet';
import Apps from './pages/Apps';
import App from './pages/Apps/App/App';
import AppOutlet from './pages/Apps/App/AppOutlet';
import CreateLicense from './pages/Apps/App/Licenses/CreateLicense';
import Licenses from './pages/Apps/App/Licenses/Licenses';
import Members from './pages/Members';
import Solutions from './pages/Solutions';

const CustomerDashboardRouter = () => (
	<HashRouter>
		<Routes>
			<Route element={<CustomerDashboardOutlet />}>
				<Route element={<Apps />} index />
				<Route element={<AppOutlet />} path="order/:orderId">
					<Route element={<App />} index />
					<Route element={<Licenses />} path="licenses" />
				</Route>
				<Route element={<Members />} path="members" />
				<Route element={<Solutions />} path="solutions" />
			</Route>

			<Route
				element={<CreateLicense />}
				path="order/:orderId/create-license"
			/>
		</Routes>
	</HashRouter>
);

export default withProviders(CustomerDashboardRouter);
