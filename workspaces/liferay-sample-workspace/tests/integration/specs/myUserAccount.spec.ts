/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect} from '@playwright/test';

import {apiTest as test} from '../fixtures/apiTest';

type UserAccount = {
	emailAddress: string;
	id: number;
	name: string;
};

test.describe('/o/headless-admin-user/v1.0/my-user-account', () => {
	test('returns the authenticated user account', async ({api}) => {
		const user = await api.get<UserAccount>(
			'/o/headless-admin-user/v1.0/my-user-account'
		);

		expect(user.id).toBeGreaterThan(0);
		expect(user.emailAddress).toContain('@');
		expect(user.name.length).toBeGreaterThan(0);
	});
});
