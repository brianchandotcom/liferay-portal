/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from './liferay';

export function request(path: string, method: string = 'GET', body?: any) {
	return fetch(path, {
		body,
		headers: {
			'accept': 'application/json',
			'content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method,
	});
}
