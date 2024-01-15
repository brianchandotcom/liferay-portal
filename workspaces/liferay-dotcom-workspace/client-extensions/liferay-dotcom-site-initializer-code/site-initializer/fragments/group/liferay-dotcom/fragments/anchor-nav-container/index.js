/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
document.addEventListener('DOMContentLoaded', () => {
	const anchors = document.querySelectorAll('.f-anchor-target');

	const anchorNavLinks = document.querySelectorAll('.anchor-nav-link');

	window.onscroll = function () {
		let current = '';

		anchors.forEach((anchor) => {
			const sectionTop = anchor.offsetTop;

			const windowHeight = window.innerHeight;

			if (scrollY >= sectionTop - windowHeight * 0.33) {
				current = anchor.getAttribute('id');
			}
		});

		anchorNavLinks.forEach((anchorNavLink) => {
			anchorNavLink.classList.remove('active');
			if (
				anchorNavLink.classList.contains('anchor-nav-link-#' + current)
			) {
				anchorNavLink.classList.add('active');
			}
		});
	};
});
