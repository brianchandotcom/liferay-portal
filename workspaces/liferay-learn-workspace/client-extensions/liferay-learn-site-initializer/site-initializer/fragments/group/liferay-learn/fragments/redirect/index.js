/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
const mode = getParameterByName('p_l_mode');

if (!mode || mode !== 'edit') {
	window.location.href = configuration.redirectLocation;
}

function getParameterByName(name, url = window.location.href) {
	name = name.replace(/[[\]]/g, '\\$&');

	const regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
	const results = regex.exec(url);

	if (!results) {
		return null;
	}
	if (!results[2]) {
		return '';
	}

	return decodeURIComponent(results[2].replace(/\+/g, ' '));
}
