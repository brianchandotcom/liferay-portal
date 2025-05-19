const WebSocket = require('ws');
const {clientLiferayJWTValidation} = require("../util/liferay-oauth2-resource-server");
const {v4: uuidv4, v1: uuidv1} = require('uuid');
const {getServerToken} = require("../util/silent-authorization");
const {buildMessageEnvelope} = require("./utils");
const {logError, logInfo} = require("../util/log");
const db = require("./pending-events/pending-events");
const {parse} = require('url');
const {ConnectionType, MessageType} = require("./types");
const {encrypt, decrypt} = require("../util/encryption");
const LRMessages = require('../services/liferay-interface/messages');
const {ChatMessageType} = require("../util/types");
const contextService = require("../services/contexts/context");
const {conversation} = require("../services/ai/conversation");
const clients = new Map();

const isOnline = (userId) => {

    if (clients) {

        return clients.has(userId.toString());
    } else
        return false;

}

const broadcastContactsUpdate = () => {

    let connectedImContactsId = [];
    let IMConnections = [];

    clients.keys().forEach((client) => {

        let imConnections = clients.get(client)
            .filter(connection => connection.type === ConnectionType.IM && connection.OPEN === 1);

        if (imConnections && imConnections.length > 0) {
            connectedImContactsId.push(encrypt(imConnections[0].userId));
        }

    });

    let message = buildMessageEnvelope(MessageType.IMCONTACTS, "connectionsUpdate", connectedImContactsId);

    clients.keys().forEach((client) => {

        let imConnections = clients.get(client)
            .filter(connection => connection.type === ConnectionType.IM && connection.OPEN === 1);

        if (imConnections && imConnections.length > 0) {
            imConnections.forEach(connection => {
                connection.send(JSON.stringify(message));
            })
        }

    });

    IMConnections.forEach(connection => {

        if (connection.OPEN === 1) {
            connection.send(JSON.stringify(message));
        }


    })

}

const handleMissingMessageNotifications = async (clientId) => {

    let pendingNotifications = await getPendingEvents({clientId: clientId, type: MessageType.MESSAGE_NOTIFICATION});

    let encryptedClientId = encrypt(clientId);

    pendingNotifications = pendingNotifications.filter(notification => notification.data.from != encryptedClientId);

    const countByFrom = pendingNotifications.reduce((acc, message) => {
        const from = message.data.from;
        acc[from] = (acc[from] || 0) + 1;
        return acc;
    }, {});


    sendMessage(MessageType.MESSAGE_NOTIFICATION,
        MessageType.MESSAGE_NOTIFICATION,
        countByFrom, [clientId], false);

}

const initializeWebSocket = (httpServer) => {

    const wss = new WebSocket.Server({
        server: httpServer,
        path: '/server',
    });

    wss.on('connection', async (ws, req) => {

        const token = req.headers['sec-websocket-protocol'];

        const parameters = parse(req.url, true).query;

        let decodedToken = await clientLiferayJWTValidation(token);

        if (!decodedToken || decodedToken.username === "default@liferay.com") {

            logError('Invalid token, closing connection.');

            ws.close(1008, 'Invalid token');

            return null;
        }

        logInfo(`New WebSocket connection established for (User Id: ${decodedToken.sub}, Email: ${decodedToken.username}).`);

        ws["userId"] = decodedToken.sub;

        ws["uuid"] = uuidv4();

        if ('type' in parameters && parameters['type'] === ConnectionType.IM) {

            ws['type'] = ConnectionType.IM;

        }else{

            ws.close(1008, 'Invalid connection type.');

            return null;
        }

        if (clients.has(decodedToken.sub)) {

            clients.set(decodedToken.sub, [ws, ...clients.get(decodedToken.sub)]);

        } else {

            clients.set(decodedToken.sub, [ws]);

        }


        if (ws.type === ConnectionType.IM) {

            broadcastContactsUpdate();
            await handleMissingMessageNotifications(decodedToken.sub);

        }

        ws.on('message', async (message) => {

            await handleOnMessage(ws, message)

        });

        ws.on('close', async () => {

            clients.set(ws["userId"],
                clients.get(ws["userId"])
                    .filter(client => client.uuid !== ws.uuid));

            if (ws.type === ConnectionType.IM) {
                broadcastContactsUpdate();
            }


        });
        // Handle errors
        ws.on('error', (err) => {
            logError('WebSocket error:', err);
        });
    });

    return wss;
}

function advancedCleanText(text) {
    return text
        .replace(/[\u{1F600}-\u{1F6FF}]/gu, '') // Remove emojis
        .replace(/[\u{2700}-\u{27BF}]/gu, '') // Remove additional symbols
        .replace(/[^a-zA-Z0-9.,!?'"() ]/g, '') // Keep only necessary characters
        .replace(/\s+/g, ' ') // Normalize spaces
        .replaceAll('"'," ")
        .replaceAll("'","")
        .trim();
}

const handleBotStatus = async (from,to,isThinking) => {

    await sendMessage(
        isThinking?MessageType.BOTSTARTTHINKING:MessageType.BOTENDTHINKING,
        "BOTSTATUS", {
        from: encrypt(to),
        to: encrypt(from),
        date: Date.now(),

    }, [from,to], true);

}

const handleChatBotMessages = async (messageObj) => {

    let from = decrypt(messageObj.data.from);
    let to = decrypt(messageObj.data.to);
    let name = messageObj.name;
    let type = messageObj.type;
    let receiversList = [from, to];
    let messageDate = Date.now();

    await handleBotStatus(from,to,true);

    let log = await db.find({$or:[
            {"from":to,"to":from},
            {"from":from,"to":to}
        ]});

    log= log.length > 5 ? log.slice(-5):log;

    log = log.sort((a,b)=> a.date -b.date).map(logItem => {
        return {
            role:logItem.from.startsWith("bot")?"assistant":"user",
            content:decrypt(logItem.message)
        }
    })

    if (to !== "bot_ui_ai"){

        let context = contextService.get(to.split('_')[1]);

        if (context.length <=0){

            await handleBotStatus(from,to,false);

            await sendMessage(type, name, {

                from: encrypt(to),
                to: encrypt(from),
                message: "Unfortunately i dont have a context right now.",
                chatMessageType: messageObj.data.chatMessageType,
                date: messageDate,

            }, receiversList, true);

            return;

        }

        let contextLog = context.length>0? context.map(contextItem => {
            return {
                role:"assistant",
                content:`${advancedCleanText(contextItem)}`
            }
        }):[];

        log.unshift(...contextLog);
    }


    let answer = await conversation(log);

    let lrMessageObjectEntry = {
        from: to,
        to: from,
        message: encrypt(answer),
        date: Date.now(),
    }

    await db.insert(lrMessageObjectEntry);

    await handleBotStatus(from,to,false);

    await sendMessage(type, name, {

        from: encrypt(to),
        to: encrypt(from),
        message: answer,
        chatMessageType: messageObj.data.chatMessageType,
        date: messageDate,

    }, receiversList, true);

}

const trackBotChatMessages = async (lrMessageObjectEntry) => {

    await db.insert(lrMessageObjectEntry);

}

const handleOnMessage = async (senderConnection, message) => {

    let messageObj = JSON.parse(message);

    switch (messageObj.type) {
        case MessageType.MESSAGE_NOTIFICATION: {
            break;
        }
        case MessageType.MASSAGE: {

            let from = decrypt(messageObj.data.from);
            let to = decrypt(messageObj.data.to);
            let name = messageObj.name;
            let type = messageObj.type;
            let receiversList = [from, to];
            let messageDate = Date.now();
            let isToChatbot = to.startsWith('bot');
            let isFromChatbot = from.startsWith('bot');

            switch (messageObj.data.chatMessageType) {

                case ChatMessageType.TEXT: {
                    try {
                        let lrMessageObjectEntry = {
                            from: from,
                            to: to,
                            message: JSON.stringify({

                                from: messageObj.data.from,
                                to: messageObj.data.to,
                                message: encrypt(messageObj.data.message),
                                chatMessageType: messageObj.data.chatMessageType,
                                date: Date.now(),

                            }),
                            date: messageDate
                        }

                        if (!isFromChatbot && !isToChatbot) {

                            await LRMessages.post(lrMessageObjectEntry);
                        }else{

                            let lrMessageObjectEntry = {
                                from: from,
                                to: to,
                                message: encrypt(messageObj.data.message),
                                date: messageDate
                            }

                            await trackBotChatMessages(lrMessageObjectEntry)

                        }

                        await sendMessage(type, name, {

                            from: messageObj.data.from,
                            to: messageObj.data.to,
                            message: messageObj.data.message,
                            chatMessageType: messageObj.data.chatMessageType,
                            date: messageDate,

                        }, receiversList, true);

                        if (isToChatbot) {

                            console.log(messageObj.data.to);
                            await handleChatBotMessages(messageObj);

                        }


                    } catch (error) {
                        logError('Error while sending message', error);
                    }
                    break;
                }
                case ChatMessageType.FILE: {
                    try {

                        let lrMessageObjectEntry = {
                            from: from,
                            to: to,
                            message: JSON.stringify({
                                from: messageObj.data.from,
                                to: messageObj.data.to,
                                file: {
                                    preview: encrypt(messageObj.data.file.preview),
                                    fileName: encrypt(messageObj.data.file.fileName),
                                    fileUrl: encrypt(messageObj.data.file.fileUrl),
                                },
                                chatMessageType: messageObj.data.chatMessageType,
                                date: Date.now(),
                            }),
                            date: messageDate
                        }

                        await LRMessages.post(lrMessageObjectEntry);

                        await sendMessage(type, name, {
                            from: messageObj.data.from,
                            to: messageObj.data.to,
                            file: messageObj.data.file,
                            chatMessageType: messageObj.data.chatMessageType,
                            date: messageDate,
                        }, receiversList, true);


                    } catch (error) {
                        logError('Error while sending message', error);
                    }
                    break;
                }
            }

            break;
        }
        case MessageType.IMCONTACTS: {

        }
        case MessageType.MESSAGE_SEEN: {

            let from = decrypt(messageObj.data.from);
            let to = decrypt(messageObj.data.to);

            let pendingNotifications = await
                getPendingEvents(
                    {
                        clientId: to,
                        type: MessageType.MESSAGE_NOTIFICATION,
                        "data.from": messageObj.data.from,
                        "data.to": messageObj.data.to,
                    });

            await Promise.all(
                pendingNotifications.map(async (pendingNotification) => {
                    await db.remove(pendingNotification, {multi: false}, true);
                })
            );

            await handleMissingMessageNotifications(to);

            break;

        }
        case MessageType.START_BOT_CONNECTION:{

            let from = decrypt(messageObj.data.from);
            let to = decrypt(messageObj.data.to);

            await db.clearByQuery({
                $or: [
                    {"from": to, "to": from},
                    {"from": from, "to": to}
                ]
            })

            break;
        }
        case MessageType.END_BOT_CONNECTION:{

            let from = decrypt(messageObj.data.from);
            let to = decrypt(messageObj.data.to);

            await db.clearByQuery({
                $or: [
                    {"from": to, "to": from},
                    {"from": from, "to": to}
                ]
            });

            break;
        }
    }

}

const getPendingEvents = async (query) => {

    const result = await db.find(query);

    return result;

}

const sendMessage = async (type, name, data, clientIds, enableOfflineMessageQueue = false) => {

    let message = buildMessageEnvelope(type, name, data);

    for (const clientId of clientIds) {

        let isOnline = false;

        if (clients.has(clientId)) {

            for (const client of clients.get(clientId)) {

                if (client.OPEN == 1) {

                    client.send(JSON.stringify(message));

                    isOnline = true;
                }

            }

        }

        await handleStorePendingMessages(clientId, isOnline, enableOfflineMessageQueue, name, type, data);


    }
}

const handleStorePendingMessages = async (clientId, isOnline, enableOfflineMessageQueue, name, type, data) => {

    switch (type) {

        case MessageType.IMCONTACTS: {

            break;
        }

        case MessageType.MASSAGE: {

            await db.insert({
                clientId: clientId,
                type: MessageType.MESSAGE_NOTIFICATION,
                name: name,
                data: data
            });

            await handleMissingMessageNotifications(clientId);

            break;

        }

    }

}

module.exports = {
    initializeWebSocket,
    sendMessage,
    isOnline
};
