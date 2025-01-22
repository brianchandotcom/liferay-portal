/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
const containerClass = '.navigation-container';

document.addEventListener('DOMContentLoaded', () => {
	const container = document.querySelector(containerClass);

	const checkboxes = container.querySelectorAll(
		'input[type="checkbox"]:not(.nav-items-menu-button-input)'
	);

	document.addEventListener('click', (event) => {
		if (!container.contains(event.target)) {
			for (const checkbox of checkboxes) {
				checkbox.checked = false;
			}
		}
	});

	document.addEventListener('keydown', (event) => {
		if (event.keyCode === 27) {
			for (const checkbox of checkboxes) {
				checkbox.checked = false;
			}
		}
	});

	for (const checkbox of checkboxes) {
		checkbox.addEventListener('click', function () {
			const otherCheckboxes = container.querySelectorAll(
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
