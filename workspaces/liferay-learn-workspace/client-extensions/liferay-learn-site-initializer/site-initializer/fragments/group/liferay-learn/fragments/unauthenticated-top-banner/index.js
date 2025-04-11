/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

if (!themeDisplay.isSignedIn()) {
	const publicSiteNavigationContainer = document.querySelector(
		'.public-site-navigation-container'
	);

	publicSiteNavigationContainer.classList.add(
		'public-site-navigation-margin-top'
	);

	document.addEventListener('DOMContentLoaded', () => {
		document.querySelector('.icon-x').addEventListener('click', () => {
			document.querySelector('.banner-sign-in').style.display = 'none';

			publicSiteNavigationContainer.classList.remove(
				'public-site-navigation-margin-top'
			);
		});
	});
}
