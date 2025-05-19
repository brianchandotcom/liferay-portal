/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {Router} = require('express');
const fs = require('node:fs'); // eslint-disable-line
const path = require('path');

const {
	getChatBotByObjectDefinitionId,
} = require('../../services/liferay-interface/chatbots');
const {
	configObjectActions,
} = require('../../services/liferay-interface/objects');
const {logError, logInfo, logWarn} = require('../../util/log');

const router = Router();

const rootFolder = path.resolve(__dirname, '../', '../');

function checkAndUpdateFile(filePath, content) {
	if (fs.existsSync(filePath)) {
		fs.writeFileSync(filePath, content, 'utf8');
	}
	else {
		fs.writeFileSync(filePath, content, 'utf8');
	}
}

function stripHtmlTags(input) {
	return input.replace(/<[^>]*>/g, '');
}

function deleteFileIfExists(filePath) {
	fs.access(filePath, fs.constants.F_OK, (error) => {
		if (!error) {
			fs.unlink(filePath, (error) => {
				if (error) {
					logError('Error deleting file:', error);
				}
				else {
					logInfo('File deleted successfully');
				}
			});
		}
		else {
			logWarn('File does not exist');
		}
	});
}

router.post('/clause/add', async (request, response) => {
	try {
		const {objectDefinitionId, objectEntry} = request.body;

		const chatBot =
			await getChatBotByObjectDefinitionId(objectDefinitionId);

		const clause = objectEntry.values[chatBot.contextClauseField];

		const folderPath = path.join(
			rootFolder,
			'contexts',
			objectDefinitionId
		);

		if (
			!(
				fs.existsSync(folderPath) &&
				fs.lstatSync(folderPath).isDirectory()
			)
		) {
			fs.mkdirSync(folderPath, {recursive: true});
		}

		checkAndUpdateFile(
			path.resolve(folderPath, `${objectEntry.objectEntryId}.txt`),
			stripHtmlTags(clause)
		);

		response.status(200).json({});
	}
	catch (error) {
		logError(error.message);

		response.status(500).json({error: error.message});
	}
});

router.post('/clause/update', async (request, response) => {
	try {
		const {objectDefinitionId, objectEntry} = request.body;

		const chatBot =
			await getChatBotByObjectDefinitionId(objectDefinitionId);

		const clause = objectEntry.values[chatBot.contextClauseField];

		const folderPath = path.join(
			rootFolder,
			'contexts',
			objectDefinitionId
		);

		if (
			!(
				fs.existsSync(folderPath) &&
				fs.lstatSync(folderPath).isDirectory()
			)
		) {
			fs.mkdirSync(folderPath, {recursive: true});
		}

		checkAndUpdateFile(
			path.resolve(folderPath, `${objectEntry.objectEntryId}.txt`),
			stripHtmlTags(clause)
		);

		response.status(200).json({});
	}
	catch (error) {
		logError(error.message);

		response.status(500).json({error: error.message});
	}
});

router.post('/clause/delete', async (request, response) => {
	try {
		const {objectDefinitionId, objectEntry} = request.body;

		const folderPath = path.join(
			rootFolder,
			'contexts',
			objectDefinitionId
		);

		if (
			!(
				fs.existsSync(folderPath) &&
				fs.lstatSync(folderPath).isDirectory()
			)
		) {
			fs.mkdirSync(folderPath, {recursive: true});
		}

		deleteFileIfExists(
			path.resolve(folderPath, `${objectEntry.objectEntryId}.txt`)
		);

		response.status(200).json({});
	}
	catch (error) {
		logError(error.message);

		response.status(500).json({error: error.message});
	}
});

router.post('/config', async (request, response) => {
	try {
		await configObjectActions(request.body.objectEntry);

		response.status(200).json({});
	}
	catch (error) {
		logError(error.message);

		response.status(500).json({error: error.message});
	}
});

router.post('/config/delete', async (request, response) => {
	try {
		await configObjectActions(request.body.objectEntry);

		response.status(200).json({});
	}
	catch (error) {
		logError(error.message);

		response.status(500).json({error: error.message});
	}
});

module.exports = router;
