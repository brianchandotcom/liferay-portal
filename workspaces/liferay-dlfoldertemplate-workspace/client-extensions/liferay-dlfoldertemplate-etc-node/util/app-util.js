export function GenerateObjectSchema(mappingData, ObjectName) {
    let objectSchema = {
        "enableComments": false,
        "enableCategorization": true,
        "enableLocalization": false,
        "accountEntryRestricted": false,
        "externalReferenceCode": `data_import_erc_${ObjectName.replaceAll(' ', '')}`,
        "objectFields": [
            {
                "indexed": false,
                "localized": false,
                "objectFieldSettings": [],
                "readOnly": "true",
                "DBType": "String",
                "label": {
                    "en_US": "Author"
                },
                "type": "String",
                "required": false,
                "indexedAsKeyword": false,
                "system": true,
                "indexedLanguageId": "",
                "name": "creator",
                "state": false,
                "businessType": "Text",
                "readOnlyConditionExpression": ""
            },
            {
                "indexed": false,
                "localized": false,
                "objectFieldSettings": [],
                "readOnly": "true",
                "DBType": "Date",
                "label": {
                    "en_US": "Create Date"
                },
                "type": "Date",
                "required": false,
                "indexedAsKeyword": false,
                "system": true,
                "indexedLanguageId": "",
                "name": "createDate",
                "state": false,
                "businessType": "Date",
                "readOnlyConditionExpression": ""
            },
            {
                "indexed": false,
                "localized": false,
                "objectFieldSettings": [],
                "readOnly": "false",
                "DBType": "String",
                "label": {
                    "en_US": "External Reference Code"
                },
                "type": "String",
                "required": false,
                "indexedAsKeyword": false,
                "system": true,
                "indexedLanguageId": "",
                "name": "externalReferenceCode",
                "state": false,
                "businessType": "Text",
                "readOnlyConditionExpression": ""
            },
            {
                "indexed": true,
                "localized": false,
                "objectFieldSettings": [],
                "readOnly": "true",
                "DBType": "Long",
                "label": {
                    "en_US": "ID"
                },
                "type": "Long",
                "required": false,
                "indexedAsKeyword": true,
                "system": true,
                "indexedLanguageId": "",
                "name": "id",
                "state": false,
                "businessType": "LongInteger",
                "readOnlyConditionExpression": ""
            },
            {
                "indexed": false,
                "localized": false,
                "objectFieldSettings": [],
                "readOnly": "true",
                "DBType": "Date",
                "label": {
                    "en_US": "Modified Date"
                },
                "type": "Date",
                "required": false,
                "indexedAsKeyword": false,
                "system": true,
                "indexedLanguageId": "",
                "name": "modifiedDate",
                "state": false,
                "businessType": "Date",
                "readOnlyConditionExpression": ""
            },
            {
                "indexed": false,
                "localized": false,
                "objectFieldSettings": [],
                "readOnly": "true",
                "DBType": "String",
                "label": {
                    "en_US": "Status"
                },
                "type": "String",
                "required": false,
                "indexedAsKeyword": false,
                "system": true,
                "indexedLanguageId": "",
                "name": "status",
                "state": false,
                "businessType": "Text",
                "readOnlyConditionExpression": ""
            }
        ],
        "scope": "company",
        "portlet": true,
        "modifiable": true,
        "parameterRequired": false,
        "enableObjectEntryHistory": false,
        "active": true,
        "label": {
            "en_US": ObjectName
        },
        "panelCategoryKey": "applications_menu.applications.content",
        "pluralLabel": {
            "en_US": ObjectName
        },
        "system": false,
        "name": `DataImport${ObjectName.replaceAll(' ', '')}`
    };
    for (let i = 0; i < mappingData.length; i++) {
        let fieldName =  mappingData[i].fieldId;
        let fieldTemplate = {
            "localized": false,
            "objectFieldSettings": mappingData[i].value.objectFieldSettings || [],
            "DBType": mappingData[i].value.dbType,
            "label": {
                "en_US": mappingData[i].fieldId
            },
            "type": mappingData[i].value.type,
            "name": keepLettersAndDigits(`cF${fieldName}`),
            "state": false,
            "businessType": mappingData[i].value.businessType,
            "readOnlyConditionExpression": ""
        };
        objectSchema.objectFields.push(fieldTemplate);
    }
    return objectSchema;
}
export function GenerateObjectEntriesBatch(ObjectName,Data)
{
    let template = {
        "configuration": {
            "className": "com.liferay.object.rest.dto.v1_0.ObjectEntry",
            "companyId": 0,
            "parameters": {
                "containsHeaders": "true",
                "createStrategy": "UPSERT",
                "onErrorFail": "false",
                "taskItemDelegateName": ObjectName,
                "updateStrategy": "UPDATE"
            },
            "taskItemDelegateName": ObjectName,
            "userId": 0,
            "version": "v1.0"
        },
        "items": Data
    };
    return template;
}

export function keepLettersAndDigits(inputString) {
    return inputString.replace(/[^a-zA-Z0-9]/g, '');
}
