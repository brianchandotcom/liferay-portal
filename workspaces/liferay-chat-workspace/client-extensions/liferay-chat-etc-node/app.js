/* ***************************************************
*               Liferay Chat Server                  *
* ****************************************************
* A Web Socket-based chat server has been developed  *
* to facilitate message exchange functionality for a *
* Liferay chat application. This server is designed  *
* to exclusively handle message identifier           *
* information, such as user IDs and message IDs,     *
* while refraining from receiving any actual message *
* content.                                           *
* ****************************************************
* Authors:                                           *
*   - Mahmoud Hussein Tayem                          *
*   - Mumen Hussein Tayem                            *
* ***************************************************/
import express from 'express';
import { Server } from 'http';
import { Server as SocketServer } from 'socket.io';
import config from './util/configTreePath.js'
import {corsWithReady, liferayJWT} from './util/liferay-oauth2-resource-server.js';
import {logger} from './util/logger.js';
import { v4 as uuidv4 } from 'uuid';

//Global Variables
let clientsSessions = {};
let clients = [];
let clientsProfiles = [];

const serverPort = config['server.port'];
const app = express();
const server = new Server(app);
const io = new SocketServer(server, {
	cors: {
		origin: '*'
	}
});
logger.log(`config: ${JSON.stringify(config, null, '\t')}`);
app.use(express.json());
app.use(corsWithReady);
app.use(liferayJWT);

function getConnectedClientsArray(clientId)
{
	var clientsId = Object.keys(clientsSessions);
	var clientsInfoArray = [];
	clientsId.forEach(client=>{
		if (client != clientId)
		{
			clientsInfoArray.push({
				id:client,
				name:clientsSessions[client].userName
			})
		}
	});
	return clientsInfoArray;
}
function broadCastClientsStatus()
{

	console.log('broad casting clients!');
	let clients = Object.keys(clientsSessions);
	clients.forEach(client=>{
		let clientsInfoArray = getConnectedClientsArray(client);
		let clientSockets = clientsSessions[client].socket;
		let clientAvailableSockets = Object.keys(clientSockets);
		clientAvailableSockets.forEach(socketId =>{
			clientsSessions[client].socket[socketId].emit('who-is-on',clientsInfoArray);
		});
	});

}
function storeClientSession(socket,userId,_userName,_connectionId)
{

	_userName = _userName.length === 0 ? "Guest Account" : _userName;
	if (userId in clientsSessions)
	{
		clientsSessions[userId].socket[_connectionId] = socket;
	}else
	{
		let socketObject = {};
		socketObject[_connectionId] = socket;
		clientsSessions[userId] = {
			socket:socketObject,
			userName:_userName
		};
		// notify others only if new unique session has been made
		broadCastClientsStatus();
	}
}
function connectionLost(userId,_connectionId)
{
	if (userId in clientsSessions)
	{
		if (_connectionId in clientsSessions[userId].socket)
		{
			delete clientsSessions[userId].socket[_connectionId];
			if (Object.keys(clientsSessions[userId].socket).length === 0 )
			{
				delete clientsSessions[userId];
				broadCastClientsStatus();
			}
		}
	}
}
function sendDirectMessage(toClientId,messageId,fromClientId)
{
	try {
		let sessionsIds = Object.keys(clientsSessions[toClientId].socket);
		sessionsIds.forEach(sessionId=>{
			clientsSessions[toClientId].socket[sessionId].emit('message',[fromClientId,messageId])
		})

	}catch (exp)
	{
		console.log(`Error while try to send message id ${messageId} from client id ${fromClientId} to clientId ${toClientId}, Client might be offline!`)
	}

}
io.on('connection', (socket) => {
	const userId = socket.handshake.query.userId;
	console.log(socket.handshake.query.userName);
	const userName = socket.handshake.query.userName;
	const connectionId = socket.handshake.query.uuid= uuidv4();
	socket.on('disconnect',()=>{
		connectionLost(socket.handshake.query.userId,socket.handshake.query.uuid);
	});
	socket.on('who-is-on',(data)=>{
		socket.emit('who-is-on',getConnectedClientsArray(socket.handshake.query.userId));
	});
	socket.on('message',(data)=>{
		const [toClientId, messageId] = data;
		sendDirectMessage(toClientId,messageId,socket.handshake.query.userId);
	});
	//Storing Clients Profiles
	storeClientSession(socket,userId,userName,connectionId);

});
app.get(config.readyPath, (req, res) => {
	res.send('READY');
});
server.listen(serverPort, () => {
	console.log(`App listening on ${serverPort}`);
});

export default server;
