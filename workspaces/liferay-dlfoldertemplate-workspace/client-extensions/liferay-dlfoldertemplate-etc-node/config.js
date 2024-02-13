/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default {
	'com.liferay.lxc.dxp.domains': '127.0.0.1:8080',
	'com.liferay.lxc.dxp.main.domain': '127.0.0.1:8080',
	'com.liferay.lxc.dxp.server.protocol': 'http',
	'configTreePaths': [
		'process.env.LIFERAY_ROUTES_CLIENT_EXTENSION',
		'process.env.LIFERAY_ROUTES_DXP',
	],
	'folder.template.nodes.end.point': '/o/c/t4t14foldertemplatenodes/',
	'liferay.dlfoldertemplate.oauth.application.server.oauth2.headless.server.client.id':
		'id-6455f1f2-f15c-07b1-cbc8-3e267524546',
	'liferay.dlfoldertemplate.oauth.application.server.oauth2.headless.server.client.secret':
		'secret-e585633a-07c8-13dd-b654-86adead8e6',
	'liferay.dlfoldertemplate.oauth.application.server.oauth2.token.uri':
		'/o/oauth2/token',
	'liferay.dlfoldertemplate.oauth.application.user.agent.oauth2.jwks.uri':
		'/o/oauth2/jwks',
	'liferay.dlfoldertemplate.oauth.application.user.agent.oauth2.user.agent.client.id':
		'id-f39269e7-1412-ea1c-30b2-8da323478c',
	'liferay.oauth.application.external.reference.codes':
		'liferay-dlfoldertemplate-oauth-application-server,liferay-dlfoldertemplate-oauth-application-user-agent',
	'ready.path': '/ready',
	'server.port': 8050,
};
