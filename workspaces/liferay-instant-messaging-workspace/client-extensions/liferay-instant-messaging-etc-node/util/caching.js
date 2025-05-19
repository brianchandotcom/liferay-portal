/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const NodeCache = require('node-cache');
const cache = new NodeCache({
	checkperiod: 5,
	stdTTL: 100,
});
const TTL = 120 * 60;
const callbackMap = new Map();

cache.on('expired', (expiredKey) => {
	if (callbackMap.has(expiredKey)) {
		callbackMap.get(expiredKey)();
	}
});

const putCacheEntry = (key, data, duration = TTL) => {
	cache.set(key, data, duration);
};

const getCacheEntry = (key) => {
	return cache.get(key);
};

const hasCacheKey = (key) => {
	return cache.has(key);
};
const deleteCacheEntry = (key) => {
	return cache.del(key);
};

const setCacheEntryCallback = (key, callback) => {
	callbackMap.set(key, callback);
};

const clearCacheEntries = () => {
	cache.close();
};

module.exports = {
	clearCacheEntries,
	deleteCacheEntry,
	getCacheEntry,
	hasCacheKey,
	putCacheEntry,
	setCacheEntryCallback,
};
