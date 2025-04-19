/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import axios from 'axios';

export function request(config) {
	return new Promise((resolve, reject) => {
		axios
			.request(
				{
					headers: {
						'Accept-Language':
							Liferay.ThemeDisplay.getLanguageId().split('_')[0],
						'scope-id': Liferay.ThemeDisplay.getScopeGroupId(),
						'x-csrf-token': Liferay.authToken,
					},
					method: 'get',
					...config,
				},
				(error) => {
					reject({error, message: error || ''});
				}
			)
			.then((response) => {
				resolve(response.data);
			})
			.catch((error) => {
				reject({error, message: error || ''});
			});
	});
}
