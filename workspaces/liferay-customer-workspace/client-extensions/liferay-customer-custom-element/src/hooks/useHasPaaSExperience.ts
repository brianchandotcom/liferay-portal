/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {hasPaaSExperienceSubscription} from '~/features/project/utils/subscription';
import SearchBuilder from '~/lib/SearchBuilder';
import {useGetAccountSubscriptions} from '~/services/liferay/graphql/account-subscriptions/queries/useGetAccountSubscriptions';

export default function useHasPaaSExperience(
	accountKey: string | undefined
): boolean {
	const {data} = useGetAccountSubscriptions({
		filter: new SearchBuilder().eq('accountKey', accountKey ?? '').build(),
		skip: !accountKey,
	});

	return hasPaaSExperienceSubscription(data?.c?.accountSubscriptions?.items);
}
