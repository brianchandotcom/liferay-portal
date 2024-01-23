/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {useQuery} from 'react-query';

import {FetchTicketsQueryKey, fetchTickets} from '../services/tickets';
import {Ticket} from '../types';
import normalizeTicket from '../utils/normalizeTicket';

const useTickets = ({
	filter,
	page,
	pageSize,
	search,
}: {
	filter: {field: string; value: string};
	page: number;
	pageSize: number;
	search?: string;
}) => {
	const tickets = useQuery(
		['tickets', {filter, page, pageSize, search}],
		({queryKey}) => fetchTickets({queryKey} as FetchTicketsQueryKey),
		{refetchInterval: 5000, refetchOnMount: true}
	);

	const ticketsMemoized = useMemo(() => {
		if (tickets.isSuccess) {
			return {
				rows: tickets?.data?.items?.map(normalizeTicket),
				totalCount: tickets?.data?.totalCount,
			};
		}

		return {rows: [], totalCount: 0};
	}, [tickets?.data?.items, tickets?.data?.totalCount, tickets.isSuccess]);

	return ticketsMemoized as {rows: Ticket[]; totalCount: number};
};

export {useTickets};
