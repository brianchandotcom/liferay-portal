/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import SecurityVulnerabilitiesItem from '../SecurityVulnerabilitiesItem';
import SecurityVulnerabilitiesList from '../SecurityVulnerabilitiesList';

const SecurityVulnerabilitiesRoutes = () => {
	return (
		<HashRouter>
			<Routes>
				<Route element={<SecurityVulnerabilitiesList />} path="/" />
				<Route element={<SecurityVulnerabilitiesItem />} path="/:id" />
			</Routes>
		</HashRouter>
	);
};

export default SecurityVulnerabilitiesRoutes;
