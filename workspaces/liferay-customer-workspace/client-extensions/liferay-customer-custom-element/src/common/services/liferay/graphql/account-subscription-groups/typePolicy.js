/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PRODUCT_TYPES} from '../../../../../routes/customer-portal/utils/constants';
import {PROVISIONED_ACCOUNT_SUBSCRIPTION_GROUPS_NAMES} from './utils/constants/provisionedAccountSubscriptionGroupsNames';

export const accountSubscriptionGroupsTypePolicy = {
	C_AccountSubscriptionGroup: {
		fields: {
			isProvisioned: {
				read(_, {readField}) {
					return PROVISIONED_ACCOUNT_SUBSCRIPTION_GROUPS_NAMES.includes(
						readField('name')
					);
				},
			},
		},
		keyFields: ['externalReferenceCode'],
	},
	C_AccountSubscriptionGroupPage: {
		fields: {
			hasPartnership: {
				read(_, {readField}) {
					return readField('items').some(
						(accountSubscriptionGroup) =>
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
