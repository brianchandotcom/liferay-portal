/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UserRoleNames} from '~/shared/util/constants';

import User from '../User';

const adminUser = new User({roleName: UserRoleNames.Administrator});

const memberUser = new User({roleName: UserRoleNames.Member});

const ownerUser = new User({roleName: UserRoleNames.Owner});

describe('User', () => {
	it('returns a new User', () => {
		const user = new User();

		expect(user).toBeTruthy();
	});

	it('is able to determine if a user has a permission, given an array of roles or a single role', () => {
		const user = new User({roleName: UserRoleNames.Member});

		expect(
			user.hasPermission([
				UserRoleNames.Administrator,
				UserRoleNames.Owner,
			])
		).toBe(false);

		expect(
			user.hasPermission([
				UserRoleNames.Administrator,
				UserRoleNames.Member,
				UserRoleNames.Owner,
			])
		).toBe(true);

		expect(user.hasPermission(UserRoleNames.Member)).toBe(true);
	});

	it('is able to determine if a user is a member', () => {
		expect(memberUser.isMember()).toBe(true);

		expect(adminUser.isMember()).toBe(false);

		expect(ownerUser.isMember()).toBe(false);
	});

	it('is able to determine if a user is an owner', () => {
		expect(ownerUser.isOwner()).toBe(true);

		expect(adminUser.isOwner()).toBe(false);

		expect(memberUser.isOwner()).toBe(false);
	});

	it('is able to determine if a user has an administrative level of access', () => {
		expect(adminUser.isAdmin()).toBe(true);

		expect(ownerUser.isAdmin()).toBe(true);

		expect(memberUser.isAdmin()).toBe(false);
	});
});
