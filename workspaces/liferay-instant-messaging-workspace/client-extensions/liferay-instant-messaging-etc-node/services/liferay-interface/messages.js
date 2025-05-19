/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {lrRequest} = require('../../util/request');
const {getServerToken} = require('../../util/silent-authorization');

const OBJECT_ENDPOINT = '/o/c/messages/';

const post = async (message, token = null) => {
	try {
		token = token || (await getServerToken());

		const result = await lrRequest(
			{
				data: JSON.stringify(message),
				method: 'POST',
				url: `${OBJECT_ENDPOINT}`,
			},
			token
		);

		return result;
	}
	catch (error) {
		throw new Error(`postIMMessage: ${error.message}`);
	}
};

const get = async (from, to, page = 0, pageSize = 0, token = null) => {
	try {
		token = token || (await getServerToken());

		const result = await lrRequest(
			{
				method: 'GET',
				url: `${OBJECT_ENDPOINT}?page=${page}&pageSize=${pageSize}&filter=((from eq '${from}' and to eq '${to}') or (to eq '${from}' and from eq '${to}'))&sort=date:desc`,
			},
			token
		);

		return result;
	}
	catch (error) {
		throw new Error(`postIMMessage: ${error.message}`);
	}
};

module.exports = {
	get,
	post,
};
