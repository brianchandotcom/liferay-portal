const {lrRequest} = require("../../util/request");
const {getServerToken} = require("../../util/silent-authorization");


const OBJECT_ENDPOINT = "/o/object-admin/v1.0/object-definitions/";

const postObjectActions = async (objectId, actions,token=null) => {

    try {
        token = token || await getServerToken();

        let result = await lrRequest({
            method: 'POST',
            url:`${OBJECT_ENDPOINT}${objectId}/object-actions/batch`,
            data:JSON.stringify(actions),
        },token);

        return result;

    }catch (error){
        throw new Error(`postObjectActions: ${error.message}`)
    }

}

const deleteObjectActions = async (actions,token=null) => {

    try {
        token = token || await getServerToken();

        let result = await lrRequest({
            method: 'DELETE',
            url:`/o/object-admin/v1.0/object-actions/batch`,
            data:JSON.stringify(actions),
        },token);

        return result;

    }catch (error){
        throw new Error(`postObjectActions: ${error.message}`)
    }

}

const getObjectActions = async (objectId,token=null) => {

    try {
        token = token || await getServerToken();

        let result = await lrRequest({
            method: 'GET',
            url:`${OBJECT_ENDPOINT}${objectId}/object-actions/`,
        },token);

        if (result.data && result.data.totalCount > 0)
            return result.data.items;
        else
            return null;

    }catch (error){
        throw new Error(`postObjectActions: ${error.message}`)
    }

}

const getObjectDefinitions = async (token = null) => {

    try {
        token = token || await getServerToken();

        let definitions = await lrRequest({
            method: 'GET',
            url:`${OBJECT_ENDPOINT}?page=0`
        },token);

        let result = definitions.data.items.filter(definition => definition.storageType === "default" && !definition.system).map(definition => {

            return {
                id:definition.id,
                externalReferenceCode:definition.externalReferenceCode,
                name:definition.name,
                objectFields:definition.objectFields.filter(field=> !field.system).map(field => {
                    return {
                        externalReferenceCode:field.externalReferenceCode,
                        id:field.id,
                        name:field.name,
                        businessType:field.businessType
                    }
                })
            }

        })

        return result;

    }catch(error) {

        throw new Error(`getConfigObject: ${error.message}`);

        return null;
    }

}


module.exports = {

    postObjectActions,
    getObjectActions,
    deleteObjectActions,
    getObjectDefinitions

}
