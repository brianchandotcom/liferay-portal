/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import CreateLicense from '../CreateLicense';
import Accounts from './Accounts/Accounts';
import Apps from './Apps';
import App from './Apps/App';
import Members from './Members';
import Projects from './Projects';
import PublishedAppsDashboardOutlet from './PublishedAppsDashboardOutlet';
import Solutions from './Solutions';

const PublishedAppsDashboardRouter = () => (
	<HashRouter>
		<Routes>
			<Route element={<PublishedAppsDashboardOutlet />}>
				<Route path="/">
					<Route element={<Apps />} index />
					<Route path="app/:appId">
						<Route element={<App />} index />
						<Route
							element={<CreateLicense />}
							path="create-license"
						/>
					</Route>
				</Route>
				<Route element={<Accounts />} path="accounts" />
				<Route element={<Members />} path="members" />
				<Route element={<Projects />} path="projects" />
				<Route element={<Solutions />} path="solutions" />
			</Route>
		</Routes>
	</HashRouter>
);

export default PublishedAppsDashboardRouter;
