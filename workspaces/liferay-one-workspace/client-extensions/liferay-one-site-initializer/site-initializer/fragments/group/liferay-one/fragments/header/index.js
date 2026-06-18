/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

async function getAccounts(searchParams = new URLSearchParams()) {
	try {
		const response = await Liferay.Util.fetch(
			`/o/headless-admin-user/v1.0/accounts?${searchParams.toString()}`
		);

		return response.json();
	}
	catch (error) {
		console.error('Failed to get accounts:', error);

		return {items: []};
	}
}

async function applyMyAccountVisibility() {
	try {
		const nav = fragmentElement.querySelector('.adt-navigation');

		if (nav && nav.dataset.accountBypass === 'true') {
			return;
		}

		const response = await getAccounts(
			new URLSearchParams({
				page: 1,
				pageSize: 1,
			})
		);

		if ((response?.items?.length || 0) > 0) {
			return;
		}

		fragmentElement
			.querySelectorAll('.adt-nav-item.dropdown')
			.forEach((navItem) => {
				const title = navItem.querySelector('.adt-nav-title');

				if (title && title.textContent.trim() === 'My Account') {
					navItem.style.display = 'none';
				}
			});
	}
	catch (error) {
		console.error('Failed to apply My Account visibility:', error);
	}
}

function closeMegaMenuOnLinkClick() {
	const nav = fragmentElement.querySelector('.adt-navigation');

	if (!nav) {
		return;
	}

	nav.addEventListener('click', (event) => {
		const link = event.target.closest('.adt-submenu-item-link');

		if (!link) {
			return;
		}

		const navItem = link.closest('.adt-nav-item.dropdown');

		if (!navItem || !navItem.classList.contains('show')) {
			return;
		}

		navItem.classList.remove('show');

		const toggle = navItem.querySelector(
			'[data-toggle="liferay-dropdown"]'
		);

		if (toggle) {
			toggle.setAttribute('aria-expanded', 'false');
		}
	});
}

function setProjectLink() {
	const projectLabel = fragmentElement.querySelector(
		'[data-nav-name="Project"]'
	);

	const projectLink = projectLabel && projectLabel.closest('a');

	const projectId = localStorage.getItem('liferay-one:last-project');

	if (!projectLink || !projectId) {
		return;
	}

	const base = projectLink.href.split('#')[0];

	projectLink.href = base + '#/projects/detail/' + projectId;
}

closeMegaMenuOnLinkClick();
setProjectLink();
applyMyAccountVisibility();
