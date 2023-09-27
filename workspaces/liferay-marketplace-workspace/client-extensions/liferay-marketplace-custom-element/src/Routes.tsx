/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from './liferay/liferay';
import {AppCreationFlow} from './pages/AppCreationFlow/AppCreationFlow';
import {CustomerGatePage} from './pages/CustomerGatePage/CustomerGatePage';
import GetAppPage from './pages/GetAppPage/GetAppPage';
import {NextStepPage} from './pages/NextStepPage/NextStepPage';
import {PublishedAppsDashboardPage} from './pages/PublishedAppsDashboardPage/PublishedAppsDashboardPage';
import {PurchasedAppsDashboardPage} from './pages/PurchasedAppsDashboardPage/PurchasedAppsDashboardPage';
import PurchasedSolutions from './pages/PurchasedSolutions/PurchasedSolutions';

interface AppRoutesProps {
	route: string;
}

export default function AppRoutes({route}: AppRoutesProps) {
	if (Liferay.ThemeDisplay.isSignedIn()) {
		if (route === 'create-app') {
			return <AppCreationFlow />;
		}
		else if (route === 'get-app') {
			return <GetAppPage />;
		}
		else if (route === 'next-steps') {
			return <NextStepPage />;
		}
		else if (route === 'purchased-apps') {
			return <PurchasedAppsDashboardPage />;
		}
		else if (route === 'published-apps') {
			return <PublishedAppsDashboardPage />;
		}
		else if (route === 'customer-gate') {
			return <CustomerGatePage />;
		}
		else if (route === 'purchased-solutions') {
			return <PurchasedSolutions />;
		}
	}

	return <></>;
}
