/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ApiHelpers} from '../helpers/ApiHelpers';

export const apiTest = test.extend<{api: ApiHelpers}>({
	api: async ({request}, use) => {
		const clientId = process.env.OAUTH_CLIENT_ID;
		const clientSecret = process.env.OAUTH_CLIENT_SECRET;

		const helpers = new ApiHelpers(
			request,
			clientId && clientSecret
				? {oAuth2: {clientId, clientSecret}}
				: {
						basicAuth: {
							password:
								process.env.LIFERAY_ADMIN_PASSWORD ?? 'test',
							user:
								process.env.LIFERAY_ADMIN_EMAIL ??
								'test@liferay.com',
						},
					}
		);

		await use(helpers);
	},
});
