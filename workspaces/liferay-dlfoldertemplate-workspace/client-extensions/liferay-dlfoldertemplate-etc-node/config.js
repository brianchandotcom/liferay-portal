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

	'liferay.oauth.application.external.reference.codes': 'liferay-dlfoldertemplate-oauth-application-server,liferay-dlfoldertemplate-oauth-application-user-agent',

	'liferay.dlfoldertemplate.oauth.application.server.oauth2.headless.server.client.id': 'id-a11aa351-254b-7f6e-a788-f437ace5e625',

	'liferay.dlfoldertemplate.oauth.application.server.oauth2.headless.server.client.secret': 'secret-2073dc13-fb7f-dc48-591a-d387e69ec5',

	'liferay.dlfoldertemplate.oauth.application.user.agent.oauth2.user.agent.client.id': 'id-ac5f8b38-50ae-8629-27df-37c198a1d551',

	'liferay.dlfoldertemplate.oauth.application.user.agent.oauth2.jwks.uri': '/o/oauth2/jwks',

	'liferay.dlfoldertemplate.oauth.application.server.oauth2.token.uri': '/o/oauth2/token',

	'folder.template.nodes.end.point': '/o/c/t4t14foldertemplatenodes/',

	'ready.path': '/ready',

	'server.port': 8050,

};
