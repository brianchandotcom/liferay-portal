/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {parse} = require('url'); // eslint-disable-line
const {v4: uuidv4} = require('uuid');
const WebSocket = require('ws');

const {conversation} = require('../services/ai/conversation');
const contextService = require('../services/contexts/context');
const LRMessages = require('../services/liferay-interface/messages');
const {decrypt, encrypt} = require('../util/encryption');
const {
	clientLiferayJWTValidation,
} = require('../util/liferay-oauth2-resource-server');
const {logError, logInfo} = require('../util/log');
const {ChatMessageType} = require('../util/types');
const db = require('./pending-events/pending-events');
const {ConnectionType, MessageType} = require('./types');
const {buildMessageEnvelope} = require('./utils');
const clients = new Map();

const isOnline = (userId) => {
	if (clients) {
		return clients.has(userId.toString());
	}
	else {
		return false;
	}
};

const broadcastContactsUpdate = () => {
	const connectedImContactsId = [];
	const IMConnections = [];

	clients.keys().forEach((client) => {
		const imConnections = clients
			.get(client)
			.filter(
				(connection) =>
					connection.type === ConnectionType.IM &&
					connection.OPEN === 1
			);

		if (imConnections && !!imConnections.length) {
			connectedImContactsId.push(encrypt(imConnections[0].userId));
		}
	});

	const message = buildMessageEnvelope(
		MessageType.IMCONTACTS,
		'connectionsUpdate',
		connectedImContactsId
	);

	clients.keys().forEach((client) => {
		const imConnections = clients
			.get(client)
			.filter(
				(connection) =>
					connection.type === ConnectionType.IM &&
					connection.OPEN === 1
			);

		if (imConnections && !!imConnections.length) {
			imConnections.forEach((connection) => {
				connection.send(JSON.stringify(message));
			});
		}
	});

	IMConnections.forEach((connection) => {
		if (connection.OPEN === 1) {
			connection.send(JSON.stringify(message));
		}
	});
};

const handleMissingMessageNotifications = async (clientId) => {
	let pendingNotifications = await getPendingEvents({
		clientId,
		type: MessageType.MESSAGE_NOTIFICATION,
	});

	const encryptedClientId = encrypt(clientId);

	pendingNotifications = pendingNotifications.filter(
		(notification) =>
			notification.data.from.toString() !== encryptedClientId.toString()
	);

	const countByFrom = pendingNotifications.reduce((acc, message) => {
		const from = message.data.from;
		acc[from] = (acc[from] || 0) + 1;

		return acc;
	}, {});

	sendMessage(
		MessageType.MESSAGE_NOTIFICATION,
		MessageType.MESSAGE_NOTIFICATION,
		countByFrom,
		[clientId],
		false
	);
};

const initializeWebSocket = (httpServer) => {
	const wss = new WebSocket.Server({
		path: '/server',
		server: httpServer,
	});

	wss.on('connection', async (ws, req) => {
		const token = req.headers['sec-websocket-protocol'];

		const parameters = parse(req.url, true).query;

		const decodedToken = await clientLiferayJWTValidation(token);

		if (!decodedToken || decodedToken.username === 'default@liferay.com') {
			logError('Invalid token, closing connection.');

			ws.close(1008, 'Invalid token');

			return null;
		}

		logInfo(
			`New WebSocket connection established for (User Id: ${decodedToken.sub}, Email: ${decodedToken.username}).`
		);

		ws['userId'] = decodedToken.sub;

		ws['uuid'] = uuidv4();

		if ('type' in parameters && parameters['type'] === ConnectionType.IM) {
			ws['type'] = ConnectionType.IM;
		}
		else {
			ws.close(1008, 'Invalid connection type.');

			return null;
		}

		if (clients.has(decodedToken.sub)) {
			clients.set(decodedToken.sub, [
				ws,
				...clients.get(decodedToken.sub),
			]);
		}
		else {
			clients.set(decodedToken.sub, [ws]);
		}

		if (ws.type === ConnectionType.IM) {
			broadcastContactsUpdate();
			await handleMissingMessageNotifications(decodedToken.sub);
		}

		ws.on('message', async (message) => {
			await handleOnMessage(ws, message);
		});

		ws.on('close', async () => {
			clients.set(
				ws['userId'],
				clients
					.get(ws['userId'])
					.filter((client) => client.uuid !== ws.uuid)
			);

			if (ws.type === ConnectionType.IM) {
				broadcastContactsUpdate();
			}
		});

		// Handle errors

		ws.on('error', (error) => {
			logError('WebSocket error:', error);
		});
	});

	return wss;
};

function advancedCleanText(text) {
	return text
		.replace(/[\u{1F600}-\u{1F6FF}]/gu, '') // Remove emojis
		.replace(/[\u{2700}-\u{27BF}]/gu, '') // Remove additional symbols
		.replace(/[^a-zA-Z0-9.,!?'"() ]/g, '') // Keep only necessary characters
		.replace(/\s+/g, ' ') // Normalize spaces
		.replaceAll('"', ' ')
		.replaceAll("'", '')
		.trim();
}

const handleBotStatus = async (from, to, isThinking) => {
	await sendMessage(
		isThinking ? MessageType.BOTSTARTTHINKING : MessageType.BOTENDTHINKING,
		'BOTSTATUS',
		{
			date: Date.now(),
			from: encrypt(to),
			to: encrypt(from),
		},
		[from, to],
		true
	);
};

const handleChatBotMessages = async (messageObj) => {
	const from = decrypt(messageObj.data.from);
	const messageDate = Date.now();
	const name = messageObj.name;
	const to = decrypt(messageObj.data.to);
	const type = messageObj.type;

	const receiversList = [from, to];

	await handleBotStatus(from, to, true);

	let log = await db.find({
		$or: [
			{from: to, to: from},
			{from, to},
		],
	});

	log = log.length > 5 ? log.slice(-5) : log;

	log = log
		.sort((a, b) => a.date - b.date)
		.map((logItem) => {
			return {
				content: decrypt(logItem.message),
				role: logItem.from.startsWith('bot') ? 'assistant' : 'user',
			};
		});

	if (to !== 'bot_ui_ai') {
		const context = contextService.get(to.split('_')[1]);

		if (context.length <= 0) {
			await handleBotStatus(from, to, false);

			await sendMessage(
				type,
				name,
				{
					chatMessageType: messageObj.data.chatMessageType,
					date: messageDate,
					from: encrypt(to),
					message: 'Unfortunately i dont have a context right now.',
					to: encrypt(from),
				},
				receiversList,
				true
			);

			return;
		}

		const contextLog = context.length
			? context.map((contextItem) => {
					return {
						content: `${advancedCleanText(contextItem)}`,
						role: 'assistant',
					};
				})
			: [];

		log.unshift(...contextLog);
	}

	const answer = await conversation(log);

	const lrMessageObjectEntry = {
		date: Date.now(),
		from: to,
		message: encrypt(answer),
		to: from,
	};

	await db.insert(lrMessageObjectEntry);

	await handleBotStatus(from, to, false);

	await sendMessage(
		type,
		name,
		{
			chatMessageType: messageObj.data.chatMessageType,
			date: messageDate,
			from: encrypt(to),
			message: answer,
			to: encrypt(from),
		},
		receiversList,
		true
	);
};

const trackBotChatMessages = async (lrMessageObjectEntry) => {
	await db.insert(lrMessageObjectEntry);
};

const handleOnMessage = async (senderConnection, message) => {
	const messageObj = JSON.parse(message);

	switch (messageObj.type) {
		case MessageType.MESSAGE_NOTIFICATION: {
			break;
		}
		case MessageType.MASSAGE: {
			const from = decrypt(messageObj.data.from);
			const to = decrypt(messageObj.data.to);
			const name = messageObj.name;
			const type = messageObj.type;
			const receiversList = [from, to];
			const messageDate = Date.now();
			const isToChatbot = to.startsWith('bot');
			const isFromChatbot = from.startsWith('bot');

			switch (messageObj.data.chatMessageType) {
				case ChatMessageType.TEXT: {
					try {
						const lrMessageObjectEntry = {
							date: messageDate,
							from,
							message: JSON.stringify({
								chatMessageType:
									messageObj.data.chatMessageType,
								date: Date.now(),
								from: messageObj.data.from,
								message: encrypt(messageObj.data.message),
								to: messageObj.data.to,
							}),
							to,
						};

						if (!isFromChatbot && !isToChatbot) {
							await LRMessages.post(lrMessageObjectEntry);
						}
						else {
							const lrMessageObjectEntry = {
								date: messageDate,
								from,
								message: encrypt(messageObj.data.message),
								to,
							};

							await trackBotChatMessages(lrMessageObjectEntry);
						}

						await sendMessage(
							type,
							name,
							{
								chatMessageType:
									messageObj.data.chatMessageType,
								date: messageDate,
								from: messageObj.data.from,
								message: messageObj.data.message,
								to: messageObj.data.to,
							},
							receiversList,
							true
						);

						if (isToChatbot) {
							await handleChatBotMessages(messageObj);
						}
					}
					catch (error) {
						logError('Error while sending message', error);
					}
					break;
				}
				case ChatMessageType.FILE: {
					try {
						const lrMessageObjectEntry = {
							date: messageDate,
							from,
							message: JSON.stringify({
								chatMessageType:
									messageObj.data.chatMessageType,
								date: Date.now(),
								file: {
									fileName: encrypt(
										messageObj.data.file.fileName
									),
									fileUrl: encrypt(
										messageObj.data.file.fileUrl
									),
									preview: encrypt(
										messageObj.data.file.preview
									),
								},
								from: messageObj.data.from,
								to: messageObj.data.to,
							}),
							to,
						};

						await LRMessages.post(lrMessageObjectEntry);

						await sendMessage(
							type,
							name,
							{
								chatMessageType:
									messageObj.data.chatMessageType,
								date: messageDate,
								file: messageObj.data.file,
								from: messageObj.data.from,
								to: messageObj.data.to,
							},
							receiversList,
							true
						);
					}
					catch (error) {
						logError('Error while sending message', error);
					}
					break;
				}
				default: {
					break;
				}
			}

			break;
		}
		case MessageType.MESSAGE_SEEN: {
			const to = decrypt(messageObj.data.to);

			const pendingNotifications = await getPendingEvents({
				'clientId': to,
				'data.from': messageObj.data.from,
				'data.to': messageObj.data.to,
				'type': MessageType.MESSAGE_NOTIFICATION,
			});

			await Promise.all(
				pendingNotifications.map(async (pendingNotification) => {
					await db.remove(pendingNotification, {multi: false}, true);
				})
			);

			await handleMissingMessageNotifications(to);

			break;
		}
		case MessageType.START_BOT_CONNECTION: {
			const from = decrypt(messageObj.data.from);
			const to = decrypt(messageObj.data.to);

			await db.clearByQuery({
				$or: [
					{from: to, to: from},
					{from, to},
				],
			});

			break;
		}
		case MessageType.END_BOT_CONNECTION: {
			const from = decrypt(messageObj.data.from);
			const to = decrypt(messageObj.data.to);

			await db.clearByQuery({
				$or: [
					{from: to, to: from},
					{from, to},
				],
			});

			break;
		}
		default: {
			break;
		}
	}
};

const getPendingEvents = async (query) => {
	const result = await db.find(query);

	return result;
};

const sendMessage = async (
	type,
	name,
	data,
	clientIds,
	enableOfflineMessageQueue = false
) => {
	const message = buildMessageEnvelope(type, name, data);

	for (const clientId of clientIds) {
		let isOnline = false;

		if (clients.has(clientId)) {
			for (const client of clients.get(clientId)) {
				if (client.OPEN === 1) {
					client.send(JSON.stringify(message));

					isOnline = true;
				}
			}
		}

		await handleStorePendingMessages(
			clientId,
			isOnline,
			enableOfflineMessageQueue,
			name,
			type,
			data
		);
	}
};

const handleStorePendingMessages = async (
	clientId,
	isOnline,
	enableOfflineMessageQueue,
	name,
	type,
	data
) => {
	switch (type) {
		case MessageType.IMCONTACTS: {
			break;
		}

		case MessageType.MASSAGE: {
			await db.insert({
				clientId,
				data,
				name,
				type: MessageType.MESSAGE_NOTIFICATION,
			});

			await handleMissingMessageNotifications(clientId);

			break;
		}
		default: {
			break;
		}
	}
};

module.exports = {
	initializeWebSocket,
	isOnline,
	sendMessage,
};
