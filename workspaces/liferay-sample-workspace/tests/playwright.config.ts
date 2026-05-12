/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {defineConfig, devices} from '@playwright/test';
import dotenv from 'dotenv';
import {resolve} from 'path';

dotenv.config({path: resolve(__dirname, '../.env')});

const baseURL = process.env.BASE_URL ?? 'http://localhost:8080';

export default defineConfig({
	expect: {
		timeout: 15 * 1000,
	},
	forbidOnly: !!process.env.CI,
	fullyParallel: false,
	globalTimeout: 30 * 60 * 1000,
	projects: [
		{
			name: 'integration',
			testDir: './integration/specs',
			use: {
				baseURL,
			},
		},
		{
			name: 'e2e',
			testDir: './e2e/specs',
			use: {
				...devices['Desktop Chrome'],
				baseURL,
				screenshot: 'only-on-failure',
				trace: 'retain-on-failure',
				video: 'retain-on-failure',
			},
		},
	],
	reporter: [
		['html', {open: 'never', outputFolder: 'playwright-report'}],
		['list'],
		[
			'junit',
			{
				outputFile: 'test-results/TEST-playwright.xml',
			},
		],
	],
	retries: process.env.CI ? 1 : 0,
	timeout: 90 * 1000,
	workers: 1,
});
