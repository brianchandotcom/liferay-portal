/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TicketPayload} from '../types';

export function TicketPayloadMapper(ticket: TicketPayload) {
	let suggestions = [];

	try {
		suggestions = JSON.parse(ticket?.suggestions);
	}
	catch (error) {}

	delete ticket.actions;

	return {
		assignee: ticket.userToJ3Y7Ticket,
		dateCreated: new Date(ticket.dateCreated),
		dateModified: new Date(ticket.dateModified),
		description: ticket.description,
		externalReferenceCode: ticket.externalReferenceCode,
		id: ticket.id,
		payload: ticket,
		priority: ticket.priority?.name,
		region: ticket.region?.name,
		resolution: ticket.resolution?.name,
		subject: ticket.subject,
		suggestions,
		ticketStatus: ticket.ticketStatus?.name,
		type: ticket.type?.name,
	};
}
