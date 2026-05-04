/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect} from '@playwright/test';

import {onePagesTest as test} from '../fixtures/onePagesTest';
import {oneLogin, oneLogout} from '../utils/login';

test.afterEach(async ({page}) => {
	await oneLogout(page);
});

test.beforeEach(async ({page}) => {
	await oneLogin(page);
});

test.describe('One Site Smoke', () => {
	test('home page loads when authenticated', async ({homePage}) => {
		await homePage.goto();

		await expect(homePage.heading).toBeVisible();
	});
});
