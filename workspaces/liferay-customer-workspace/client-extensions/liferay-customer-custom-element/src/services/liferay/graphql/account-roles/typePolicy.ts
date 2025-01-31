/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const accountRolesTypePolicy = {
	AccountRole: {
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
};

export const accountRolesQueryTypePolicy = {
	accountAccountRolesByExternalReferenceCode: {
		keyArgs: ['filter'],
	},
};
