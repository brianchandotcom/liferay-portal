/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type Lookup = {
	key: string;
	name: string;
};

export type User = {
	additionalName: string;
	alternateName: string;
	createDate: Date;
	creator: number;
	emailAddress: string;
	externalReferenceCode: string;
	familyName: string;
	givenName: string;
	id: number;
	lastLoginDate: Date;
	modifiedDate: Date;
	objectDefinitionId: number;
	status: number;
	uuid: number;
};

export type Ticket = {
	assignee: User;
	dateCreated: Date;
	dateModified: Date;
	description: string;
	externalReferenceCode: string;
	id: string;
	payload: TicketPayload;
	priority: string;
	region: string;
	resolution: string;
	subject: string;
	suggestions: {
		assetURL: string;
		text: string;
	}[];
	ticketStatus: string;
	type: string;
};

export type TicketPayload = {
	dateCreated: string;
	dateModified: string;
	description: string;
	externalReferenceCode: string;
	id: string;
	priority: {name: string};
	region: {name: string};
	resolution: {name: string};
	subject: string;
	suggestions: string;
	ticketStatus: {name: string};
	type: {name: string};
	userToJ3Y7Ticket: User;
};
