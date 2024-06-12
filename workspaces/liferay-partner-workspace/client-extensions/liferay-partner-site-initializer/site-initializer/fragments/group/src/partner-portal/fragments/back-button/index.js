/* eslint-disable no-undef */

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const backButton = document.getElementById('back-button-detail-page');

if (backButton) {
	const siteURL = Liferay.ThemeDisplay.getPortalURL().split('/l/')[0];

	backButton.onclick = () => {
		const urlParams = new URLSearchParams(window.location.search);

		if (urlParams.has('p_l_back_url')) {
			const backURLs = urlParams.getAll('p_l_back_url');
			const backURL = backURLs.pop();

			urlParams.delete('p_l_back_url', backURL);

			location.assign(
				`${siteURL}${decodeURIComponent(
					`${backURL}?${urlParams.toString()}`
				)}`
			);
		}
		else {
			location.assign(decodeURIComponent(configuration.backURL));
		}
	};
}
