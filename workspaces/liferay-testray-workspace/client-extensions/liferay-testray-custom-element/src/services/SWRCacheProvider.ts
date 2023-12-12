/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import TestrayStorage, {STORAGE_KEYS} from '~/core/Storage';
import {CONSENT_TYPE} from '~/util/enum';

const testrayStorage = TestrayStorage.getInstance().getStorage('temporary');

/**
 * @description When initializing, we restore the data from `STORAGE` into a map.
 * Before unloading the app, we write back all the data into `STORAGE`.
 * We still use the map for write & read for performance.
 */

const SWRCacheProvider = (): Map<any, any> => {
	const cacheMap = new Map(
		JSON.parse(
			testrayStorage.getItem(
				STORAGE_KEYS.SWR_CACHE,
				CONSENT_TYPE.PERFORMANCE
			) || '[]'
		)
	);

	window.addEventListener('beforeunload', () => {
		const appCache = JSON.stringify(Array.from(cacheMap.entries()));

		testrayStorage.setItem(
			STORAGE_KEYS.SWR_CACHE,
			appCache,
			CONSENT_TYPE.PERFORMANCE
		);
	});

	return cacheMap;
};

export default SWRCacheProvider;
