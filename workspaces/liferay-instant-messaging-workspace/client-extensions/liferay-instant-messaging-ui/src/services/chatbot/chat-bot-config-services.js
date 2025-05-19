const {request} = require("../../utils/request");
const {config} = require("../../utils/constants");


const getEntries = (pageIndex,pageSize) => {

    return request({
        method: 'GET',
        url: `${config.chatBotEndPoint}?page=${pageIndex}&pageSize=${pageSize}`,
    });

}

const deleteEntry = (entryId) => {

    return request({
        method: 'DELETE',
        url: `${config.chatBotEndPoint}${entryId}`,
    });

}

module.exports = {
    getEntries,
    deleteEntry
}
