/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {BasePage} from './BasePage';

export class LoginPage extends BasePage {
	readonly emailField: Locator;
	readonly passwordField: Locator;
	readonly signInButton: Locator;

	constructor(page: Page) {
		super(page);

		this.emailField = page.getByLabel('Email Address');
		this.passwordField = page.getByLabel('Password');
		this.signInButton = page.getByRole('button', {name: 'Sign In'});
	}

	async goto() {
		await this.page.goto('/c/portal/login');
	}
}
