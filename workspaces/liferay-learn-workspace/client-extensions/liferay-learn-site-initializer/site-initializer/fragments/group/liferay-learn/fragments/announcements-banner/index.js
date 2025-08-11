/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const publicSiteNavigationContainer = document.querySelector(
	'.public-site-navigation-container'
);

const announcementsBanner = document.querySelector('.announcements-banner');
const BANNER_CLOSED_SESSION_KEY = 'bannerWasClosed';
const isInEditMode = document.body.classList.contains('has-edit-mode-menu');

if (isInEditMode) {
	announcementsBanner.style.display = 'flex';
}

document.addEventListener('DOMContentLoaded', () => {
	const userClosedBanner = sessionStorage.getItem(BANNER_CLOSED_SESSION_KEY);

	if (userClosedBanner === 'true') {
		announcementsBanner.style.display = 'none';
		publicSiteNavigationContainer.classList.remove(
			'navigation-margin-true'
		);

		return;
	}

	if (announcementsBanner) {
		if (themeDisplay.isSignedIn()) {
			announcementsBanner.style.top = '56px';
			publicSiteNavigationContainer.classList.add(
				'navigation-margin-true'
			);
		}
		else {
			announcementsBanner.style.top = '0px';
			publicSiteNavigationContainer.classList.add(
				'navigation-margin-true'
			);
		}
	}

	announcementsBanner.style.display = 'flex';

	document.querySelector('.icon-x').addEventListener('click', () => {
		document.querySelector('.announcements-banner').style.display = 'none';
		publicSiteNavigationContainer.classList.remove(
			'navigation-margin-true'
		);

		sessionStorage.setItem(BANNER_CLOSED_SESSION_KEY, 'true');
	});
});
