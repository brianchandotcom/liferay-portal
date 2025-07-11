/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import SSADashboardOutlet from './SSADashboardOutlet';

import './index.scss';
import withProviders from '../../hoc/withProviders';
import SSATrials from './pages';

const SSADashboardRouter = () => {
	return (
		<HashRouter>
			<Routes>
				<Route element={<SSADashboardOutlet />}>
					<Route element={<SSATrials />} index />
				</Route>
			</Routes>
		</HashRouter>
	);
};

export default withProviders(SSADashboardRouter);
