const {Router} = require("express");
const {getContactsStatus} = require("../../interfaces/contacts");
const LRMessages = require('../../services/liferay-interface/messages');
const {decrypt, encrypt} = require("../../util/encryption");
const {ChatMessageType} = require("../../util/types");

const router = Router();

router.get("/contacts", async (req, res) => {

    res.send(await getContactsStatus(0, 0, null, req.jwt));

});

router.get("/chatlog/:from/:to", async (req, res) => {

    const {from, to} = req.params;

    let chatLog = await LRMessages.get(decrypt(from), decrypt(to));

    let decryptedMessageLog = [];

    for (let logIndex = 0; logIndex < chatLog.data.items.length; logIndex++) {

        let messageObj = JSON.parse(chatLog.data.items[logIndex].message);

        if (messageObj.chatMessageType === ChatMessageType.TEXT) {

            messageObj.message = decrypt(messageObj.message);
            decryptedMessageLog.push(messageObj);
        }

        if (messageObj.chatMessageType === ChatMessageType.FILE) {
            messageObj.file = {
                preview: decrypt(messageObj.file.preview),
                fileName: decrypt(messageObj.file.fileName),
                fileUrl: decrypt(messageObj.file.fileUrl),
            };

            decryptedMessageLog.push(messageObj);
        }

    }

    res.send(decryptedMessageLog);

});


module.exports = router;
