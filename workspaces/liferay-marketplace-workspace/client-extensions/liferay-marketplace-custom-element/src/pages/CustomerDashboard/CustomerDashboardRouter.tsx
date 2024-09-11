/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import withProviders from '../../hoc/withProviders';
import CustomerDashboardOutlet from './CustomerDashboardOutlet';
import Apps from './pages/Apps';
import App from './pages/Apps/App/App';
import AppOutlet from './pages/Apps/App/AppOutlet';
import Download from './pages/Apps/App/Download/Download';
import CreateLicense from './pages/Apps/App/Licenses/CreateLicense';
import Licenses from './pages/Apps/App/Licenses/Licenses';
import Provisioning from './pages/Apps/App/Provisioning/Provisioning';
import Members from './pages/Members';
import Solutions from './pages/Solutions';
import Solution from './pages/Solutions/Solution';
import SolutionOutlet from './pages/Solutions/SolutionOutlet';

const CustomerDashboardRouter = () => {
	const {properties} = useMarketplaceContext();

	return (
		<HashRouter>
			<Routes>
				<Route element={<CustomerDashboardOutlet />}>
					<Route element={<Apps />} index />
					<Route element={<AppOutlet />} path="order/:orderId">
						<Route element={<App />} index />

						{properties.featureFlags?.includes('LPD-21582') && (
							<Route element={<Download />} path="download" />
						)}

						<Route element={<Licenses />} path="licenses" />

						{properties.featureFlags?.includes('LPD-34129') && (
							<Route
								element={<Provisioning />}
								path="cloud-provisioning"
							/>
						)}
					</Route>
					<Route element={<Members />} path="members" />
					<Route element={<Solutions />} path="solutions" />

					<Route
						element={<SolutionOutlet />}
						path="solutions/:orderId"
					>
						<Route element={<Solution />} index />
					</Route>
				</Route>

				<Route
					element={<CreateLicense />}
					path="order/:orderId/create-license"
				/>
			</Routes>
		</HashRouter>
	);
};

export default withProviders(CustomerDashboardRouter);
