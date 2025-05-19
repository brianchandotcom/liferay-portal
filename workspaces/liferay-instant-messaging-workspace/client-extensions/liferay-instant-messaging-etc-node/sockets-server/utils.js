const buildMessageEnvelope = (type,name,data) => {

    return {
        name:name,
        type:type,
        data:data
    }

}

module.exports = {
    buildMessageEnvelope
};
