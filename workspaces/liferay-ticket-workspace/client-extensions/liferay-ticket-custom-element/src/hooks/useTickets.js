/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {useQuery} from 'react-query';

import {fetchTickets} from '../services/tickets';

const useTickets = (page, pageSize, filter, search) => {
	const tickets = useQuery(
		['tickets', {filter, page, pageSize, search}],
		fetchTickets,
		{refetchInterval: 5000, refetchOnMount: false}
	);

	const ticketsMemoized = useMemo(() => {
		if (tickets.isSuccess) {
			return {
				rows: tickets?.data?.items?.map((ticket) => {
					let suggestions = [];
					try {
						suggestions = JSON.parse(ticket?.suggestions);
					}
					catch (error) {}

					return {
						description: ticket.description,
						id: ticket.id,
						priority: ticket.priority?.name,
						region: ticket.region?.name,
						resolution: ticket.resolution?.name,
						subject: ticket.subject,
						suggestions,
						ticketStatus: ticket.ticketStatus?.name,
						type: ticket.type?.name,
					};
				}),
				totalCount: tickets?.data?.totalCount,
			};
		}

		return {rows: [], totalCount: 0};
	}, [tickets?.data?.items, tickets?.data?.totalCount, tickets.isSuccess]);

	return ticketsMemoized;
};

export {useTickets};
