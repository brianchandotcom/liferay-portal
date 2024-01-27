/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import GetAppPage from './GetAppPage';
import {InsuficientResources} from './InsuficientResources';
import ContactSalesForm from './InsuficientResources/ContactSalesForm';
import ContactSalesPage from './InsuficientResources/ContactSalesPage';

const GetAppRouter = () => {
	return (
		<HashRouter>
			<Routes>
				<Route element={<GetAppPage />} index />
				<Route
					element={<InsuficientResources />}
					path="insuficient-resources/:projectId/:accountId"
				>
					<Route element={<ContactSalesPage />} index />
					<Route element={<ContactSalesForm />} path="form" />
				</Route>
			</Routes>
		</HashRouter>
	);
};

export default GetAppRouter;
