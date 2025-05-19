/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {lrRequest} = require('../../util/request');
const {getServerToken} = require('../../util/silent-authorization');

const OBJECT_ENDPOINT = '/o/c/streamhubconfigurations/';

const getConfigurationEntryByObjectDefinitionId = async (
	objectDefinitionId,
	token = null
) => {
	try {
		token = token || (await getServerToken());

		const result = await lrRequest(
			{
				method: 'get',
				url: `${OBJECT_ENDPOINT}?filter=objectDefinitionId eq '${objectDefinitionId}'`,
			},
			token
		);

		if (result.data && result.data.totalCount > 0) {
			return result.data.items[0];
		}
		else {
			return null;
		}
	}
	catch (error) {
		throw new Error(`postObjectActions: ${error.message}`);
	}
};

module.exports = {
	getConfigurationEntryByObjectDefinitionId,
};
