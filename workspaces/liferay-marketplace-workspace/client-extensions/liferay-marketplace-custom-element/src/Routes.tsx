/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from './liferay/liferay';
import {AppCreationFlow} from './pages/AppCreationFlow/AppCreationFlow';
import {CustomerGatePage} from './pages/CustomerGatePage/CustomerGatePage';
import GetAppPage from './pages/GetAppPage/GetAppPage';
import {NextStepPage} from './pages/NextStepPage/NextStepPage';
import PublishedAppsDashboardRouter from './pages/PublishedAppsDashboard/PublishedAppsDashboardRouter';
import PurchasedAppsDashboardRouter from './pages/PurchasedAppsDashboard/PurchasedAppsDashboardRouter';
import PurchasedSolutions from './pages/PurchasedSolutions/PurchasedSolutions';

const Routes = {
	'create-app': AppCreationFlow,
	'customer-gate': CustomerGatePage,
	'get-app': GetAppPage,
	'next-steps': NextStepPage,
	'published-apps': PublishedAppsDashboardRouter,
	'purchased-apps': PurchasedAppsDashboardRouter,
	'purchased-solutions': PurchasedSolutions,
} as const;

export type RouteType = keyof typeof Routes;

type AppRoutesProps = {
	route: RouteType;
};

export default function AppRoutes({route}: AppRoutesProps) {
	const Route = Routes[route];

	if (!Liferay.ThemeDisplay.isSignedIn() || !Route) {
		return null;
	}

	return <Route />;
}
