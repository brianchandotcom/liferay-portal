/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

if (!themeDisplay.isSignedIn()) {
	document.querySelector('.public-site-navigation-container').style.marginTop = '50px';
	document.querySelector('.public-site-navigation-container').style.top = '50px';

	document.addEventListener('DOMContentLoaded', () => {
		document.querySelector('.icon-x').addEventListener('click', () => {
			document.querySelector('.banner-sign-in').style.display = 'none';
			document.querySelector('.public-site-navigation-container').style.marginTop = '0';
			document.querySelector('.public-site-navigation-container').style.top = '0';

			const navigationContainer = document.querySelector(
				'.navigation-container'
			);

			navigationContainer.style.marginTop = '0';
			navigationContainer.style.position = 'inherit';
		});
	});
}