/* global Liferay */


export const config = {

	agentOauthAppId: 'liferay-instant-messaging-etc-node-oauth-application-user-agent',
	chatBotEndPoint: `/o/c/chatbots/`,
	objectApiEndPoint: ``,
	objectDefinitionEndPoint: `/ui/object/definition/list`,
};

export const MessageType={
	BOTENDTHINKING:'BotEndThinking',
	BOTSTARTTHINKING:'BotStartThinking',
	END_BOT_CONNECTION:"EndBotConnection",
	IMCONTACTS:'IMContacts',
	MASSAGE:"Message",
	MESSAGE_NOTIFICATION:"MessageNotification",
	MESSAGE_SEEN:"MessageSeen",
	START_BOT_CONNECTION:"StartBotConnection",
}

export const ChatMessageType={
	FILE: 'File',
	TEXT:'Text',
}

export const ActionsTypes = {
	ON_AFTER_ADD : "onAfterAdd",
	ON_AFTER_REMOVE : "onAfterDelete",
	ON_AFTER_UPDATE : "onAfterUpdate",
	STAND_ALONE:"standalone",
}
