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

const OBJECT_ENDPOINT = '/o/c/chatbots/';

const getChatBots = async (pageSize = 0, page = 0, token = null) => {
	token = token || (await getServerToken());

	const cacheKey = `bots_${pageSize}_${page}`;

	const endPoint = `${OBJECT_ENDPOINT}?page=${page}&pageSize=${pageSize}`;

	if (hasCacheKey(cacheKey)) {
		return getCacheEntry(cacheKey);
	}
	else {
		const chatbots = await lrRequest(
			{
				method: 'GET',
				url: endPoint,
			},
			token
		);

		const result = chatbots.data.items.map((item) => {
			return {
				emailAddress: 'chatbot@liferay.com',
				familyName: item.familyName,
				givenName: item.givenName,
				name: item.name,
				roles: [],
				type: 'bot',
				userId: `bot_${item.contextObjectDefinitionID}`,
			};
		});

		putCacheEntry(cacheKey, result, 500);

		setCacheEntryCallback(cacheKey, async () => {
			await getChatBots();
		});

		return result;
	}
};

const getChatBotByObjectDefinitionId = async (
	objectDefinitionId,
	token = null
) => {
	token = token || (await getServerToken());

	const cacheKey = `bots_${objectDefinitionId}`;

	const endPoint = `${OBJECT_ENDPOINT}?fields=contextObjectDefinitionID,name,contextClauseField&filter=contextObjectDefinitionID eq '${objectDefinitionId}'`;

	if (hasCacheKey(cacheKey)) {
		return getCacheEntry(cacheKey);
	}
	else {
		const chatbots = await lrRequest(
			{
				method: 'GET',
				url: endPoint,
			},
			token
		);

		if (chatbots.data.items.length) {
			const result = chatbots.data.items[0];

			putCacheEntry(cacheKey, result, 10 * 60);

			return result;
		}
		else {
			return null;
		}
	}
};

module.exports = {
	getChatBotByObjectDefinitionId,
	getChatBots,
};
