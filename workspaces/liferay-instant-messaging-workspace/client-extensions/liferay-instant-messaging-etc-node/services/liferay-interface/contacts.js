const {hasCacheKey, getCacheEntry, putCacheEntry, getCacheObject, setCacheEntryCallback} = require("../../util/caching");
const {lrRequest} = require("../../util/request");
const {getServerToken} = require("../../util/silent-authorization");
const {isOnline} = require("../../sockets-server/websocketServer");

const getLiferayContacts = async (pageSize,page,token = null) => {

    token = token  || await getServerToken();

    const cacheKey = `Contacts_${pageSize}_${page}`;

    const endPoint = `/o/headless-admin-user/v1.0/user-accounts?page=${page}&pageSize=${pageSize}`;

    if(hasCacheKey(cacheKey)) {

        return getCacheEntry(cacheKey);

    }else{

        let contacts = await lrRequest({
            method: 'GET',
            url:endPoint
        },token);

        const result = contacts.data.items.map(item => {
            return {
                emailAddress : item.emailAddress,
                userId : item.id,
                roles: item.roleBriefs,
                familyName: item.familyName,
                givenName:item.givenName,
                name:item.name,
            }
        });

        putCacheEntry(cacheKey,result, 0.2 *60);

        setCacheEntryCallback(cacheKey,async  ()=>{

            let token = await getServerToken();

            getLiferayContacts(pageSize,page,token);

        });

        return result;
    }

}


module.exports = {
    getLiferayContacts
}
