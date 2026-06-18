/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const social = fragmentElement.querySelector('.footer-social');
	const socialIcons = social && social.querySelector('.social-icons');

	if (!social || !socialIcons) {
		return;
	}

	const navColumns = fragmentElement.querySelectorAll(
		'.footer-navigation .section-title'
	);

	const lastColumn = navColumns[navColumns.length - 1];

	if (lastColumn) {
		lastColumn.appendChild(socialIcons);

		social.remove();
	}
})();
