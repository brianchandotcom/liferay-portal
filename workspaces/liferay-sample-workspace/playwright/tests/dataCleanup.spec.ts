/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../core/fixtures/dataApiHelpersTest';
import {loginTest} from '../core/fixtures/loginTest';

const test = mergeTests(dataApiHelpersTest(), loginTest());

test.describe.serial('Data cleanup', () => {
	let objectFolderId: number;

	test(
		'records an object folder created through apiHelpers',
		{tag: '@LPD-108757'},
		async ({apiHelpers}) => {
			const objectFolder =
				await apiHelpers.objectAdmin.postRandomObjectFolder();

			apiHelpers.data.push({id: objectFolder.id, type: 'objectFolder'});

			objectFolderId = objectFolder.id;
		}
	);

	test(
		'the recorded object folder no longer exists',
		{tag: '@LPD-108757'},
		async ({apiHelpers}) => {
			const response = await apiHelpers.getResponse(
				`${apiHelpers.baseUrl}object-admin/v1.0/object-folders/${objectFolderId}`
			);

			expect(response.status()).toBe(404);
		}
	);
});
