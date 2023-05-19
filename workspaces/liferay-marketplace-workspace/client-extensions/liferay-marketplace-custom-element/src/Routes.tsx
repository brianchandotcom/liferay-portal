/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {Liferay} from './liferay/liferay';
import {AppCreationFlow} from './pages/AppCreationFlow/AppCreationFlow';
import GetAppPage from './pages/GetAppPage/GetAppPage';
import {NextStepPage} from './pages/NextStepPage/NextStepPage';
import {PublishedAppsDashboardPage} from './pages/PublishedAppsDashboardPage/PublishedAppsDashboardPage';
import {PurchasedAppsDashboardPage} from './pages/PurchasedAppsDashboardPage/PurchasedAppsDashboardPage';

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
	}

	return <></>;
}
