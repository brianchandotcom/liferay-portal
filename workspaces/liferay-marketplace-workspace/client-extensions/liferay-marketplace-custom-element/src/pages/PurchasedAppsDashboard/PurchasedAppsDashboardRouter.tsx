/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import CreateLicense from '../CreateLicense';
import Apps from './Apps';
import App from './Apps/App';
import Members from './Members';
import PurchasedAppsDashboardOutlet from './PurchasedAppsDashboardOutlet';
import Solutions from './Solutions';

const PurchasedAppsDashboardRouter = () => (
	<HashRouter>
		<Routes>
			<Route element={<PurchasedAppsDashboardOutlet />}>
				<Route path="/">
					<Route element={<Apps />} index />
					<Route path="app/:appId">
						<Route element={<App />} index />
					</Route>
				</Route>
				<Route element={<Members />} path="members" />
				<Route element={<Solutions />} path="solutions" />
			</Route>
			<Route
				element={<CreateLicense />}
				path="app/:appId/order/:orderId/create-license"
			/>
		</Routes>
	</HashRouter>
);

export default PurchasedAppsDashboardRouter;
