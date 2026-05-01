/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as OAuth2 from '@liferay/oauth2-provider-web/client';

const OAUTH2_APP = 'liferay-customer-etc-spring-boot-oaua';
const BASE_PATH = '/jira';

async function jiraFetch(
	path: string,
	options?: RequestInit
): Promise<Response> {
	const oauth2Client = await OAuth2.FromUserAgentApplication(OAUTH2_APP);

	return oauth2Client.fetch(`${BASE_PATH}${path}`, options);
}

async function jiraFetchJSON<T = any>(
	path: string,
	options?: RequestInit
): Promise<T> {
	const response = await jiraFetch(path, options);

	if (!response.ok) {
		throw new Error(`Jira API error: ${response.statusText}`);
	}

	return response.json();
}

export async function createBusinessEvent(
	externalReferenceCode: string,
	businessEvent: any
) {
	const response = await jiraFetch(
		`/accounts/${externalReferenceCode}/business-events`,
		{
			body: JSON.stringify(businessEvent),
			headers: {'Content-Type': 'application/json'},
			method: 'POST',
		}
	);

	if (!response.ok) {
		throw new Error(
			`Failed to create business event: ${response.statusText}`
		);
	}

	return response;
}

export async function getAccountTickets(
	externalReferenceCode: string,
	ticketIds?: string[]
) {
	const params = ticketIds?.length
		? `?${ticketIds.map((id) => `ticketIds=${id}`).join('&')}`
		: '';

	return jiraFetchJSON(`/accounts/${externalReferenceCode}/tickets${params}`);
}

export async function getBusinessEventById(
	externalReferenceCode: string,
	id: string
) {
	return jiraFetchJSON(
		`/accounts/${externalReferenceCode}/business-events/${id}`
	);
}

export async function getBusinessEvents(externalReferenceCode: string) {
	const response = await jiraFetch(
		`/accounts/${externalReferenceCode}/business-events`
	);

	if (!response.ok) {
		return {items: []};
	}

	return response.json();
}

export async function getBusinessEventVersions(
	externalReferenceCode: string,
	id: string
) {
	return jiraFetchJSON(
		`/accounts/${externalReferenceCode}/business-events/${id}/versions`
	);
}

export async function getFieldOptions(fieldName: string) {
	const response = await jiraFetch(`/field-options/${fieldName}`);

	if (!response.ok) {
		return [];
	}

	return response.json();
}

export async function getIssue(issueKey: string) {
	return jiraFetchJSON(`/issue/${issueKey}`);
}

export async function getProductVersions() {
	const response = await jiraFetch('/product-versions');

	if (!response.ok) {
		return [];
	}

	return response.json();
}

export async function getSecurityVulnerabilityAffectedVersions(): Promise<
	string[]
> {
	return jiraFetchJSON('/security-vulnerabilities/affected-versions');
}

export async function searchSecurityVulnerabilities(queryString: string) {
	return jiraFetchJSON(`/security-vulnerabilities/search?${queryString}`);
}

export async function updateBusinessEvent(
	externalReferenceCode: string,
	id: string,
	fieldsToPatch: any
) {
	return jiraFetchJSON(
		`/accounts/${externalReferenceCode}/business-events/${id}`,
		{
			body: JSON.stringify(fieldsToPatch),
			headers: {'Content-Type': 'application/json'},
			method: 'PUT',
		}
	);
}
