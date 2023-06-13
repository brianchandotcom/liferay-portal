## Liferay Chat Workspace

This workspace has been established to showcase the latest Liferay client extensions, which encompass comprehensive chat functionality. The chat feature leverages Liferay objects for efficient message storage and management. Additionally, Liferay permissions are utilized to govern the security of the chat system. A Node.js server is employed to facilitate the exchange of messages between users.

### Workspace breakdown

1.  Chat Message “**Object - Batch** **Type**”: Store chat messages. Each message will be stored twice. One will be owned by the sender and one will be owned by the receiver. 
2.  Websocket Server “**NodeJS - Service Type**”: A Node.js server will be utilized as a message exchange server for the chat application.
3.  Chat Widget - **“NG Web Component - Custom Element Type”:** An Angular Web Component which communicates with LR Object API to post messages and connects to the Chat Server to get notified when messages arrive.

