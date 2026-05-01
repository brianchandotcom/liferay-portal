/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect} from '@playwright/test';

import {oneApiTest as test} from '../fixtures/oneApiTest';
import {HeadlessPage} from '../helpers/OneApiHelpers';

const OBJECT_REST_PATHS = ['accountFlags'];

test.describe('One Object REST surface', () => {
	for (const path of OBJECT_REST_PATHS) {
		test(`GET /o/c/${path} responds with a page envelope`, async ({
			oneApi,
		}) => {
			const body = await oneApi.get<HeadlessPage<unknown>>(
				`/o/c/${path}`
			);

			expect(Array.isArray(body.items)).toBeTruthy();
			expect(typeof body.totalCount).toBe('number');
		});
	}
});
