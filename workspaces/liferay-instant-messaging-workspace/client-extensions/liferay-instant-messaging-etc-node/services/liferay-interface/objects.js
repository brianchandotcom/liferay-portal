const {lrRequest} = require("../../util/request");
const {getServerToken} = require("../../util/silent-authorization");
const {logError} = require("../../util/log");

const TARGET_OBJECT_ENDPOINT = "/api/jsonws/invoke";

const OBJECT_ENDPOINT = "/o/object-admin/v1.0/object-definitions/";

const OBJECT_ACTION_ENDPOINT = "/o/object-admin/v1.0/object-actions/";


const getObjectDefinitions = async (token = null) => {

    try {

        token = token || await getServerToken();

        let definitions = await lrRequest({
            method: 'GET',
            url:`${OBJECT_ENDPOINT}?page=0`
        },token);


        let result = definitions.data.items.filter(definition =>  !definition.system).map(definition => {
            return {
                id:definition.id,
                externalReferenceCode:definition.externalReferenceCode,
                name:definition.name,
                objectFields:definition.objectFields.map(field => {
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

const getObjectDefinitionActions = async (objectId,token = null) => {

    try {
        token = token || await getServerToken();

        let definitions = await lrRequest({
            method: 'GET',
            url:`${OBJECT_ENDPOINT}${objectId}/object-actions?fields=id,objectActionTriggerKey,externalReferenceCode`
        },token);

        return definitions;

    }catch(error) {

        throw new Error(`getConfigObject: ${error.message}`);

        return null;
    }

}

const getChatBotContextActions =  (objectId) =>{

    let actions = [
        {
            "objectActionExecutorKey": "function#liferay-instant-messaging-context-clause-add-etc-node",
            "errorMessage": {},
            "active": true,
            "description": "",
            "label": {
                "en_US": "Add Context Clause"
            },
            "externalReferenceCode": `ChatBot_${objectId}_onAfterAdd`,
            "system": false,
            "objectActionTriggerKey": "onAfterAdd",
            "name": "contextOnAdd",
            "parameters": {},
            "status": {
                "label_i18n": "Never Ran",
                "code": 0,
                "label": "never-ran"
            }
        },
        {
            "objectActionExecutorKey": "function#liferay-instant-messaging-context-clause-delete-etc-node",
            "errorMessage": {},
            "active": true,
            "description": "",
            "label": {
                "en_US": "Delete Context Clause"
            },
            "externalReferenceCode": `ChatBot_${objectId}_onAfterDelete`,
            "system": false,
            "objectActionTriggerKey": "onAfterDelete",
            "name": "contextOnDelete",
            "parameters": {},
            "status": {
                "label_i18n": "Never Ran",
                "code": 0,
                "label": "never-ran"
            }
        },
        {
            "objectActionExecutorKey": "function#liferay-instant-messaging-context-clause-add-etc-node",
            "errorMessage": {
                "en_US": "Error while reading file."
            },
            "active": true,
            "description": "",
            "label": {
                "en_US": "Force Context Update"
            },
            "externalReferenceCode":`ChatBot_${objectId}_standalone`,
            "system": false,
            "objectActionTriggerKey": "standalone",
            "name": "contextOnForce",
            "parameters": {},
            "status": {
                "label_i18n": "Never Ran",
                "code": 0,
                "label": "never-ran"
            }
        },
        {
            "objectActionExecutorKey": "function#liferay-instant-messaging-context-clause-update-etc-node",
            "errorMessage": {},
            "active": true,
            "description": "",
            "label": {
                "en_US": "Update Context"
            },
            "externalReferenceCode": `ChatBot_${objectId}_onAfterUpdate`,
            "system": false,
            "objectActionTriggerKey": "onAfterUpdate",
            "name": "contextOnUpdate",
            "parameters": {},
            "status": {
                "label_i18n": "Never Ran",
                "code": 0,
                "label": "never-ran"
            }
        }
    ]

    return  actions;

}

const postObjectActions = async (objectId, actions,token=null) =>{

    try {
        token = token || await getServerToken();

        let result = await lrRequest({
            method: 'POST',
            url:`${OBJECT_ENDPOINT}${objectId}/object-actions/batch`,
            data:JSON.stringify(actions),
        },token);
    }catch (error){
        throw new Error(`postObjectActions: ${error.message}`)
    }

}

const deleteObjectAction = async (objectActionId,token=null) =>{

    try {
        token = token || await getServerToken();

        let result = await lrRequest({
            method: 'DELETE',
            url:`${OBJECT_ACTION_ENDPOINT}${objectActionId}`
        },token);

    }catch (error){

        throw new Error(`deleteObjectAction: ${error.message}`)

    }

}

const configObjectActions = async (object) => {

    let objectId = object.values.contextObjectDefinitionID;

    await removeConfigObjectActions(object);

    let actions = getChatBotContextActions(objectId);

    await postObjectActions(objectId,actions);

}

const removeConfigObjectActions = async (object) => {

    let objectId = object.values.contextObjectDefinitionID;


    let actions = getChatBotContextActions(objectId);

    actions = actions.map(action=> action.externalReferenceCode);

    let currentActions = await getObjectDefinitionActions(objectId);

    let toBeDeletedAction = currentActions.data.items
        .filter(
            action=> actions.includes(action.externalReferenceCode)
        );

    for (let objectActionIndex = 0 ; objectActionIndex < toBeDeletedAction.length; objectActionIndex++) {

        await deleteObjectAction(toBeDeletedAction[objectActionIndex].id);
    }

}

module.exports = {
    getObjectDefinitions,
    configObjectActions,
    removeConfigObjectActions
}
