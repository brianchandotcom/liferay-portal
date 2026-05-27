/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface AuthorizationToken {
	accessToken: string;
	scope: string;
	serviceURL: string;
	userToken: string;
}

export interface ChatbotConfiguration {
	active: boolean;
	avatar?: {
		fileURL: string;
	};
	defaultLanguageId: string;
	disclaimerMessage_i18n?: Record<string, string>;
	introMessage_i18n?: Record<string, string>;
	notificationMessage_i18n?: Record<string, string>;
	placeholderMessage_i18n?: Record<string, string>;
	title_i18n?: Record<string, string>;
}

export interface ChatMessage {
	sender: 'assistant' | 'error' | 'user';
	text: string;
	traceId?: string;
}

export interface WidgetConfiguration {
	aiHubURL: string;
	chatbotExternalReferenceCode: string;
	liferayDXPURL: string;
}
