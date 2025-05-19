const express = require('express');
const {Router} = require("express");
const {logError} = require("../../util/log");
const {getConfigurationEntryByObjectDefinitionId} = require("../../services/liferay-interface/stream-hub-configuration");
const {
    getObjectActions,
    postObjectActions,
    deleteObjectActions
} = require("../../services/liferay-interface/object-admin");
const {buildAction} = require("../../util/object-action-builder");
const {getServerToken} = require("../../util/silent-authorization");
const {getContactsStatus, getContactsWithStatus} = require("../../interfaces/contacts");
const {sendMessage} = require("../../sockets-server/websocketServer");
const {MessageType} = require("../../sockets-server/types");
const {ActionsTypes} = require("../../util/types");
const {getSystemObjectEntryModelData, getFilteredObject} = require("../../util/object-actions-util");
const path = require("path");
const fs = require("node:fs");
const {getChatBotByObjectDefinitionId} = require("../../services/liferay-interface/chatbots");
const {configObjectActions} = require("../../services/liferay-interface/objects");

const router = Router();

const rootFolder = path.resolve(__dirname, '../','../');

function checkAndUpdateFile(filePath, content) {

    if (fs.existsSync(filePath)) {
        fs.writeFileSync(filePath, content, 'utf8');
    } else {
        fs.writeFileSync(filePath, content, 'utf8');
    }
}

function stripHtmlTags(input) {
    return input.replace(/<[^>]*>/g, '');
}

function deleteFileIfExists(filePath) {
    fs.access(filePath, fs.constants.F_OK, (err) => {
        if (!err) {
            fs.unlink(filePath, (err) => {
                if (err) {
                    logError('Error deleting file:', err);
                } else {
                    console.info('File deleted successfully');
                }
            });
        } else {
            console.warn('File does not exist');
        }
    });
}

router.post('/clause/add', async (request, response) => {

    try {

        const {objectEntry, objectDefinitionId} = request.body;

        let chatBot = await getChatBotByObjectDefinitionId(objectDefinitionId);

        const clause = objectEntry.values[chatBot.contextClauseField];

        const folderPath = path.join(rootFolder, 'contexts',objectDefinitionId);

        if (!(fs.existsSync(folderPath) && fs.lstatSync(folderPath).isDirectory())) {

            fs.mkdirSync(folderPath, { recursive: true });

        }

        checkAndUpdateFile(path.resolve(folderPath,`${objectEntry.objectEntryId}.txt`),stripHtmlTags(clause));

        response.status(200).json({});

    } catch (error) {

        logError(error.message);

        response.status(500).json({error: error.message});
    }


});

router.post('/clause/update', async (request, response) => {

    try {

        const {objectActionTriggerKey, objectEntry, objectDefinitionId} = request.body;

        let chatBot = await getChatBotByObjectDefinitionId(objectDefinitionId);

        const clause = objectEntry.values[chatBot.contextClauseField];

        const folderPath = path.join(rootFolder, 'contexts',objectDefinitionId);

        if (!(fs.existsSync(folderPath) && fs.lstatSync(folderPath).isDirectory())) {

            fs.mkdirSync(folderPath, { recursive: true });

        }

        checkAndUpdateFile(path.resolve(folderPath,`${objectEntry.objectEntryId}.txt`),stripHtmlTags(clause));

        response.status(200).json({});

    } catch (error) {

        logError(error.message);

        response.status(500).json({error: error.message});
    }


});

router.post('/clause/delete', async (request, response) => {

    try {

        const {objectActionTriggerKey, objectEntry, objectDefinitionId} = request.body;

        const {clause} = objectEntry.values;

        const folderPath = path.join(rootFolder, 'contexts',objectDefinitionId);

        if (!(fs.existsSync(folderPath) && fs.lstatSync(folderPath).isDirectory())) {

            fs.mkdirSync(folderPath, { recursive: true });

        }

        deleteFileIfExists(path.resolve(folderPath,`${objectEntry.objectEntryId}.txt`));

        response.status(200).json({});

    } catch (error) {

        logError(error.message);

        response.status(500).json({error: error.message});
    }


});

router.post('/config', async (request, response) => {

    try {
        await configObjectActions(request.body.objectEntry);

        response.status(200).json({});

    }catch (error) {

        logError(error.message);

        response.status(500).json({error: error.message});
    }



});

router.post('/config/delete', async (request, response) => {

    try {
        await configObjectActions(request.body.objectEntry);

        response.status(200).json({});

    }catch (error) {

        logError(error.message);

        response.status(500).json({error: error.message});
    }



});


module.exports = router;
