/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EventSource} from 'eventsource';

import type {ChatbotConfiguration} from './types';

let aiHubURL = '';

export function setAIHubURL(url: string) {
	aiHubURL = url;
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

export function createEventSource(): EventSource {
	return new EventSource(`${aiHubURL}/o/ai-hub/v1.0/chats/subscribe`, {
		fetch: (input, init) =>
			fetch(input as RequestInfo, {
				...init,
				headers: new Headers({
					Accept: 'text/event-stream',
				}),
			}),
		withCredentials: true,
	});
}

export function postChatMessage(
	chatbotExternalReferenceCode: string,
	eventSourceReference: string,
	text: string
): Promise<Response> {
	return fetch(
		`${aiHubURL}/o/ai-hub/v1.0/chats/by-external-reference-code/${eventSourceReference}/messages`,
		{
			body: JSON.stringify({
				chatbotExternalReferenceCode,
				context: {},
				instructionDefinitionScope: 'clickToChat',
				text,
			}),
			headers: new Headers({
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			}),
			method: 'POST',
		}
	);
}
