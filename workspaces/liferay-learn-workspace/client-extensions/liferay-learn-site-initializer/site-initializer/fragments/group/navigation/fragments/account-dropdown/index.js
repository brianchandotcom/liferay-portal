/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

document.addEventListener('click', (event) => {
	const dropdownContent = document.querySelector('.account-dropdown-content');
	const dropdownNav = document.querySelector('.account-dropdown-nav');

	const checkbox = dropdownNav.querySelector("input[type='checkbox']");

	if (
		!dropdownNav.contains(event.target) &&
		dropdownContent &&
		dropdownContent.style.display !== 'none'
	) {
		checkbox.checked = false;
	}
});
