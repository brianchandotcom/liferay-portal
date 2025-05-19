/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {getChatBots} = require('../services/liferay-interface/chatbots');
const {getLiferayContacts} = require('../services/liferay-interface/contacts');
const {isOnline} = require('../sockets-server/websocketServer');
const {encrypt} = require('../util/encryption');

const getContactsWithStatus = async (pageSize = 0, page = 0, token = null) => {
	let contacts = await getLiferayContacts(pageSize, page, token);

	contacts = contacts.map((contact) => {
		return {
			...contact,
			online: isOnline(contact.userId),
		};
	});

	return contacts;
};

const getContactsStatus = async (pageSize, page, token = null, jwt) => {
	let contacts = await getLiferayContacts(pageSize, page, token);

	const chatBots = await getChatBots(0, 0, token);

	contacts = [...chatBots, ...contacts].map((contact) => {
		return {
			name: contact.name,
			online:
				contact.type.toString() === 'bot'
					? true
					: isOnline(contact.userId),
			self: jwt.sub.toString() === contact.userId.toString(),
			type: contact.type || 'person',
			userId: encrypt(contact.userId),
		};
	});

	return contacts;
};

module.exports = {
	getContactsStatus,
	getContactsWithStatus,
};
