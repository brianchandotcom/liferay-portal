/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {config} = require('../../utils/constants');
const {request} = require('../../utils/request');

const getEntries = (pageIndex, pageSize) => {
	return request({
		method: 'GET',
		url: `${config.chatBotEndPoint}?page=${pageIndex}&pageSize=${pageSize}`,
	});
};

const deleteEntry = (entryId) => {
	return request({
		method: 'DELETE',
		url: `${config.chatBotEndPoint}${entryId}`,
	});
};

module.exports = {
	deleteEntry,
	getEntries,
};
