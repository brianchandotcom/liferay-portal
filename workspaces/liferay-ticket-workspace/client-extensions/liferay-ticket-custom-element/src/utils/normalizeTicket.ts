/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Ticket, TicketPayload} from '../types';

export default function normalizeTicket(ticketPayload: TicketPayload): Ticket {
	let suggestions = [];

	try {
		suggestions = JSON.parse(ticketPayload?.suggestions);
	}
	catch (error) {}

	delete ticketPayload.actions;

	return {
		assignee: ticketPayload.userToJ3Y7Ticket,
		dateCreated: new Date(ticketPayload.dateCreated),
		dateModified: new Date(ticketPayload.dateModified),
		description: ticketPayload.description,
		externalReferenceCode: ticketPayload.externalReferenceCode,
		id: ticketPayload.id,
		payload: ticketPayload,
		priority: ticketPayload.priority?.name,
		region: ticketPayload.region?.name,
		resolution: ticketPayload.resolution?.name,
		subject: ticketPayload.subject,
		suggestions,
		ticketStatus: ticketPayload.ticketStatus?.name,
		type: ticketPayload.type?.name,
	};
}
