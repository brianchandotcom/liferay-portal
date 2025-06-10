/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
/* global Liferay */


export const config = {

	agentOauthAppId: 'liferay-stream-hub-etc-spring-boot-oauth-application-user-agent',

	objectApiEndPoint: ``,

};

export const MessageType={

	MASSAGE:"Message",
	EVENT:"Event",
	IMCONTACTS:'IMContacts',
	MESSAGE_NOTIFICATION:"MessageNotification",
	MESSAGE_SEEN:"MessageSeen",

}

export const ChatMessageType={
	TEXT:'Text',
	FILE: 'File'
}

export const ActionsTypes = {

	ON_AFTER_ADD : "onAfterAdd",
	ON_AFTER_REMOVE : "onAfterDelete",
	ON_AFTER_UPDATE : "onAfterUpdate",
	STAND_ALONE:"standalone",

}
