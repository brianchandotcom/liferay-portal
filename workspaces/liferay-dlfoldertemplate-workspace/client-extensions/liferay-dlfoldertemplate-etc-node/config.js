/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
export default {
	'configTreePaths': [
		process.env.LIFERAY_ROUTES_CLIENT_EXTENSION ,
		process.env.LIFERAY_ROUTES_DXP,
	],
	'liferay.oauth.application.external.reference.codes':
		'liferay-dlfoldertemplate-oauth-application-server,liferay-dlfoldertemplate-oauth-application-user-agent',
	'readyPath': '/ready',
	'jobs.attachments.folder.external.reference.code':'liferay-jobs-attachments-folder',
	'employee.folder.external.reference.code':'HR',
	'employee.folder.site.id':'20118',
	'services.main.address':'/jobs',
	'services.endpoints':'',
	'com.liferay.sh.dxp.domains':'127.0.0.1:8080',
	'com.liferay.sh.dxp.server.protocol':'http',
	'com.liferay.sh.dxp.mainDomain':'127.0.0.1:8080',
	'project-id':'liferay-dlfoldertemplate',
	'liferay-dlfoldertemplate-oauth-application-server.sh.oauth2.headless.server.client.id':'id-7c8c5e44-c983-44b6-2bf6-98a54d73763d',
	'liferay-dlfoldertemplate-oauth-application-server.sh.oauth2.headless.server.client.secret':'secret-c86a8b50-fd47-b17e-b07b-23ab7a7b42',
	'liferay-dlfoldertemplate-oauth-application-user-agent.sh.oauth2.headless.agent.client.id':'id-bbc0c1e4-52c9-f5d4-4f72-73157739e7f',
	'self.initialization':process.env.Self_Initialization,
	'templateId':'59455',
	'server.port': 8050,
};
