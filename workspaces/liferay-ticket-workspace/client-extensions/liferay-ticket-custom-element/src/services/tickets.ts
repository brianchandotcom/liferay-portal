/* eslint-disable quote-props */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Ticket} from '../types';
import {Liferay} from './liferay';
import {
	J3Y7_PRIORITIES,
	J3Y7_REGIONS,
	J3Y7_RESOLUTIONS,
	J3Y7_STATUSES,
	J3Y7_TYPES,
	ListTypeDefinitions,
	fetchListTypeDefinitions,
} from './listTypeEntries';

const ticketSubjects = [
	'My object definition is not deploying in my batch client extension',
	'A theme CSS client extension is not showing on my search page',
	"I would like to change my site's icon through a client extension",
	'When updating a custom element React app, the URL metadata is not specified correctly',
	'Liferay is not triggering my Spring Boot app from an Object Action',
	'Client Extensions are amazing - how can I learn more?',
];

function getRandomElement(array: any[]) {
	return array[Math.floor(Math.random() * array.length)];
}

export type FetchTicketsQueryKey = {
	queryKey: [
		string,
		{
			filter: {field: string; value: string};
			page: number;
			pageSize: number;
			search?: string;
		}
	];
};

export async function fetchTickets({queryKey}: FetchTicketsQueryKey) {
	const [, {filter, page, pageSize, search}] = queryKey;

	let filterString = '';
	let searchString = '';

	if (filter?.field && filter?.value) {
		filterString =
			'&filter=' +
			encodeURIComponent(`${filter.field} eq '${filter.value}'`);
	}

	if (search) {
		searchString = '&search=' + encodeURIComponent(search);
	}

	const response = await fetch(
		`/o/c/j3y7tickets?pageSize=${pageSize}&page=${page}&sort=dateModified:desc${filterString}${searchString}&nestedFields=userToJ3Y7Ticket`,
		{
			headers: {
				'accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		}
	);

	return response.json();
}

export async function fetchRecentTickets() {
	const response = await fetch(
		'/o/c/j3y7tickets?pageSize=3&page=1&sort=dateModified:desc',
		{
			headers: {
				'accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		}
	);

	return response.json();
}

export async function generateNewTicket() {
	let listTypeDefinitions = {} as ListTypeDefinitions;

	if (!(J3Y7_PRIORITIES in listTypeDefinitions)) {
		listTypeDefinitions = await fetchListTypeDefinitions();
	}
	const priorities = listTypeDefinitions[J3Y7_PRIORITIES];
	const regions = listTypeDefinitions[J3Y7_REGIONS];
	const resolutions = listTypeDefinitions[J3Y7_RESOLUTIONS];
	const types = listTypeDefinitions[J3Y7_TYPES];

	return fetch(`/o/c/j3y7tickets`, {
		body: JSON.stringify({
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
		}),
		headers: {
			'accept': 'application/json',
			'content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'POST',
	});
}

export async function updateTicketStatus(ticket: Ticket) {
	let listTypeDefinitions = {} as ListTypeDefinitions;

	if (!(J3Y7_PRIORITIES in listTypeDefinitions)) {
		listTypeDefinitions = await fetchListTypeDefinitions();
	}
	const priorities = listTypeDefinitions[J3Y7_PRIORITIES] as any[];
	const regions = listTypeDefinitions[J3Y7_REGIONS] as any[];
	const resolutions = listTypeDefinitions[J3Y7_RESOLUTIONS] as any[];
	const types = listTypeDefinitions[J3Y7_TYPES] as any[];
	const statuses = listTypeDefinitions[J3Y7_STATUSES] as any[];

	ticket.priority = priorities.find((p) => p.name === ticket.priority);
	ticket.region = regions.find((p) => p.name === ticket.region);
	ticket.resolution = resolutions.find((p) => p.name === ticket.resolution);
	ticket.type = types.find((p) => p.name === ticket.type);
	ticket.ticketStatus = statuses.find(
		(status: any) => ticket.ticketStatus === status.name
	);

	return fetch(`/o/c/j3y7tickets/${ticket.id}`, {
		body: JSON.stringify(ticket),
		headers: {
			'accept': 'application/json',
			'content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'PUT',
	});
}

export async function assignTicketToMe(ticket: Ticket) {
	return fetch(
		`/o/c/j3y7tickets/by-external-reference-code/${ticket.externalReferenceCode}/object-actions/AssignTicketToMe`,
		{
			headers: {
				'accept': 'application/json',
				'content-Type': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
			method: 'PUT',
		}
	);
}
