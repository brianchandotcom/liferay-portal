const {isOnline} = require("../sockets-server/websocketServer");
const {getLiferayContacts} = require("../services/liferay-interface/contacts");
const {encrypt} = require("../util/encryption");
const {getChatBots} = require("../services/liferay-interface/chatbots");


const getContactsWithStatus = async (pageSize=0,page=0,token = null) => {

    let contacts = await getLiferayContacts(pageSize, page,token);

    contacts = contacts.map(contact => {
        return {
            ...contact,
            online:isOnline(contact.userId)
        }
    });

    return contacts;
}

const getContactsStatus = async (pageSize,page,token = null,jwt) => {

    let contacts = await getLiferayContacts(pageSize, page,token);

    let chatBots = await getChatBots(0,0,token);

    contacts = [...chatBots,...contacts].map(contact => {
        return {
            name: contact.name,
            online: contact.type == "bot"? true:isOnline(contact.userId),
            userId: encrypt(contact.userId),
            self: jwt.sub.toString() == contact.userId.toString(),
            type:contact.type || "person"
        }
    });

    return contacts;
}

module.exports = {
    getContactsStatus,
    getContactsWithStatus
}
