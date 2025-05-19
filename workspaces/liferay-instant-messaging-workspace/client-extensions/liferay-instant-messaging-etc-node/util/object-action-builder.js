const {ActionsTypes} = require("./types");

const getActionTitle = (actionType) => {

    switch (actionType) {
        case ActionsTypes.ON_AFTER_REMOVE:
            return "Stream Events - On Remove";
        case ActionsTypes.ON_AFTER_UPDATE:
            return "Stream Events - On Update";
        case ActionsTypes.ON_AFTER_ADD:
            return "Stream Events - On Add";
        case ActionsTypes.STAND_ALONE:
            return "Stream Events - Standalone";
    }
}

const getActionName = (actionType,objectId) => {

    switch (actionType) {
        case ActionsTypes.ON_AFTER_REMOVE:
            return `stream${objectId}OnRemove`;
        case ActionsTypes.ON_AFTER_UPDATE:
            return `stream${objectId}OnUpdate`;
        case ActionsTypes.ON_AFTER_ADD:
            return `stream${objectId}OnAdd`;
        case ActionsTypes.STAND_ALONE:
            return `stream${objectId}Standalone`;
    }
}

const getAction = (actionType) => {
    switch (actionType) {
        case ActionsTypes.ON_AFTER_REMOVE:
            return `liferay-stream-hub-event-delete-etc-node`;
        case ActionsTypes.ON_AFTER_UPDATE:
            return `liferay-stream-hub-event-update-etc-node`;
        case ActionsTypes.ON_AFTER_ADD:
            return `liferay-stream-hub-event-add-etc-node`;
        case ActionsTypes.STAND_ALONE:
            return `liferay-stream-hub-event-standalone-etc-node`;
    }
}

const buildAction = (objectId,actionType,) => {

    return {
        "objectActionExecutorKey": `function#${getAction(actionType)}`,
        "errorMessage": {
            en_US: `Error while executing Event Streaming for :${actionType}`
        },
        "active": true,
        "description": "",
        "label": {
            "en_US": getActionTitle(actionType),
        },
        "externalReferenceCode": `STREAM_${objectId}_${actionType}`,
        "system": false,
        "objectActionTriggerKey": actionType,
        "name": getActionName(actionType,objectId),
        "parameters": {},
        "status": {
            "label_i18n": "Never Ran",
            "code": 0,
            "label": "never-ran"
        }
    }

}

module.exports = {
    buildAction
}
