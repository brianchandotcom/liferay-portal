const {Router} = require("express");
const {getContactsStatus} = require("../../interfaces/contacts");
const LRMessages = require('../../services/liferay-interface/messages');
const {decrypt, encrypt} = require("../../util/encryption");
const {ChatMessageType} = require("../../util/types");
const {getObjectDefinitions} = require("../../services/liferay-interface/objects");

const router = Router();

router.get("/list", async (req, res) => {

    res.send(await getObjectDefinitions());

});



module.exports = router;
