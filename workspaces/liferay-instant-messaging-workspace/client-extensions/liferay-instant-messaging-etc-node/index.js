/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {lookupConfig} = require('@rotty3000/config-node');
const bodyParser = require('body-parser');
const cors = require('cors');
const express = require('express');
const http = require('http'); // eslint-disable-line

const instantMessaging = require('./custom-apis/instant-messaging/im');
const objectDefinitionCustomAPIs = require('./custom-apis/objects/object-definitions');
const contextActions = require('./object/actions/context');
const {initializeWebSocket} = require('./sockets-server/websocketServer');
const {
	corsWithReady,
	liferayJWT,
} = require('./util/liferay-oauth2-resource-server.js');
const {logInfo} = require('./util/log');
const serverPort = lookupConfig('server.port');
const readyPath = lookupConfig('ready.path');

const app = express();
const httpServer = http.createServer(app);

app.use(bodyParser.json());
app.use(cors());
app.use(corsWithReady);
app.use(express.json({limit: '10mb'}));
app.use(express.urlencoded({extended: true, limit: '10mb'}));
app.use(liferayJWT);

const io = initializeWebSocket(httpServer); // eslint-disable-line

// Load Client Extensions Routes here

app.use('/context/', contextActions);

app.use('/im/', instantMessaging);

app.use('/ui/object/definition/', objectDefinitionCustomAPIs);

app.get(readyPath, (req, res) => {
	res.send({groups: ['liveness', 'readiness'], status: 'UP'});
});

httpServer.listen(serverPort, () => {
	logInfo(`App listening on ${serverPort}`);
});

module.exports = app;
