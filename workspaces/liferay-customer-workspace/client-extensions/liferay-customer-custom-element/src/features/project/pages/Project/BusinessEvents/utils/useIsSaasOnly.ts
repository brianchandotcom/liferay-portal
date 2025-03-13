/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {PRODUCT_TYPES} from '~/features/project/utils/constants';
import {IAccountSubscriptionGroup} from '~/utils/types';

export default function useIsSaasOnly(
	subscriptionGroups?: IAccountSubscriptionGroup[]
) {
	const isSaasOnly = useMemo(
		() =>
			subscriptionGroups?.length === 1 &&
			subscriptionGroups[0].name === PRODUCT_TYPES.liferayExperienceCloud,
		[subscriptionGroups]
	);

	return {isSaasOnly};
}
