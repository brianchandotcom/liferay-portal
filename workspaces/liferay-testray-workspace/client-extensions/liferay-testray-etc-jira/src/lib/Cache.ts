/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {JIRA_AUTH_CLOUD_ID} from '../utils/env';
import logger from './Logger';

class Cache {
	public cache = new Map<string, any>();
	private static instance: Cache;
	static KEYS = {
		JIRA_APP_ID_KEY: 'JIRA_APP_ID_KEY',
	};

	private constructor() {
		if (JIRA_AUTH_CLOUD_ID) {
			this.cache.set(Cache.KEYS.JIRA_APP_ID_KEY, JIRA_AUTH_CLOUD_ID);
		}
	}

	public static getInstance(): Cache {
		if (!Cache.instance) {
			Cache.instance = new Cache();
		}

		return Cache.instance;
	}

	public get<T = any>(key: string): T | undefined {
		const cachedValue = this.cache.get(key);

		logger.debug(`[CACHE]: Get: ${key}, StoredValue: ${cachedValue}}`);

		return cachedValue;
	}

	public set(key: string, value: unknown) {
		logger.debug(`[CACHE]: Set: ${key}, StoredValue: ${value}}`);

		this.cache.set(key, value);
	}
}

export default Cache;
