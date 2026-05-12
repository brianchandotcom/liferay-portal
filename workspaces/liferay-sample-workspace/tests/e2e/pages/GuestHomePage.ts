/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {BasePage} from './BasePage';

export class GuestHomePage extends BasePage {
	readonly heading: Locator;

	constructor(page: Page) {
		super(page);

		this.heading = page.getByRole('heading', {level: 1, name: /.+/});
	}

	async goto() {
		await this.page.goto('/web/guest/home');
	}
}
