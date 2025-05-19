/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {ActionsTypes} = require('./types');

const getActionTitle = (actionType) => {
	switch (actionType) {
		case ActionsTypes.ON_AFTER_REMOVE:
			return 'Stream Events - On Remove';
		case ActionsTypes.ON_AFTER_UPDATE:
			return 'Stream Events - On Update';
		case ActionsTypes.ON_AFTER_ADD:
			return 'Stream Events - On Add';
		case ActionsTypes.STAND_ALONE:
			return 'Stream Events - Standalone';
		default:
			return null;
	}
};

const getActionName = (actionType, objectId) => {
	switch (actionType) {
		case ActionsTypes.ON_AFTER_REMOVE:
			return `stream${objectId}OnRemove`;
		case ActionsTypes.ON_AFTER_UPDATE:
			return `stream${objectId}OnUpdate`;
		case ActionsTypes.ON_AFTER_ADD:
			return `stream${objectId}OnAdd`;
		case ActionsTypes.STAND_ALONE:
			return `stream${objectId}Standalone`;
		default:
			return null;
	}
};

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
		default:
			return null;
	}
};

const buildAction = (objectId, actionType) => {
	return {
		active: true,
		description: '',
		errorMessage: {
			en_US: `Error while executing Event Streaming for :${actionType}`,
		},
		externalReferenceCode: `STREAM_${objectId}_${actionType}`,
		label: {en_US: getActionTitle(actionType)},
		name: getActionName(actionType, objectId),
		objectActionExecutorKey: `function#${getAction(actionType)}`,
		objectActionTriggerKey: actionType,
		parameters: {},
		status: {code: 0, label: 'never-ran', label_i18n: 'Never Ran'},
		system: false,
	};
};

module.exports = {
	buildAction,
};
