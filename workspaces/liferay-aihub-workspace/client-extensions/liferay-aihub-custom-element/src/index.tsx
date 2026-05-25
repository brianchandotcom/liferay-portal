/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {createRoot} from 'react-dom/client';

import {setURLs} from './api';
import ChatbotWidget from './components/ChatbotWidget';
import {WidgetConfiguration} from './types';

import './index.css';

const CHATBOT_WIDGET_ID = 'aihub-chatbot-widget';

if (!document.getElementById(CHATBOT_WIDGET_ID)) {
	const scriptTag = document.getElementById('aihub-chatbot-widget-script');

	if (!scriptTag) {
		console.error(
			'Element with id="aihub-chatbot-widget-script" not found'
		);
	}
	else {
		const widgetConfiguration: WidgetConfiguration = {
			aiHubURL: scriptTag.getAttribute('ai-hub-url') || '',
			chatbotExternalReferenceCode:
				scriptTag.getAttribute('chatbot-external-reference-code') || '',
			liferayDXPURL: scriptTag.getAttribute('liferay-dxp-url') || '',
		};

		const element = document.createElement('div');

		element.id = CHATBOT_WIDGET_ID;

		document.body.appendChild(element);

		setURLs(widgetConfiguration.aiHubURL, widgetConfiguration.liferayDXPURL);

		createRoot(element).render(
			<ChatbotWidget widgetConfiguration={widgetConfiguration} />
		);
	}
}
