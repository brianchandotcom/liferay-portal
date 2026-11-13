/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PlaywrightTestConfig, devices} from '@playwright/test';
import {join} from 'path';

import {liferayConfig} from './core/liferayConfig';

const {baseUrl} = liferayConfig.environment;

export const baseConfig: PlaywrightTestConfig = {
	expect: {
		timeout: 15 * 1000,
	},
	forbidOnly: !!process.env.CI,
	globalSetup: join(__dirname, 'globalSetup.ts'),
	reporter: [['html', {open: 'never'}]],
	retries: process.env.CI ? 1 : 0,
	testDir: './tests',
	timeout: 90 * 1000,
	use: {
		...devices['Desktop Chrome'],
		baseURL: baseUrl,
		screenshot: 'only-on-failure',
		trace: 'retain-on-failure',
	},
	workers: 1,
};
