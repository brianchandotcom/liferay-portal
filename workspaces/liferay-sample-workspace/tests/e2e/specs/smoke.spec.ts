/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect} from '@playwright/test';

import {pagesTest as test} from '../fixtures/pagesTest';
import {liferayLogin, liferayLogout} from '../utils/login';

test.afterEach(async ({page}) => {
	await liferayLogout(page);
});

test.beforeEach(async ({page}) => {
	await liferayLogin(page);
});

test.describe('Smoke', () => {
	test('guest home page loads when authenticated', async ({
		guestHomePage,
	}) => {
		await guestHomePage.goto();

		await expect(guestHomePage.heading).toBeVisible();
	});
});
