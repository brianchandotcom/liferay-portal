/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isAccountAdministrator from '~/utils/isAccountAdministrator';
import isSupportSeatRole from '~/utils/isSupportSeatRole';

import {Liferay} from '../..';

export const userAccountsTypePolicy = {
	AccountBrief: {
		keyFields: false,
	},
	OrganizationBrief: {
		keyFields: false,
	},
	RoleBrief: {
		fields: {
			name: {
				read(name: string) {
					if (name === 'Account Member') {
						return 'User';
					}

					if (name === 'Account Administrator') {
						return 'Administrator';
					}

					return name;
				},
			},
		},
		keyFields: ['id'],
	},
	UserAccount: {
		fields: {
			dateCreated: {
				read(dateCreated: string): Date {
					return new Date(dateCreated);
				},
			},
			isLiferayStaff: {
				read(_: unknown, {readField}: {readField: Function}): boolean {
					return !!(readField('roleBriefs') as any[]).some(
						(roleBrief: any) =>
							readField('name', roleBrief) === 'Liferay Staff'
					);
				},
			},
			isLoggedUser: {
				read(_: unknown, {readField}: {readField: Function}): boolean {
					return (
						readField('id') === +Liferay.ThemeDisplay.getUserId()
					);
				},
			},
			isPartner: {
				read(_: unknown, {readField}: {readField: Function}): boolean {
					return !!(readField('roleBriefs') as any[]).some(
						(roleBrief: any) =>
							readField('name', roleBrief) === 'Partner'
					);
				},
			},
			selectedAccountSummary: {
				read(
					_: unknown,
					{
						readField,
						variables: {externalReferenceCode},
					}: {
						readField: Function;
						variables: {externalReferenceCode: string};
					}
				): {
					hasAdministratorRole: boolean;
					hasSupportSeatRole: boolean;
					roleBriefs: {id: string; name: string}[];
				} {
					const accountBriefRef = readField('accountBriefs')?.find(
						(accountBrief: {externalReferenceCode: string}) =>
							readField('externalReferenceCode', accountBrief) ===
							externalReferenceCode
					);

					const roleBriefs = readField(
						'roleBriefs',
						accountBriefRef
					)?.map((roleBrief: {id: string; name: string}) => ({
						id: readField('id', roleBrief),
						name: readField('name', roleBrief),
					}));

					const hasAdministratorRole = roleBriefs?.some(
						({name}: {name: string}) => isAccountAdministrator(name)
					);

					const hasSupportSeatRole = roleBriefs?.some(
						({name}: {name: string}) => isSupportSeatRole(name)
					);

					return {
						hasAdministratorRole,
						hasSupportSeatRole,
						roleBriefs,
					};
				},
			},
		},
		keyFields: ['id'],
	},
};
