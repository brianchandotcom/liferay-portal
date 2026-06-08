/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import {useSelector} from 'react-redux';

type RootState = Map<string, any>;

export const useSubscriptionName = function useSubscriptionName({
	groupId,
}: {
	groupId: string;
}): string | null {
	return useSelector((state: RootState) =>
		state.getIn(
			['projects', groupId, 'data', 'faroSubscription', 'name'],
			null
		)
	);
};
