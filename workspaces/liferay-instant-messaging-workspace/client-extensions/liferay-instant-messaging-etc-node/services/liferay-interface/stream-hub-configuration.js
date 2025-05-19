const {lrRequest} = require("../../util/request");
const {getServerToken} = require("../../util/silent-authorization");


const OBJECT_ENDPOINT = "/o/c/streamhubconfigurations/";

const getConfigurationEntryByObjectDefinitionId = async (objectDefinitionId,token=null) => {

    try {
        token = token || await getServerToken();

        let result = await lrRequest({
            method: 'get',
            url:`${OBJECT_ENDPOINT}?filter=objectDefinitionId eq '${objectDefinitionId}'`
        },token);

        if (result.data && result.data.totalCount > 0)
            return result.data.items[0];
        else
            return null;

    }catch (error){
        throw new Error(`postObjectActions: ${error.message}`)
    }

}


module.exports = {

    getConfigurationEntryByObjectDefinitionId

}
