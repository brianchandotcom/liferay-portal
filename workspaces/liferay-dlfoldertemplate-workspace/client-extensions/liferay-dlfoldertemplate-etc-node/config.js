/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default {
	'com.liferay.sh.dxp.domains': '127.0.0.1:8080',
	'com.liferay.sh.dxp.mainDomain': '127.0.0.1:8080',
	'com.liferay.sh.dxp.server.protocol': 'http',
	'configTreePaths': [
		'process.env.LIFERAY_ROUTES_CLIENT_EXTENSION',
		'process.env.LIFERAY_ROUTES_DXP',
	],
	'employee.folder.external.reference.code': 'HR',
	'employee.folder.site.id': '20118',
	'jobs.attachments.folder.external.reference.code':
		'liferay-jobs-attachments-folder',
	'liferay.oauth.application.external.reference.codes':
		'liferay-dlfoldertemplate-oauth-application-server,liferay-dlfoldertemplate-oauth-application-user-agent',
	'liferay-dlfoldertemplate-oauth-application-server.sh.oauth2.headless.server.client.id':
		'id-48cba9c9-7cf8-f55d-f884-9f162496b2c',
	'liferay-dlfoldertemplate-oauth-application-server.sh.oauth2.headless.server.client.secret':
		'secret-c72013da-2923-6418-9b82-9c5967ea832',
	'liferay-dlfoldertemplate-oauth-application-user-agent.sh.oauth2.headless.agent.client.id':
		'id-2a98188a-9ca2-39ad-a714-2850efd344f0',
	'project-id': 'liferay-dlfoldertemplate',
	'readyPath': '/ready',
	'self.initialization': 'process.env.Self_Initialization',
	'server.port': 8050,
	'services.endpoints': '',
	'services.main.address': '/jobs',
	'templateId': '59455',
};
