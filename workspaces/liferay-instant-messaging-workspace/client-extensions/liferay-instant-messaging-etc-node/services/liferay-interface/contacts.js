/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {
	getCacheEntry,
	hasCacheKey,
	putCacheEntry,
	setCacheEntryCallback,
} = require('../../util/caching');
const {lrRequest} = require('../../util/request');
const {getServerToken} = require('../../util/silent-authorization');

const getLiferayContacts = async (pageSize, page, token = null) => {
	token = token || (await getServerToken());

	const cacheKey = `Contacts_${pageSize}_${page}`;

	const endPoint = `/o/headless-admin-user/v1.0/user-accounts?page=${page}&pageSize=${pageSize}`;

	if (hasCacheKey(cacheKey)) {
		return getCacheEntry(cacheKey);
	}
	else {
		const contacts = await lrRequest(
			{
				method: 'GET',
				url: endPoint,
			},
			token
		);

		const result = contacts.data.items.map((item) => {
			return {
				emailAddress: item.emailAddress,
				familyName: item.familyName,
				givenName: item.givenName,
				name: item.name,
				roles: item.roleBriefs,
				userId: item.id,
			};
		});

		putCacheEntry(cacheKey, result, 0.2 * 60);

		setCacheEntryCallback(cacheKey, async () => {
			getLiferayContacts(pageSize, page);
		});

		return result;
	}
};

module.exports = {
	getLiferayContacts,
};
