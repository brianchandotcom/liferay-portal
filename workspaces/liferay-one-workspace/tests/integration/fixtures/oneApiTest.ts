/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {OneApiHelpers} from '../helpers/OneApiHelpers';

export const oneApiTest = test.extend<{oneApi: OneApiHelpers}>({
	oneApi: async ({request}, use) => {
		const helpers = new OneApiHelpers(request, {
			basicAuth: {
				password: process.env.ONE_ADMIN_PASSWORD ?? 'test',
				user: process.env.ONE_ADMIN_EMAIL ?? 'test@liferay.com',
			},
		});

		await use(helpers);
	},
});
