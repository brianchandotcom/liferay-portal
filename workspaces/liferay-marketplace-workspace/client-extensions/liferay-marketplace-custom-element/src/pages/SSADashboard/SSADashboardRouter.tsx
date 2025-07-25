/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { HashRouter, Route, Routes } from 'react-router-dom';

import withProviders from '../../hoc/withProviders';
import SSADashboardOutlet from './SSADashboardOutlet';

import './index.scss';
import SaaSTrials from './pages';
import TrialDetails from './pages/trialDetails/TrialDetails';

const SSADashboardRouter = () => (
	<HashRouter>
		<Routes>
			<Route element={<SSADashboardOutlet />}>
				<Route element={<SaaSTrials />} index />
				<Route element={<TrialDetails />} path="details/:orderId" />
			</Route>
		</Routes>
	</HashRouter>
);

export default withProviders(SSADashboardRouter);
