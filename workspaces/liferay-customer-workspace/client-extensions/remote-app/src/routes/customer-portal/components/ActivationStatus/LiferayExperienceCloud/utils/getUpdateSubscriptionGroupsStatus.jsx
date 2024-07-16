/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {actionTypes} from '../../../../context/reducer';
import {STATUS_TAG_TYPE_NAMES} from '../../../../utils/constants';

export default function getUpdateSubscriptionGroupsStatus(
	dispatch,
	handleFinishUpdate,
	handleStatusLxcActivation,
	project,
	projectIdValue,
	subscriptionGroupLxcEnvironment,
	subscriptionGroups,
	updateAccountSubscriptionGroup
) {
	updateAccountSubscriptionGroup({
		variables: {
			AccountSubscriptionGroup: {
				accountKey: project?.accountKey,
				activationStatus: STATUS_TAG_TYPE_NAMES.active,
				r_accountEntryToAccountSubscriptionGroup_accountEntryId:
					project?.id,
			},
			accountSubscriptionGroupId:
				subscriptionGroupLxcEnvironment?.accountSubscriptionGroupId,
		},
	});

	handleStatusLxcActivation();
	handleFinishUpdate();

	const newSubscriptionGroups = subscriptionGroups.map((subscription) => {
		if (
			subscription.accountSubscriptionGroupId ===
			subscriptionGroupLxcEnvironment?.accountSubscriptionGroupId
		) {
			return {
				...subscription,
				activationStatus: STATUS_TAG_TYPE_NAMES.active,
			};
		}

		return subscription;
	});

	dispatch({
		payload: newSubscriptionGroups,
		type: actionTypes.UPDATE_SUBSCRIPTION_GROUPS,
	});
}
