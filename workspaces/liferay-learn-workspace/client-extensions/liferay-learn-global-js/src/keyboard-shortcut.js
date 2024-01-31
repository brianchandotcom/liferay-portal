/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Add Keyboard shortcut "/" to activate search bar for
 * quick search, search focus depends on which page user is
 * (homepage, search, article)
 */

window.addEventListener('keyup', (e) => {
	if (e.key === '/') {
		document.getElementsByClassName('input-group-inset-after').length
			? document
					.getElementsByClassName('input-group-inset-after')[0]
					.focus()
			: document
					.getElementsByClassName('search-wrapper')[0]
					.classList.contains('search-open')
			? document.getElementById('searchInput').focus()
			: document.getElementById('searchIcon').click();
	}
});
