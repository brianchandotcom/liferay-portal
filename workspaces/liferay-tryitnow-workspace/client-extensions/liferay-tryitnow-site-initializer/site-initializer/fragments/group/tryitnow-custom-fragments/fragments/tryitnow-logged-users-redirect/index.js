/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
const editMode = document.body.classList.contains('has-edit-mode-menu');
const signIn = Liferay.ThemeDisplay.isSignedIn();
const URLredirect = Liferay.ThemeDisplay.getURLHome();

if (!editMode && signIn) {
	window.location.href =
		'/web/' +
		(configuration.groupKey === 'test'
			? 'test-1'
			: configuration.groupKey) +
		'/home';
}
else if (editMode) {
	fragmentElement.querySelector('.url-div').classList.remove('d-none');
	fragmentElement.querySelector('.url-link').innerHTML = URLredirect;
}
