/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../services/liferay';

export default function routerPath() {
	const relativeSiteURL = Liferay.ThemeDisplay.getLayoutRelativeURL();
	const lastIndexSlash = relativeSiteURL.lastIndexOf('/');

	let siteURL = '';

	if (lastIndexSlash > 0) {
		siteURL = `/${relativeSiteURL.substring(1, lastIndexSlash)}`;
	}

	return {
		home: () => `${Liferay.ThemeDisplay.getPortalURL()}${siteURL}`,
		onboarding: (externalReferenceCode: string) =>
			`${Liferay.ThemeDisplay.getPortalURL()}${siteURL}/onboarding/#/${externalReferenceCode}`,
		project: (externalReferenceCode: string) =>
			`${Liferay.ThemeDisplay.getPortalURL()}${siteURL}/project/#/${externalReferenceCode}`,
	};
}
