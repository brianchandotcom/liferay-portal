/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	accountRolesQueryTypePolicy,
	accountRolesTypePolicy,
} from './account-roles/typePolicy';
import {
	accountSubscriptionGroupsQueryTypePolicy,
	accountSubscriptionGroupsTypePolicy,
} from './account-subscription-groups/typePolicy';
import {
	accountSubscriptionsQueryTypePolicy,
	accountSubscriptionsTypePolicy,
} from './account-subscriptions/typePolicy';
import {
	koroneikiAccountsQueryTypePolicy,
	koroneikiAccountsTypePolicy,
} from './koroneiki-accounts/typePolicy';
import {
	orderItemsQueryTypePolicy,
	orderItemsTypePolicy,
} from './order-items/typePolicy';
import {userAccountsTypePolicy} from './user-accounts/typePolicy';

export const liferayTypePolicies = {
	...accountSubscriptionsTypePolicy,
	...accountSubscriptionGroupsTypePolicy,
	...accountRolesTypePolicy,
	...userAccountsTypePolicy,
	...koroneikiAccountsTypePolicy,
	...orderItemsTypePolicy,
	Mutationc: {
		merge: true,
	},
	Query: {
		fields: {
			...accountRolesQueryTypePolicy,
			...orderItemsQueryTypePolicy,
		},
	},
	c: {
		fields: {
			...accountSubscriptionsQueryTypePolicy,
			...accountSubscriptionGroupsQueryTypePolicy,
			...koroneikiAccountsQueryTypePolicy,
		},
		merge: true,
	},
};
