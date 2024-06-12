/* eslint-disable quote-props */

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Ticket} from '../types';
import {
	J3Y7_PRIORITIES,
	J3Y7_REGIONS,
	J3Y7_RESOLUTIONS,
	J3Y7_STATUSES,
	J3Y7_TYPES,
	ListTypeDefinitions,
	fetchListTypeDefinitions,
} from './listTypeEntries';
import {request} from './request';

export type FetchTicketsQueryKey = {
	queryKey: [
		string,
		{
			filter: {field: string; value: string};
			page: number;
			pageSize: number;
			search?: string;
		},
	];
};

const LIST_TYPE_DEFINITIONS: ListTypeDefinitions =
	await fetchListTypeDefinitions();

const TICKET_SUBJECTS = [
	'My Object Definition Is Not Deploying in My Batch Client Extension',
	'A Theme CSS Client Extension Is Not Showing on My Search Page',
	"I Would Like to Change My Site's Icon Through a Client Extension",
	'When Updating a Custom Element React App, the URL Metadata Is Not Specified Correctly',
	'Liferay Is Not Triggering My Spring Boot App From an Object Action',
	'Client Extensions Are Amazing - How Can I Learn More?',
];

function getRandomElement(array: any[]) {
	return array[Math.floor(Math.random() * array.length)];
}

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

	const response = await request(
		`/o/c/j3y7tickets?pageSize=${pageSize}&page=${page}&sort=dateModified:desc${filterString}${searchString}&nestedFields=userToJ3Y7Ticket`
	);

	return response.json();
}

export async function fetchRecentTickets() {
	const response = await request(
		'/o/c/j3y7tickets?pageSize=3&page=1&sort=dateModified:desc'
	);

	return response.json();
}

export async function generateNewTicket() {
	const priorities = LIST_TYPE_DEFINITIONS[J3Y7_PRIORITIES].entriesArray;
	const regions = LIST_TYPE_DEFINITIONS[J3Y7_REGIONS].entriesArray;
	const resolutions = LIST_TYPE_DEFINITIONS[J3Y7_RESOLUTIONS].entriesArray;
	const types = LIST_TYPE_DEFINITIONS[J3Y7_TYPES].entriesArray;

	return request(
		`/o/c/j3y7tickets`,
		'POST',
		JSON.stringify({
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
			subject: getRandomElement(TICKET_SUBJECTS),
			ticketStatus: {
				key: 'open',
			},
			type: {
				key: getRandomElement(types).key,
			},
		})
	);
}

export async function updateTicketStatus(ticket: Ticket) {
	const result = await request(
		`/o/c/j3y7tickets/${ticket.id}`,
		'PATCH',
		JSON.stringify({
			id: ticket.id,
			ticketStatus:
				LIST_TYPE_DEFINITIONS[J3Y7_STATUSES].entriesMap[
					ticket.ticketStatus
				],
		})
	);

	if (result.ok) {
		return;
	}
	else {
		const jsonResult = await result.json();

		throw new Error(JSON.stringify(jsonResult));
	}
}

export async function assignTicketToMe(ticket: Ticket) {
	const result = await request(
		`/o/c/j3y7tickets/by-external-reference-code/${ticket.externalReferenceCode}/object-actions/AssignTicketToMe`,
		'PUT'
	);

	if (result.ok) {
		return;
	}
	else {
		const jsonResult = await result.json();

		throw new Error(`${jsonResult.status} - ${jsonResult.title}`);
	}
}
