/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {LoginPage} from '../pages/LoginPage';
import {DEFAULT_ADMIN_EMAIL, DEFAULT_ADMIN_PASSWORD} from './constants';

export async function liferayLogin(
	page: Page,
	email: string = DEFAULT_ADMIN_EMAIL,
	password: string = DEFAULT_ADMIN_PASSWORD
) {
	const loginPage = new LoginPage(page);

	await loginPage.goto();
	await loginPage.emailField.fill(email);
	await loginPage.passwordField.fill(password);
	await loginPage.signInButton.click();

	await page.waitForURL((url) => !url.pathname.includes('login'));
}

export async function liferayLogout(page: Page) {
	await page.goto('/c/portal/logout');
}
