/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EventSource} from 'eventsource';

import type {AuthorizationToken, ChatbotConfiguration} from './types';

const AI_HUB_ENDPOINT = '/o/ai-hub/v1.0';

const AUTHORIZATION_TOKEN_TTL = 9 * 60 * 1000;

let aiHubURL = '';
let cachedAuthorizationToken: AuthorizationToken | null = null;
let cachedAuthorizationTokenMintedAt = 0;
let pendingAuthorizationTokenPromise: Promise<AuthorizationToken | null> | null =
	null;

export function setAIHubURL(url: string) {
	aiHubURL = url;
}

async function postAuthorizationToken(): Promise<AuthorizationToken | null> {
	try {
		const csrfToken = (window as any).Liferay?.authToken;

		const response = await fetch(
			`${aiHubURL}/o/ai-hub-cell/v1.0/authorization-tokens`,
			{
				headers: csrfToken
					? new Headers({'x-csrf-token': csrfToken})
					: undefined,
				method: 'POST',
			}
		);

		if (!response.ok) {
			throw new Error(
				`Unable to generate authorization token: ${response.statusText}`
			);
		}

		const data = (await response.json()) as Partial<AuthorizationToken>;

		if (!data?.accessToken) {
			throw new Error('Unable to generate authorization token.');
		}

		if (!data?.userToken) {
			throw new Error('Unable to generate user token.');
		}

		if (!data?.serviceURL) {
			throw new Error('Unable to find service URL.');
		}

		return data as AuthorizationToken;
	}
	catch (error) {
		console.warn((error as Error).message);

		return null;
	}
}

async function getAuthorizationToken(): Promise<AuthorizationToken | null> {
	const now = Date.now();

	if (
		cachedAuthorizationToken &&
		now - cachedAuthorizationTokenMintedAt < AUTHORIZATION_TOKEN_TTL
	) {
		return cachedAuthorizationToken;
	}

	if (!pendingAuthorizationTokenPromise) {
		pendingAuthorizationTokenPromise = postAuthorizationToken().then(
			(token) => {
				if (token) {
					cachedAuthorizationToken = token;
					cachedAuthorizationTokenMintedAt = Date.now();
				}

				pendingAuthorizationTokenPromise = null;

				return token;
			}
		);
	}

	return pendingAuthorizationTokenPromise;
}

export async function getChatbotConfiguration(
	chatbotExternalReferenceCode: string
): Promise<ChatbotConfiguration> {
	const authorizationToken = await getAuthorizationToken();

	if (!authorizationToken) {
		throw new Error(
			'Unable to obtain authorization token for chatbot configuration.'
		);
	}

	const response = await fetch(
		`${authorizationToken.serviceURL}/o/ai-hub/chatbots/by-external-reference-code/${chatbotExternalReferenceCode}`,
		{
			headers: new Headers({
				Accept: 'application/json',
			}),
		}
	);

	if (!response.ok) {
		throw new Error(
			`Unable to fetch chatbot configuration: ${response.statusText}`
		);
	}

	return (await response.json()) as ChatbotConfiguration;
}

export async function createEventSource(): Promise<EventSource | null> {
	const authorizationToken = await getAuthorizationToken();

	if (!authorizationToken) {
		return null;
	}

	return new EventSource(
		`${authorizationToken.serviceURL}${AI_HUB_ENDPOINT}/chats/subscribe`,
		{
			fetch: (input, init) =>
				fetch(input as RequestInfo, {
					...init,
					headers: new Headers({
						Accept: 'text/event-stream',
						Authorization: `Bearer ${authorizationToken.accessToken}`,
					}),
				}),
			withCredentials: true,
		}
	);
}

export async function postChatMessage(
	chatbotExternalReferenceCode: string,
	eventSourceReference: string,
	text: string
): Promise<Response> {
	const authorizationToken = await getAuthorizationToken();

	if (!authorizationToken) {
		throw new Error(
			'Unable to obtain authorization token for chat message.'
		);
	}

	return fetch(
		`${authorizationToken.serviceURL}${AI_HUB_ENDPOINT}/chats/by-external-reference-code/${eventSourceReference}/messages`,
		{
			body: JSON.stringify({
				chatbotExternalReferenceCode,
				context: {},
				instructionDefinitionScope: 'clickToChat',
				text,
			}),
			headers: new Headers({
				'Accept': 'application/json',
				'Authorization': `Bearer ${authorizationToken.accessToken}`,
				'Content-Type': 'application/json',
				'Liferay-AI-Hub-Cell-On-Behalf-Of':
					authorizationToken.userToken,
			}),
			method: 'POST',
		}
	);
}
