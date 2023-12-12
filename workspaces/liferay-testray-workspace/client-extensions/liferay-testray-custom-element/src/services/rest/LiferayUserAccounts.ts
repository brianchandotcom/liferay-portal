/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '~/core/Rest';
import yupSchema from '~/schema/yup';

import {UserAccount} from './types';

type UserForm = typeof yupSchema.userWithPassword.__outputType;

class LiferayUserAccountsImpl extends Rest<UserForm, UserAccount> {
	constructor() {
		super({
			adapter: ({
				alternateName,
				currentPassword,
				emailAddress,
				familyName,
				givenName,
				password,
			}) => ({
				alternateName,
				currentPassword,
				emailAddress,
				familyName,
				givenName,
				password,
			}),
			uri: 'user-accounts',
		});
	}

	public async getPagePermission() {
		const response = await this.getAll();

		const actions = response?.actions ?? {};

		return !!(actions as any)['post-user-account'];
	}
}

const liferayUserAccountsImpl = new LiferayUserAccountsImpl();

export {liferayUserAccountsImpl};
