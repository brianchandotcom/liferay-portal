/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface ChatbotConfig {
	active: boolean;
	introMessage: string;
	notificationMessage: string;
	placeholderMessage: string;
	title: string;
}

export interface ChatMessage {
	sender: 'assistant' | 'error' | 'user';
	text: string;
}

export interface WidgetConfiguration {
	aiHubURL: string;
	chatbotExternalReferenceCode: string;
}
