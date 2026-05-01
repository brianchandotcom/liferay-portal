/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import useAccountKey from '~/hooks/useAccountKey';
import {getBusinessEvents} from '~/services/liferay/rest/jira/Jira';
import {IBusinessEvent} from '~/utils/types';

export default function useGetBusinessEvents(): {
	businessEvents: IBusinessEvent[];
	fetchBusinessEvents: () => Promise<void>;
	loading: boolean;
} {
	const [businessEvents, setBusinessEvents] = useState<IBusinessEvent[]>([]);

	const [loading, setLoading] = useState(true);

	const accountKey = useAccountKey();

	const fetchBusinessEvents = useCallback(async () => {
		if (!accountKey) {
			return;
		}

		try {
			const businessEventsResponse = await getBusinessEvents(accountKey);

			const items = (businessEventsResponse.items ||
				[]) as IBusinessEvent[];

			setBusinessEvents(items);
		}
		catch (error) {
			console.error('Error fetching business events:', error);
		}
		finally {
			setLoading(false);
		}
	}, [accountKey]);

	useEffect(() => {
		fetchBusinessEvents();
	}, [fetchBusinessEvents]);

	return {businessEvents, fetchBusinessEvents, loading};
}
