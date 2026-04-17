/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '.';
import {fetcher} from './fetcher';

const HEADLESS_DELIVERY_BASE_URL_ = `${window.location.origin}/o/headless-delivery/v1.0`;
const HEADLESS_BASE_URL = `${window.location.origin}/o/`;

const liferayHeaders = () => ({
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
	'x-csrf-token': Liferay.authToken,
});

const cacheHeaders = () => ({
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Cache-Control': 'max-age=30, stale-while-revalidate=30',
	'x-csrf-token': Liferay.authToken,
});

const fetchHeadless = async ({
	resolveAsJson = true,
	url,
}: {
	resolveAsJson?: boolean;
	url: string;
}) => {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(`${HEADLESS_DELIVERY_BASE_URL_}${url}`, {
		headers: cacheHeaders(),
	});

	if (resolveAsJson) {
		return response.json();
	}

	return response;
};

const getHighPriorityContacts = async (filter: string) => {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${HEADLESS_BASE_URL}${`c/highprioritycontacts/?nestedFields=user&filter=${filter}`}`,
		{headers: cacheHeaders()}
	);

	return response.json();
};

const getTicketAttachmentById = async (id: string, fields: string) => {
	return fetcher(
		`${HEADLESS_BASE_URL}${`c/ticketattachments/${id}?fields=${fields}`}`,
		{headers: cacheHeaders(), method: 'GET'}
	);
};

const getTicketAttachments = async (filter: string) => {
	return fetcher(
		`${HEADLESS_BASE_URL}${`c/ticketattachments?filter=${filter}`}`,
		{headers: cacheHeaders(), method: 'GET'}
	);
};

const normalizeLegacyEvent = (event: any) => {
	if (!event) {
		return event;
	}

	const {actualGoLiveDateTime, targetGoLiveDateTime, ...rest} = event;

	return {
		...rest,
		actualEventDate: actualGoLiveDateTime ?? rest.actualEventDate,
		plannedEventDate: targetGoLiveDateTime ?? rest.plannedEventDate,
	};
};

const normalizeLegacyResponse = (response: any) => {
	if (response?.items) {
		return {
			...response,
			items: response.items.map(normalizeLegacyEvent),
		};
	}

	return normalizeLegacyEvent(response);
};

const denormalizeLegacyEvent = (event: any) => {
	if (!event) {
		return event;
	}

	const {actualEventDate, plannedEventDate, ...rest} = event;

	return {
		...rest,
		actualGoLiveDateTime: actualEventDate ?? rest.actualGoLiveDateTime,
		targetGoLiveDateTime: plannedEventDate ?? rest.targetGoLiveDateTime,
	};
};

const createBusinessEventLegacy = async (businessEvent: any) => {
	return fetcher(`${HEADLESS_BASE_URL}c/businessevents/`, {
		body: JSON.stringify(denormalizeLegacyEvent(businessEvent)),
		headers: liferayHeaders(),
		method: 'POST',
	});
};

const deleteBusinessEventLegacy = async (id: string | number) => {
	return fetcher(`${HEADLESS_BASE_URL}c/businessevents/${id}`, {
		headers: liferayHeaders(),
		method: 'DELETE',
	});
};

const getBusinessEventByIdLegacy = async (id: string | number) => {
	const response = await fetcher(
		`${HEADLESS_BASE_URL}c/businessevents/${id}`,
		{headers: liferayHeaders(), method: 'GET'}
	);

	return normalizeLegacyEvent(response);
};

const getBusinessEventsLegacy = async (filterQuery: string) => {
	const response = await fetcher(
		`${HEADLESS_BASE_URL}c/businessevents?${filterQuery}`,
		{headers: liferayHeaders(), method: 'GET'}
	);

	return normalizeLegacyResponse(response);
};

const getBusinessEventVersionsLegacy = async (filters: string) => {
	return fetcher(`${HEADLESS_BASE_URL}c/businesseventversions?${filters}`, {
		headers: liferayHeaders(),
		method: 'GET',
	});
};

const getListTypeEntriesLegacy = async (name: string) => {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${HEADLESS_BASE_URL}headless-admin-list-type/v1.0/list-type-definitions?filter=${encodeURIComponent(`name eq '${name}'`)}`,
		{headers: liferayHeaders()}
	);

	const data = await response.json();

	const entries = data?.items?.[0]?.listTypeEntries ?? [];

	return entries.map((entry: {key: string; name: string}) => ({
		key: entry.key,
		name: entry.name,
	}));
};

const updateBusinessEventLegacy = async (
	id: string | number,
	fieldsToPatch: any
) => {
	return fetcher(`${HEADLESS_BASE_URL}c/businessevents/${id}`, {
		body: JSON.stringify(denormalizeLegacyEvent(fieldsToPatch)),
		headers: liferayHeaders(),
		method: 'PATCH',
	});
};

export {
	createBusinessEventLegacy,
	deleteBusinessEventLegacy,
	fetchHeadless,
	getBusinessEventByIdLegacy,
	getBusinessEventVersionsLegacy,
	getBusinessEventsLegacy,
	getHighPriorityContacts,
	getListTypeEntriesLegacy,
	getTicketAttachmentById,
	getTicketAttachments,
	updateBusinessEventLegacy,
};
