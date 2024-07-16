/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../..';
import isAccountAdministrator from '../../../../utils/isAccountAdministrator';
import isSupportSeatRole from '../../../../utils/isSupportSeatRole';

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
				read(name) {
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
				read(dateCreated) {
					return new Date(dateCreated);
				},
			},
			isLiferayStaff: {
				read(_, {readField}) {
					return !!readField('organizationBriefs').some(
						(organizationBrief) =>
							readField('name', organizationBrief) ===
							'Liferay Staff'
					);
				},
			},
			isLoggedUser: {
				read(_, {readField}) {
					return (
						readField('id') === +Liferay.ThemeDisplay.getUserId()
					);
				},
			},
			isProvisioning: {
				read(_, {readField}) {
					return !!readField('roleBriefs')?.some(
						(roleBrief) =>
							readField('name', roleBrief) === 'Provisioning'
					);
				},
			},
			selectedAccountSummary: {
				read(_, {readField, variables: {externalReferenceCode}}) {
					const accountBriefRef = readField('accountBriefs')?.find(
						(accountBrief) =>
							readField('externalReferenceCode', accountBrief) ===
							externalReferenceCode
					);

					const roleBriefs = readField(
						'roleBriefs',
						accountBriefRef
					).map((roleBrief) => ({
						id: readField('id', roleBrief),
						name: readField('name', roleBrief),
					}));

					const hasAdministratorRole = roleBriefs.some(({name}) =>
						isAccountAdministrator(name)
					);

					const hasSupportSeatRole = roleBriefs.some(({name}) =>
						isSupportSeatRole(name)
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
