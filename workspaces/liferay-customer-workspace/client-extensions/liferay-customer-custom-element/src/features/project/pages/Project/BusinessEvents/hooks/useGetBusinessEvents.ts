/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import {getBusinessEvents} from '~/services/liferay/api';
import {IBusinessEvent} from '~/utils/types';

export default function useGetBusinessEvents(filterQuery: string): {
	businessEvents: IBusinessEvent[];
	fetchBusinessEvents: () => Promise<void>;
	loading: boolean;
} {
	const [businessEvents, setBusinessEvents] = useState<IBusinessEvent[]>([]);

	const [loading, setLoading] = useState(true);

	const fetchBusinessEvents = useCallback(async () => {
		try {
			const businessEventsResponse = await getBusinessEvents(filterQuery);

			setBusinessEvents(businessEventsResponse.items);
		}
		catch (error) {
			console.error('Error fetching business events:', error);
		}
		finally {
			setLoading(false);
		}
	}, [filterQuery]);

	useEffect(() => {
		fetchBusinessEvents();
	}, [fetchBusinessEvents]);

	return {businessEvents, fetchBusinessEvents, loading};
}
