/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '~/core/Rest';
import yupSchema from '~/schema/yup';

import {Role, UserAccount} from './types';

type RoleForm = typeof yupSchema.role.__outputType;

class LiferayUserRolesRest extends Rest<RoleForm, Role> {
	constructor() {
		super({
			uri: 'roles',
		});
	}

	public async rolesToUser(
		roles: number[],
		roleBriefs: Role[] = [],
		userAccount: UserAccount
	): Promise<UserAccount> {
		const deleteRoles = roleBriefs
			.filter(
				(roleBrief) =>
					!roles.find((role) => Number(role) === Number(roleBrief.id))
			)
			.map((roleBrief) => roleBrief.id as number);

		const addRoles = roles.filter(
			(rolesItems) =>
				!roleBriefs.find(
					(item) => Number(item.id) === Number(rolesItems)
				)
		);

		for (const rolesUserDelete of deleteRoles) {
			await this.fetcher.delete(
				`/roles/${rolesUserDelete}/association/user-account/${userAccount.id}`
			);
		}

		for (const rolesUserUpdate of addRoles) {
			await this.fetcher.post(
				`/roles/${rolesUserUpdate}/association/user-account/${userAccount.id}`
			);
		}

		userAccount.roleBriefs = [
			...userAccount.roleBriefs.filter(
				(roleBrief) => !deleteRoles.includes(roleBrief.id)
			),
			...addRoles.map((roleId) => ({id: roleId, name: 'www'})),
		];

		return userAccount;
	}
}

const liferayUserRolesRest = new LiferayUserRolesRest();

export {liferayUserRolesRest};
