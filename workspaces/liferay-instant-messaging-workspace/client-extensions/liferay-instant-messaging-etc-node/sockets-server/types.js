/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const MessageType = {
	BOTENDTHINKING: 'BotEndThinking',
	BOTSTARTTHINKING: 'BotStartThinking',
	END_BOT_CONNECTION: 'EndBotConnection',
	IMCONTACTS: 'IMContacts',
	MASSAGE: 'Message',
	MESSAGE_NOTIFICATION: 'MessageNotification',
	MESSAGE_SEEN: 'MessageSeen',
	START_BOT_CONNECTION: 'StartBotConnection',
};

const ConnectionType = {
	IM: 'im',
};

module.exports = {
	ConnectionType,
	MessageType,
};
