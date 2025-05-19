/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {lxcConfig} = require('@rotty3000/config-node');
const axios = require('axios');

const lxcDXPMainDomain = lxcConfig.dxpMainDomain();
const lxcDXPServerProtocol = lxcConfig.dxpProtocol();
const oauth2JWKSURI = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}`;

function lrRequest(config, token) {
	const _config = {
		data: config.data,
		headers: {
			'Accept': 'application/json',
			'Authorization': `Bearer ${token}`,
			'Content-Type': 'application/json',
			...config.headers,
		},
		method: config.method,
		url: `${oauth2JWKSURI}${config.url}`,
	};

	return axios.request(_config);
}

module.exports = {
	lrRequest,
};
