/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CustomerGatePage} from './pages/CustomerGatePage/CustomerGatePage';
import GetAppPage from './pages/GetAppPage/GetAppPage';
import {NextSteps} from './pages/NextSteps';
import {AppCreationFlow} from './pages/PublishedAppsDashboard/Apps/AppCreationFlow/AppCreationFlow';
import PublishedAppsDashboardRouter from './pages/PublishedAppsDashboard/PublishedAppsDashboardRouter';
import PublisherGateRouter from './pages/PublisherGate/PublisheGateRouter';
import PurchasedAppsDashboardRouter from './pages/PurchasedAppsDashboard/PurchasedAppsDashboardRouter';
import PurchasedSolutions from './pages/PurchasedSolutions/PurchasedSolutions';

const Routes = {
	'create-app': AppCreationFlow,
	'customer-gate': CustomerGatePage,
	'get-app': GetAppPage,
	'next-steps': NextSteps,
	'published-apps': PublishedAppsDashboardRouter,
	'publisher-gate': PublisherGateRouter,
	'purchased-apps': PurchasedAppsDashboardRouter,
	'purchased-solutions': PurchasedSolutions,
} as const;

export type RouteType = keyof typeof Routes;

type AppRoutesProps = {
	route: RouteType;
};

export default function AppRoutes({route}: AppRoutesProps) {
	const Route = Routes[route];

	if (!Route) {
		return <h1>Page not found</h1>;
	}

	return <Route />;
}
