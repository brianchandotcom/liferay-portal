/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function main() {
	if (fragmentElement.querySelector('.one-dropdown.one-dropdown-edit')) {
		return;
	}

	const content = fragmentElement.querySelector('.one-dropdown-content');
	const trigger = fragmentElement.querySelector('.one-dropdown-trigger');

	trigger?.addEventListener('click', () => {
		content.classList.toggle('active');
	});

	document.addEventListener('click', (event) => {
		if (!fragmentElement.contains(event.target)) {
			content.classList.remove('active');
		}
	});
}

main();
