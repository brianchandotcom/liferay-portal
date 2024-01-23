/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {useQuery} from 'react-query';

import {fetchRecentTickets} from '../services/tickets';
import normalizeTicket from '../utils/normalizeTicket';

const useRecentTickets = () => {
	const recentTickets = useQuery(['recentTickets'], fetchRecentTickets, {
		refetchInterval: 5000,
		refetchOnMount: false,
	});

	const recentTicketsMemoized = useMemo(() => {
		if (recentTickets.isSuccess) {
			return recentTickets.data?.items.map(normalizeTicket);
		}

		return [];
	}, [recentTickets.data?.items, recentTickets.isSuccess]);

	return recentTicketsMemoized;
};

export {useRecentTickets};
