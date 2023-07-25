/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default {
	'com.liferay.lxc.dxp.domains': 'localhost:8080',
	'com.liferay.lxc.dxp.mainDomain': 'localhost:8080',
	'com.liferay.lxc.dxp.server.protocol': 'http',
	'configTreePaths': [
		process.env.DXP_METADATA_PATH,
		process.env.EXT_INIT_METADATA_PATH,
	],
	'liferay.oauth.application.external.reference.codes':
		'liferay-sample-node-oauth-application-user-agent',
	'readyPath': '/ready',
	'server.port': 3001,
};
