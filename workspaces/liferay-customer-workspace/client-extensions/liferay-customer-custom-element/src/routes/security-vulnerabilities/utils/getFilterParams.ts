/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FILTER_MAP} from './constants/filterOptions';

export function getFilterParams(params: string): string {
	const splittedParams = params.split('&');

	const mappedParams = splittedParams.map((param) => {
		const [key, value] = param.split('=');

		const mappedKey = FILTER_MAP[key] || key;

		return `${mappedKey}=${value}`;
	});

	return mappedParams.join('&');
}
