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
		process.env.LIFERAY_ROUTES_CLIENT_EXTENSION,
		process.env.LIFERAY_ROUTES_DXP,
	],
	'liferay.oauth.application.external.reference.codes':
		'liferay-chat-etc-node-oauth-application-server,liferay-chat-etc-node-oauth-application-user-agent',
	'readyPath': '/ready',
	'chat.attachments.folder.external.reference.code':'liferay-chat-attachments-folder',
	'chat.services.main.address':'/chat/',
	'chat.services.endpoints':'/chat/contacts,/chat/me,/chat/messages',
	'self.initialization':process.env.Self_Initialization,
	'server.port': 8050,
};
