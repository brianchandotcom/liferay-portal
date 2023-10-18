/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

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
					<Route element={<App />} path="app/:appId" />
				</Route>
				<Route element={<Members />} path="members" />
				<Route element={<Solutions />} path="solutions" />
			</Route>
		</Routes>
	</HashRouter>
);

export default PurchasedAppsDashboardRouter;
