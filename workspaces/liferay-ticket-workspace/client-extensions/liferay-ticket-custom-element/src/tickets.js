/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import axios from 'axios';
import {useQuery} from 'react-query';

import {
	J3Y7_PRIORITIES,
	J3Y7_REGIONS,
	J3Y7_RESOLUTIONS,
	J3Y7_TYPES,
	fetchListTypeDefinitions,
} from './listTypeEntries';

export async function fetchTickets({queryKey}) {
	const [, {filter, page, pageSize, search}] = queryKey;
	const filterSnippet =
		filter && filter.field && filter.value
			? encodeURI(`&filter=${filter.field} eq '${filter.value}'`)
			: '';
	const searchSnippet = search ? encodeURI(`&search=${search}`) : '';
	const {data} = await axios.get(
		`/o/c/j3y7tickets?pageSize=${pageSize}&sort=dateModified:desc&page=${page}${filterSnippet}${searchSnippet}`,
		{
			headers: {
				"accept": "application/json",
				"x-csrf-token": Liferay.authToken
			}
		}
	);

	return data;
}

export async function fetchRecentTickets() {
	const {data} = await axios.get(
		`/o/c/j3y7tickets?pageSize=3&page=1&sort=dateModified:desc`,
		{
			headers: {
				"accept": "application/json",
				"x-csrf-token": Liferay.authToken
			}
		}
	);

	return data;
}

let listTypeDefinitions = {};

const ticketSubjects = [
	'My object definition is not deploying in my batch client extension',
	'A theme CSS client extension is not showing on my search page',
	"I would like to change my site's icon through a client extension",
	'When updating a custom element React app, the URL metadata is not specified correctly',
	'Liferay is not triggering my Spring Boot app from an Object Action',
	'Client Extensions are amazing - how can I learn more?',
];

function getRandomElement(array) {
	return array[Math.floor(Math.random() * array.length)];
}

export async function generateNewTicket() {
	if (!(J3Y7_PRIORITIES in listTypeDefinitions)) {
		listTypeDefinitions = await fetchListTypeDefinitions();
	}
	const priorities = listTypeDefinitions[J3Y7_PRIORITIES];
	const regions = listTypeDefinitions[J3Y7_REGIONS];
	const resolutions = listTypeDefinitions[J3Y7_RESOLUTIONS];
	const types = listTypeDefinitions[J3Y7_TYPES];

	return axios.post(
		`/o/c/j3y7tickets`,
		{
			priority: {
				key: getRandomElement(priorities).key,
			},
			region: {
				key: getRandomElement(regions).key,
			},
			resolution: {
				key: getRandomElement(resolutions).key,
			},
			status: {
				code: 0,
			},
			subject: getRandomElement(ticketSubjects),
			ticketStatus: {
				key: 'open',
			},
			type: {
				key: getRandomElement(types).key,
			},
		},
		{
			headers: {
				"accept": "application/json",
				"content-Type": "application/json",
				"x-csrf-token": Liferay.authToken
			}
		}
	);
}

export function useRecentTickets() {
	const recentTickets = useQuery(['recentTickets'], fetchRecentTickets, {
		refetchInterval: 5000,
		refetchOnMount: false,
	});

	if (recentTickets.isSuccess) {
		return recentTickets.data?.items.map((ticket) => {
			let suggestions = [];
			try {
				suggestions = JSON.parse(ticket?.suggestions);
			}
			catch (error) {}

			return {
				dateCreated: new Date(ticket.dateCreated),
				dateModified: new Date(ticket.dateModified),
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
		});
	}

	return [];
}

/* Return ticket data from a closure. using React state was leading to too many rerenders
   or flickering of ui components */
export const useTickets = (() => {
	let ticketData = {rows: [], totalCount: 0};

	const useTicketsInner = (page, pageSize, filter, search) => {
		const tickets = useQuery(
			['tickets', {page, pageSize, filter, search}],
			fetchTickets,
			{refetchInterval: 5000, refetchOnMount: false}
		);

		if (tickets.isSuccess) {
			ticketData = {
				totalCount: tickets?.data?.totalCount,
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
			};
		}

		return ticketData;
	};

	return useTicketsInner;
})();
