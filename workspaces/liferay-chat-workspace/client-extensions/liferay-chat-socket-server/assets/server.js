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
module.exports =  class SocketClient
{
    constructor(id,name,avatar) {
        this.clientId = id;
        this.clientName = name;
        this.clientAvatar = avatar;
    }
}
const app = require('express')();
const server = require('http').createServer(app);
const cors = require('cors');
const io = require('socket.io')(server, {
    cors: {
        origin: '*'
    }
});
const { v4: uuidv4 } = require('uuid');

//Global Variables
clientsSessions = {};
clients = [];
clientsProfiles = [];

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

app.use(cors());

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

io.on('disconnect',(socket)=>{
   console.log('connection lost!')
});

app.get('/', (request, response) => {
    response.sendStatus(200);
});

server.listen(3000, () => {
    console.log('listening on *:3000');
});
