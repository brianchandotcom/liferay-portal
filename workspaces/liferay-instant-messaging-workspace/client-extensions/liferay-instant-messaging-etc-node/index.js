const express = require('express');
const http = require("http");
const bodyParser = require('body-parser');
const cors = require('cors');
const { lookupConfig } = require('@rotty3000/config-node');
const { corsWithReady, liferayJWT } = require('./util/liferay-oauth2-resource-server.js');
const {initializeWebSocket, sendMessage, isAlive} = require("./sockets-server/websocketServer");
const {logInfo} = require("./util/log");
const contextActions = require("./object/actions/context");
const instantMessaging = require("./custom-apis/instant-messaging/im");
const objectDefinitionCustomAPIs = require("./custom-apis/objects/object-definitions");
const serverPort = lookupConfig('server.port');
const readyPath = lookupConfig('ready.path');

const app = express();
const httpServer = http.createServer(app);

app.use(bodyParser.json());
app.use(cors());
app.use(corsWithReady);
app.use(express.json({ limit: '10mb' }));
app.use(express.urlencoded({ extended: true, limit: '10mb' }));
app.use(liferayJWT);

const io = initializeWebSocket(httpServer);

//Load Client Extensions Routes here
app.use("/context/",contextActions);

app.use("/im/",instantMessaging);

app.use("/ui/object/definition/",objectDefinitionCustomAPIs)

app.get(readyPath, (req, res) => {

    res.send({ groups: ['liveness', 'readiness'], status: 'UP' });

});

httpServer.listen(serverPort, () => {

    logInfo(`App listening on ${serverPort}`);

});

module.exports = app;
