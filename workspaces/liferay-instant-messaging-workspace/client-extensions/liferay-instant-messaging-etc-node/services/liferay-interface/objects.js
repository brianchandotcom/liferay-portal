/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {lrRequest} = require('../../util/request');
const {getServerToken} = require('../../util/silent-authorization');

const OBJECT_ENDPOINT = '/o/object-admin/v1.0/object-definitions/';

const OBJECT_ACTION_ENDPOINT = '/o/object-admin/v1.0/object-actions/';

const getObjectDefinitions = async (token = null) => {
	try {
		token = token || (await getServerToken());

		const definitions = await lrRequest(
			{
				method: 'GET',
				url: `${OBJECT_ENDPOINT}?page=0`,
			},
			token
		);

		const result = definitions.data.items
			.filter((definition) => !definition.system)
			.map((definition) => {
				return {
					externalReferenceCode: definition.externalReferenceCode,
					id: definition.id,
					name: definition.name,
					objectFields: definition.objectFields.map((field) => {
						return {
							businessType: field.businessType,
							externalReferenceCode: field.externalReferenceCode,
							id: field.id,
							name: field.name,
						};
					}),
				};
			});

		return result;
	}
	catch (error) {
		throw new Error(`getConfigObject: ${error.message}`);
	}
};

const getObjectDefinitionActions = async (objectId, token = null) => {
	try {
		token = token || (await getServerToken());

		const definitions = await lrRequest(
			{
				method: 'GET',
				url: `${OBJECT_ENDPOINT}${objectId}/object-actions?fields=id,objectActionTriggerKey,externalReferenceCode`,
			},
			token
		);

		return definitions;
	}
	catch (error) {
		throw new Error(`getConfigObject: ${error.message}`);
	}
};

const getChatBotContextActions = (objectId) => {
	const actions = [
		{
			active: true,
			description: '',
			errorMessage: {},
			externalReferenceCode: `ChatBot_${objectId}_onAfterAdd`,
			label: {en_US: 'Add Context Clause'},
			name: 'contextOnAdd',
			objectActionExecutorKey:
				'function#liferay-instant-messaging-context-clause-add-etc-node',
			objectActionTriggerKey: 'onAfterAdd',
			parameters: {},
			status: {code: 0, label: 'never-ran', label_i18n: 'Never Ran'},
			system: false,
		},
		{
			active: true,
			description: '',
			errorMessage: {},
			externalReferenceCode: `ChatBot_${objectId}_onAfterDelete`,
			label: {en_US: 'Delete Context Clause'},
			name: 'contextOnDelete',
			objectActionExecutorKey:
				'function#liferay-instant-messaging-context-clause-delete-etc-node',
			objectActionTriggerKey: 'onAfterDelete',
			parameters: {},
			status: {code: 0, label: 'never-ran', label_i18n: 'Never Ran'},
			system: false,
		},
		{
			active: true,
			description: '',
			errorMessage: {en_US: 'Error while reading file.'},
			externalReferenceCode: `ChatBot_${objectId}_standalone`,
			label: {en_US: 'Force Context Update'},
			name: 'contextOnForce',
			objectActionExecutorKey:
				'function#liferay-instant-messaging-context-clause-add-etc-node',
			objectActionTriggerKey: 'standalone',
			parameters: {},
			status: {code: 0, label: 'never-ran', label_i18n: 'Never Ran'},
			system: false,
		},
		{
			active: true,
			description: '',
			errorMessage: {},
			externalReferenceCode: `ChatBot_${objectId}_onAfterUpdate`,
			label: {en_US: 'Update Context'},
			name: 'contextOnUpdate',
			objectActionExecutorKey:
				'function#liferay-instant-messaging-context-clause-update-etc-node',
			objectActionTriggerKey: 'onAfterUpdate',
			parameters: {},
			status: {code: 0, label: 'never-ran', label_i18n: 'Never Ran'},
			system: false,
		},
	];

	return actions;
};

const postObjectActions = async (objectId, actions, token = null) => {
	try {
		token = token || (await getServerToken());

		const result = await lrRequest(
			{
				data: JSON.stringify(actions),
				method: 'POST',
				url: `${OBJECT_ENDPOINT}${objectId}/object-actions/batch`,
			},
			token
		);

		return result;
	}
	catch (error) {
		throw new Error(`postObjectActions: ${error.message}`);
	}
};

const deleteObjectAction = async (objectActionId, token = null) => {
	try {
		token = token || (await getServerToken());

		const result = await lrRequest(
			{
				method: 'DELETE',
				url: `${OBJECT_ACTION_ENDPOINT}${objectActionId}`,
			},
			token
		);

		return result;
	}
	catch (error) {
		throw new Error(`deleteObjectAction: ${error.message}`);
	}
};

const configObjectActions = async (object) => {
	const objectId = object.values.contextObjectDefinitionID;

	await removeConfigObjectActions(object);

	const actions = getChatBotContextActions(objectId);

	await postObjectActions(objectId, actions);
};

const removeConfigObjectActions = async (object) => {
	const objectId = object.values.contextObjectDefinitionID;

	let actions = getChatBotContextActions(objectId);

	actions = actions.map((action) => action.externalReferenceCode);

	const currentActions = await getObjectDefinitionActions(objectId);

	const toBeDeletedAction = currentActions.data.items.filter((action) =>
		actions.includes(action.externalReferenceCode)
	);

	for (
		let objectActionIndex = 0;
		objectActionIndex < toBeDeletedAction.length;
		objectActionIndex++
	) {
		await deleteObjectAction(toBeDeletedAction[objectActionIndex].id);
	}
};

module.exports = {
	configObjectActions,
	getObjectDefinitions,
	removeConfigObjectActions,
};
