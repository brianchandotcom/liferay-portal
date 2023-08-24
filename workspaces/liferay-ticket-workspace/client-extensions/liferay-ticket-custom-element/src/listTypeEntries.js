/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import axios from 'axios';

async function fetchListTypeEntries(externalReferenceCode) {
	const {data} = await axios.get(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/${externalReferenceCode}/list-type-entries`,
		{
			headers: {
				"accept": "application/json",
				"x-csrf-token": Liferay.authToken
			}
		}
	);

	return data?.items.map((item) => ({
		key: item.key,
		name: item.name,
	}));
}

export const J3Y7_PRIORITIES = 'J3Y7_PRIORITIES';
export const J3Y7_RESOLUTIONS = 'J3Y7_RESOLUTIONS';
export const J3Y7_REGIONS = 'J3Y7_REGIONS';
export const J3Y7_STATUSES = 'J3Y7_STATUSES';
export const J3Y7_TYPES = 'J3Y7_TYPES';

const listTypeDefinitionERCs = [
	J3Y7_PRIORITIES,
	J3Y7_RESOLUTIONS,
	J3Y7_REGIONS,
	J3Y7_STATUSES,
	J3Y7_TYPES,
];

export async function fetchListTypeDefinitions() {
	const listTypeDefinitions = {};
	for (const listTypeDefinitionERC of listTypeDefinitionERCs) {
		listTypeDefinitions[listTypeDefinitionERC] = await fetchListTypeEntries(
			listTypeDefinitionERC
		);
	}

	return listTypeDefinitions;
}
