/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AdministratorDashboardRouter from './pages/AdministratorDashboard/AdministratorDashboardRouter';
import CustomerDashboardRouter from './pages/CustomerDashboard/CustomerDashboardRouter';
import {CustomerGatePage} from './pages/CustomerGatePage/CustomerGatePage';
import GetAppRouter from './pages/GetApp/GetAppRouter';
import GetSolutionRouter from './pages/GetSolution/GetSolutionRouter';
import {NextSteps} from './pages/NextSteps';
import PublisherDashboardRouter from './pages/PublisherDashboard/PublisherDashboardRouter';
import PublisherGateRouter from './pages/PublisherGate/PublisheGateRouter';

const Routes = {
	'administrator-dashboard': AdministratorDashboardRouter,
	'customer-gate': CustomerGatePage,
	'get-app': GetAppRouter,
	'next-steps': NextSteps,
	'published-apps': PublisherDashboardRouter,
	'publisher-gate': PublisherGateRouter,
	'purchased-apps': CustomerDashboardRouter,
	'purchased-solutions': GetSolutionRouter,
} as const;

export type RouteType = keyof typeof Routes;

type AppRoutesProps = {
	path: RouteType;
};

export default function AppRoutes({path}: AppRoutesProps) {
	const Route = Routes[path];

	if (!Route) {
		return <h1>Page not found</h1>;
	}

	return <Route />;
}
