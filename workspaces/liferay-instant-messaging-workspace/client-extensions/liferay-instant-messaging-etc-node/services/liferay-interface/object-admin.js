/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {lrRequest} = require('../../util/request');
const {getServerToken} = require('../../util/silent-authorization');

const OBJECT_ENDPOINT = '/o/object-admin/v1.0/object-definitions/';

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

const deleteObjectActions = async (actions, token = null) => {
	try {
		token = token || (await getServerToken());

		const result = await lrRequest(
			{
				data: JSON.stringify(actions),
				method: 'DELETE',
				url: `/o/object-admin/v1.0/object-actions/batch`,
			},
			token
		);

		return result;
	}
	catch (error) {
		throw new Error(`postObjectActions: ${error.message}`);
	}
};

const getObjectActions = async (objectId, token = null) => {
	try {
		token = token || (await getServerToken());

		const result = await lrRequest(
			{
				method: 'GET',
				url: `${OBJECT_ENDPOINT}${objectId}/object-actions/`,
			},
			token
		);

		if (result.data && result.data.totalCount > 0) {
			return result.data.items;
		}
		else {
			return null;
		}
	}
	catch (error) {
		throw new Error(`postObjectActions: ${error.message}`);
	}
};

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
			.filter(
				(definition) =>
					definition.storageType === 'default' && !definition.system
			)
			.map((definition) => {
				return {
					externalReferenceCode: definition.externalReferenceCode,
					id: definition.id,
					name: definition.name,
					objectFields: definition.objectFields
						.filter((field) => !field.system)
						.map((field) => {
							return {
								businessType: field.businessType,
								externalReferenceCode:
									field.externalReferenceCode,
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

module.exports = {
	deleteObjectActions,
	getObjectActions,
	getObjectDefinitions,
	postObjectActions,
};
