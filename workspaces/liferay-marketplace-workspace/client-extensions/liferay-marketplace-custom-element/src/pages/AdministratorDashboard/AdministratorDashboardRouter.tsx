/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Routes, Route} from 'react-router-dom';

import AdministratorDashboardOutlet from './AdministratorDashboardOutlet';
import './index.scss';
import Metrics from './Metrics/Metrics';
import PublisherRequest from './PublisherRequest';
import AppAdministrator from './AppsAdministrator';

const AdministratorDashboardRouter = () => {
	return (
		<HashRouter>
			<Routes>
				<Route element={<AdministratorDashboardOutlet />}>
					<Route element={<Metrics />} index />

					<Route
						element={<PublisherRequest />}
						path="publisher-request"
					/>

					<Route
						element={<AppAdministrator />}
						path="apps-administrator"
					/>
				</Route>
			</Routes>
		</HashRouter>
	);
};

export default AdministratorDashboardRouter;
