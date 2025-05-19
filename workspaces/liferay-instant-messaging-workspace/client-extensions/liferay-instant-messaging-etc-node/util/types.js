/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const ActionsTypes = {
	ON_AFTER_ADD: 'onAfterAdd',
	ON_AFTER_REMOVE: 'onAfterDelete',
	ON_AFTER_UPDATE: 'onAfterUpdate',
	STAND_ALONE: 'standalone',
};

const ChatMessageType = {
	FILE: 'File',
	TEXT: 'Text',
};

module.exports = {
	ActionsTypes,
	ChatMessageType,
};
