/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

if (!themeDisplay.isSignedIn()) {
	const signInLink = document.createElement('a');

	signInLink.setAttribute('href', '/c/portal/login');
	signInLink.textContent = 'Sign In';

	const signInMessage = document.createElement('span');

	signInMessage.textContent = 'to save your progress';

	const signInContainer = document.querySelector('.signin-container');

	signInContainer.appendChild(signInLink);
	signInContainer.appendChild(signInMessage);
}
