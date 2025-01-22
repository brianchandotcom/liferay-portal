/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
if (!themeDisplay.isSignedIn()) {
	document.addEventListener('DOMContentLoaded', () => {
		const bannerSignIn = document.querySelector('.banner-sign-in-lesson');
		const iconX = document.querySelector('.icon-x-lesson');

		iconX.addEventListener('click', () => {
			bannerSignIn.style.display = 'none';
		});
	});
}
