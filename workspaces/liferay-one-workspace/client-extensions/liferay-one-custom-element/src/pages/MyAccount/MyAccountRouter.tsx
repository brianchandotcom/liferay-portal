/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, useRoutes} from 'react-router-dom';

import {toRouteObjects} from '../../utils/routes';
import MyAccountLayout from './MyAccountLayout';
import {myAccountRoutes} from './myAccountRoutes';

function MyAccountRoutes() {
	return useRoutes([
		{
			children: toRouteObjects(myAccountRoutes),
			element: <MyAccountLayout />,
			path: '/',
		},
	]);
}

export default function MyAccountRouter() {
	return (
		<HashRouter>
			<MyAccountRoutes />
		</HashRouter>
	);
}
