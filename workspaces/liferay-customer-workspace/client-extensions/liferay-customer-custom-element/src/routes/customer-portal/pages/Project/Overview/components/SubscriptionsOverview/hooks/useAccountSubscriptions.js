/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {useLazyGetAccountSubscriptions} from '../../../../../../../../common/services/liferay/graphql/account-subscriptions';
import {SUBSCRIPTIONS_STATUS} from '../../../../../../utils/constants';

export const getCurrentDate = new Date().toISOString().slice(0, 10);

export default function useAccountSubscriptions(
	accountSubcriptionGroup,
	accountSubscriptionGroupsLoading
) {
	const [lastSubscriptionStatus, setLastSubscriptionStatus] = useState([
		SUBSCRIPTIONS_STATUS.active,
	]);

	const [handleGetAccountSubscriptions, {called, data, loading}] =
		useLazyGetAccountSubscriptions();

	const getSubscriptionStatusFilter = (subscriptionStatuses) => {
		const filters = [];

		if (subscriptionStatuses.includes(SUBSCRIPTIONS_STATUS.active)) {
			filters.push(
				`(endDate ge ${getCurrentDate} and startDate le ${getCurrentDate})`
			);
		}

		if (subscriptionStatuses.includes(SUBSCRIPTIONS_STATUS.expired)) {
			filters.push(`endDate lt ${getCurrentDate}`);
		}

		if (subscriptionStatuses.includes(SUBSCRIPTIONS_STATUS.future)) {
			filters.push(`startDate gt ${getCurrentDate}`);
		}

		return filters.join(' or ');
	};

	useEffect(() => {
		if (accountSubcriptionGroup) {
			handleGetAccountSubscriptions({
				variables: {
					filter: `accountSubscriptionGroupERC eq '${accountSubcriptionGroup.externalReferenceCode}' and (${getSubscriptionStatusFilter(lastSubscriptionStatus)})`,
				},
			});
		}
	}, [
		handleGetAccountSubscriptions,
		accountSubcriptionGroup,
		lastSubscriptionStatus,
	]);

	return [
		setLastSubscriptionStatus,
		{
			data,
			loading: accountSubscriptionGroupsLoading || !called || loading,
		},
	];
}
