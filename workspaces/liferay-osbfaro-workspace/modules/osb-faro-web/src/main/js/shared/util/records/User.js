/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Record} from 'immutable';
import {isArray} from 'lodash';
import {UserRoleNames, UserStatuses} from '~/shared/util/constants';

export default class User extends (new Record({
	emailAddress: null,
	groupId: null,
	id: null,
	languageId: null,
	name: '',
	roleName: null,
	screenName: '',
	status: UserStatuses.Approved,
	userId: null,
})) {
	constructor(params = {}) {
		super(params);
	}

	hasPermission(permissions) {
		const {roleName} = this;

		return isArray(permissions)
			? permissions.includes(roleName)
			: roleName === permissions;
	}

	isAdmin() {
		return (
			this.roleName === UserRoleNames.Administrator ||
			this.roleName === UserRoleNames.Owner
		);
	}

	isMember() {
		return this.roleName === UserRoleNames.Member;
	}

	isOwner() {
		return this.roleName === UserRoleNames.Owner;
	}
}
