/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EventSource} from 'eventsource';

import type {AuthorizationToken, ChatbotConfiguration} from './types';

const AI_HUB_ENDPOINT = '/o/ai-hub/v1.0';

let aiHubURL = '';
let liferayDXPURL = '';

export function setURLs(aiHub: string, liferayDXP: string) {
	aiHubURL = aiHub;
	liferayDXPURL = liferayDXP;
}

async function postAuthorizationToken(): Promise<AuthorizationToken | null> {
	try {
		const csrfToken = (window as any).Liferay?.authToken;

		const response = await fetch(
			`${liferayDXPURL}/o/ai-hub-cell/v1.0/authorization-tokens`,
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
		console.warn(error instanceof Error ? error.message : String(error));

		return null;
	}
}

export async function getChatbotConfiguration(
	chatbotExternalReferenceCode: string
): Promise<ChatbotConfiguration> {
	const response = await fetch(
		`${aiHubURL}/o/ai-hub/chatbots/by-external-reference-code/${chatbotExternalReferenceCode}`,
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
	return new EventSource(`${aiHubURL}${AI_HUB_ENDPOINT}/chats/subscribe`, {
		fetch: async (input, init) => {
			const headers = new Headers({Accept: 'text/event-stream'});

			if ((window as any).Liferay) {
				const authorizationToken = await postAuthorizationToken();

				if (authorizationToken?.accessToken) {
					headers.set(
						'Authorization',
						`Bearer ${authorizationToken.accessToken}`
					);
				}
			}

			return fetch(input as RequestInfo, {
				...init,
				headers,
			});
		},
		withCredentials: true,
	});
}

export async function postChatMessage(
	chatbotExternalReferenceCode: string,
	eventSourceReference: string,
	text: string
): Promise<Response> {
	const headers = new Headers({
		'Accept': 'application/json',
		'Content-Type': 'application/json',
	});

	if ((window as any).Liferay) {
		const authorizationToken = await postAuthorizationToken();

		if (!authorizationToken) {
			throw new Error(
				'Unable to obtain authorization token for chat message.'
			);
		}

		headers.set(
			'Authorization',
			`Bearer ${authorizationToken.accessToken}`
		);
		headers.set(
			'Liferay-AI-Hub-Cell-On-Behalf-Of',
			authorizationToken.userToken
		);
	}

	return fetch(
		`${aiHubURL}${AI_HUB_ENDPOINT}/chats/by-external-reference-code/${eventSourceReference}/messages`,
		{
			body: JSON.stringify({
				chatbotExternalReferenceCode,
				context: {},
				instructionDefinitionScope: 'clickToChat',
				text,
			}),
			headers,
			method: 'POST',
		}
	);
}
