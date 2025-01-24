/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
document.addEventListener('DOMContentLoaded', () => {
	const navigationContainer = document.querySelector('.navigation-container');

	const checkboxes = navigationContainer.querySelectorAll(
		'input[type="checkbox"]:not(.nav-items-menu-button-input)'
	);

	document.addEventListener('click', (event) => {
		if (!navigationContainer.contains(event.target)) {
			for (const checkbox of checkboxes) {
				checkbox.checked = false;
			}
		}
	});

	document.addEventListener('keydown', (event) => {
		if (event.key === 'Escape') {
			for (const checkbox of checkboxes) {
				checkbox.checked = false;
			}
		}
	});

	for (const checkbox of checkboxes) {
		checkbox.addEventListener('click', function () {
			const otherCheckboxes = navigationContainer.querySelectorAll(
				'input[type="checkbox"]:not(.nav-items-menu-button-input)'
			);

			for (const otherCheckbox of otherCheckboxes) {
				if (otherCheckbox !== this) {
					otherCheckbox.checked = false;
				}
			}
		});
	}
});
