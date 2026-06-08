/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSubscriptionName} from '~/shared/hooks/useSubscriptionName';
import {isLDPPlan} from '~/shared/util/subscriptions';

export const useLDPEnabled = function useLDPEnabled({
	groupId,
}: {
	groupId: string;
}): boolean {
	return isLDPPlan(useSubscriptionName({groupId}));
};
