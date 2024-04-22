/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import withProviders from '../../hoc/withProviders';
import GetAppContextProvider from './GetAppContextProvider';
import GetAppOutlet from './GetAppOutlet';
import GetAppPage from './pages/Account';
import {InsuficientResources} from './pages/InsuficientResources';
import ContactSalesPage from './pages/InsuficientResources/ContactSales';
import ContactSalesForm from './pages/InsuficientResources/ContactSalesForm';
import LicenseStep from './pages/License';
import Payment from './pages/Payment';
import ProjectStep from './pages/Project';

const GetAppRouter = () => (
	<HashRouter>
		<GetAppContextProvider>
			<Routes>
				<Route element={<GetAppOutlet />} path="/">
					<Route element={<GetAppPage />} index />
					<Route element={<ProjectStep />} path="project" />
					<Route element={<LicenseStep />} path="license" />
					<Route element={<Payment />} path="payment" />
				</Route>

				<Route
					element={<InsuficientResources />}
					path="insuficient-resources/:projectId/:accountId"
				>
					<Route element={<ContactSalesPage />} index />
					<Route element={<ContactSalesForm />} path="form" />
				</Route>
			</Routes>
		</GetAppContextProvider>
	</HashRouter>
);

export default withProviders(GetAppRouter);
