/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
const cookieName = 'OSB_WWW_NOTIFICATION_' + fragmentNamespace;
const body = document.body;

if (body && body.classList.contains('has-edit-mode-menu')) {
	body.classList.add('has-alert-container');
}

window.addEventListener('DOMContentLoaded', () => {
	const closeButton = fragmentElement.querySelector('#closeButton');

	if (!(document.cookie.indexOf(cookieName) >= 0)) {
		if (body) {
			body.classList.add('has-alert-container');
		}

		const path = themeDisplay.getPathContext() || '/';

		closeButton.addEventListener('click', () => {
			if (document.cookie.length) {
				body.classList.remove('has-alert-container');
				document.cookie = `${cookieName}=true;path=${path};max-age=604800;`;
			}
			else {
				document.cookie = `${cookieName}=true;path=${path};`;
			}
		});
	}
});
