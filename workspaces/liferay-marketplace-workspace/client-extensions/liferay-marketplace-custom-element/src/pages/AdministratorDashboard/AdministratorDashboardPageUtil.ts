/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DashboardListItems} from '../../components/DashboardNavigation/DashboardNavigation';
import i18n from '../../i18n';

export const initialAdministratorDashboardNavigationItems: DashboardListItems[] = [
	{
		itemTitle: i18n.translate('dashboard'),
		items: [],
		path: '/',
		symbol: 'polls',
	},
	{
		itemTitle: i18n.translate('apps'),
		path: '/apps',
		symbol: 'grid',
	},
	{
		itemTitle: i18n.translate('trials'),
		path: '/trial',
		symbol: 'squares-clock',
	},
	{
		itemTitle: i18n.translate('publisher-requests'),
		path: '/publisher-request',
		symbol: 'envelope-closed',
	},
];
