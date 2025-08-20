/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

let menu;
let trigger;

function initializeElements() {
	menu = fragmentElement.querySelector('.dropdown-menu-container');
	trigger = fragmentElement.querySelector('.dropdown-trigger');
}

function setupEventListeners() {
	trigger?.addEventListener('click', () => {
		menu.classList.toggle('active');
	});

	document.addEventListener('click', (event) => {
		if (!fragmentElement.contains(event.target)) {
			menu.classList.remove('active');
		}
	});
}

async function main() {
	initializeElements();

	setupEventListeners();
}

main();
