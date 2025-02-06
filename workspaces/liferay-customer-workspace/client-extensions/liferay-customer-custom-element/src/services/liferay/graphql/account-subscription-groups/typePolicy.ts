/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FieldPolicy,
	FieldReadFunction,
	TypePolicies,
} from '@apollo/client/cache';
import {PRODUCT_TYPES} from '~/features/project/utils/constants';

import {PROVISIONED_ACCOUNT_SUBSCRIPTION_GROUPS_NAMES} from './utils/constants/provisionedAccountSubscriptionGroupsNames';

export type AccountSubscriptionGroupsTypePolicies = {
	C_AccountSubscriptionGroup: {
		fields: {
			isProvisioned: FieldPolicy<boolean> & {
				read: FieldReadFunction<boolean>;
			};
		};
		keyFields: (data: any) => string[];
	};
	C_AccountSubscriptionGroupPage: {
		fields: {
			hasPartnership: FieldPolicy<boolean> & {
				read: FieldReadFunction<boolean>;
			};
		};
	};
} & TypePolicies;

export const accountSubscriptionGroupsTypePolicy: AccountSubscriptionGroupsTypePolicies =
	{
		C_AccountSubscriptionGroup: {
			fields: {
				isProvisioned: {
					read(_, {readField}) {
						return PROVISIONED_ACCOUNT_SUBSCRIPTION_GROUPS_NAMES.includes(
							readField('name') as string
						);
					},
				},
			},
			keyFields: () => ['externalReferenceCode'],
		},
		C_AccountSubscriptionGroupPage: {
			fields: {
				hasPartnership: {
					read(_, {readField}) {
						return (readField('items') as []).some(
							(accountSubscriptionGroup: any) =>
								readField('name', accountSubscriptionGroup) ===
								PRODUCT_TYPES.partnership
						);
					},
				},
			},
		},
	};

export const accountSubscriptionGroupsQueryTypePolicy = {
	accountSubscriptionGroups: {
		keyArgs: ['filter', 'sort'],
	},
};
