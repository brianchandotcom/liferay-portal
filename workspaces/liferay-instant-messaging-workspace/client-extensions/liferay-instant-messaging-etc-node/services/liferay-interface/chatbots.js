const {getServerToken} = require("../../util/silent-authorization");
const {lrRequest} = require("../../util/request");
const {hasCacheKey, getCacheEntry, putCacheEntry, setCacheEntryCallback} = require("../../util/caching");


const OBJECT_ENDPOINT = '/o/c/chatbots/';

const getChatBots = async (pageSize=0,page=0,token = null) => {

    token = token  || await getServerToken();

    const cacheKey = `bots_${pageSize}_${page}`;

    const endPoint = `${OBJECT_ENDPOINT}?page=${page}&pageSize=${pageSize}`;

    if(hasCacheKey(cacheKey)) {

        return getCacheEntry(cacheKey);

    }else{

        let chatbots = await lrRequest({
            method: 'GET',
            url:endPoint
        },token);

        const result = chatbots.data.items.map(item => {
            return {
                emailAddress : "chatbot@liferay.com",
                userId : `bot_${item.contextObjectDefinitionID}`,
                roles: [],
                familyName: item.familyName,
                givenName:item.givenName,
                name:item.name,
                type: 'bot'
            }
        });

        /*putCacheEntry(cacheKey,result, 0);

        setCacheEntryCallback(cacheKey,async  ()=>{

            let token = await getServerToken();

            await getChatBots();

        });*/

        return result;
    }

}

const getChatBotByObjectDefinitionId = async (objectDefinitionId,token = null) => {

    token = token  || await getServerToken();

    const cacheKey = `bots_${objectDefinitionId}`;

    const endPoint = `${OBJECT_ENDPOINT}?fields=contextObjectDefinitionID,name,contextClauseField&filter=contextObjectDefinitionID eq '${objectDefinitionId}'`;

    if(hasCacheKey(cacheKey)) {

        return getCacheEntry(cacheKey);

    }else{

        let chatbots = await lrRequest({
            method: 'GET',
            url:endPoint
        },token);

        if (chatbots.data.items.length > 0) {

            const result = chatbots.data.items[0];

            putCacheEntry(cacheKey,result, 10 * 60);

            return result;
        }else{

            return null;

        }

    }

}


module.exports = {
    getChatBots,
    getChatBotByObjectDefinitionId
}

