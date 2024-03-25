/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DashboardListItems} from '../../components/DashboardNavigation/DashboardNavigation';
import {AppProps} from '../../components/DashboardTable/DashboardTable';
import i18n from '../../i18n';

export const initialAdministratorDashboardNavigationItems: DashboardListItems[] =
	[
		{
			itemIcon: 'polls',
			itemName: 'metrics',
			itemSelected: true,
			itemTitle: i18n.translate('dashboard'),
			items: [] as AppProps[],
			path: '/',
		},
		{
			itemIcon: 'grid',
			itemName: 'account',
			itemSelected: false,
			itemTitle: i18n.translate('apps'),
			path: '/apps-administrator',
		},
		{
			itemIcon: 'envelope-closed',
			itemName: 'solutions',
			itemSelected: false,
			itemTitle: i18n.translate('publisher-requests'),
			path: '/publisher-request',
		},
	];
