/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {Router} = require('express');

const {getContactsStatus} = require('../../interfaces/contacts');
const LRMessages = require('../../services/liferay-interface/messages');
const {decrypt} = require('../../util/encryption');
const {ChatMessageType} = require('../../util/types');

const router = Router();

router.get('/contacts', async (req, res) => {
	res.send(await getContactsStatus(0, 0, null, req.jwt));
});

router.get('/chatlog/:from/:to', async (req, res) => {
	const {from, to} = req.params;

	const chatLog = await LRMessages.get(decrypt(from), decrypt(to));

	const decryptedMessageLog = [];

	for (let logIndex = 0; logIndex < chatLog.data.items.length; logIndex++) {
		const messageObj = JSON.parse(chatLog.data.items[logIndex].message);

		if (messageObj.chatMessageType === ChatMessageType.TEXT) {
			messageObj.message = decrypt(messageObj.message);
			decryptedMessageLog.push(messageObj);
		}

		if (messageObj.chatMessageType === ChatMessageType.FILE) {
			messageObj.file = {
				fileName: decrypt(messageObj.file.fileName),
				fileUrl: decrypt(messageObj.file.fileUrl),
				preview: decrypt(messageObj.file.preview),
			};

			decryptedMessageLog.push(messageObj);
		}
	}

	res.send(decryptedMessageLog);
});

module.exports = router;
