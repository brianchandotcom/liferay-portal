/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {defineConfig, devices} from '@playwright/test';
import * as dotenv from 'dotenv';

dotenv.config();
dotenv.config({override: true, path: '.env.local'});

const portalURL = process.env.PORTAL_URL || 'http://localhost:8080';

export default defineConfig({
	expect: {
		timeout: 15 * 1000,
	},
	forbidOnly: !!process.env.CI,
	globalSetup: './globalSetup.ts',
	reporter: [['html', {open: 'never'}]],
	retries: process.env.CI ? 1 : 0,
	testDir: './tests',
	timeout: 90 * 1000,
	use: {
		...devices['Desktop Chrome'],
		baseURL: portalURL,
		screenshot: 'only-on-failure',
		trace: 'retain-on-failure',
	},
	workers: 1,
});
