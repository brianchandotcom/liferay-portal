/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import React from 'react';
import {createRoot} from 'react-dom/client';

import {setURLs} from './api';
import atlasCss from './assets/atlas.css?inline';
import iconsSpriteMap from './assets/icons.svg?raw';
import ChatbotWidget from './components/ChatbotWidget';
import widgetCss from './index.css?inline';
import ShadowPortalContext from './shadow';
import {WidgetConfiguration} from './types';

const CHATBOT_HOST_ID = 'aihub-chatbot-host';
const CHATBOT_WIDGET_ID = 'aihub-chatbot-widget';

if (!document.getElementById(CHATBOT_HOST_ID)) {
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

		const hostElement = document.createElement('div');

		hostElement.id = CHATBOT_HOST_ID;

		document.body.appendChild(hostElement);

		const shadowRoot = hostElement.attachShadow({mode: 'open'});

		const styleElement = document.createElement('style');

		styleElement.textContent =
			atlasCss.replace(/:root/g, ':host') + '\n' + widgetCss;

		shadowRoot.appendChild(styleElement);

		const mountNode = document.createElement('div');

		mountNode.id = CHATBOT_WIDGET_ID;

		shadowRoot.appendChild(mountNode);

		const portalNode = document.createElement('div');

		portalNode.className = 'aihub-shadow-portal';

		shadowRoot.appendChild(portalNode);

		const portalContainerRef = {current: portalNode};

		const spritemapNode = document.createElement('div');

		spritemapNode.hidden = true;
		spritemapNode.innerHTML = iconsSpriteMap;

		shadowRoot.appendChild(spritemapNode);

		setURLs(
			widgetConfiguration.aiHubURL,
			widgetConfiguration.liferayDXPURL
		);

		createRoot(mountNode).render(
			<ShadowPortalContext.Provider value={portalContainerRef}>
				<ClayIconSpriteContext.Provider value="">
					<ChatbotWidget widgetConfiguration={widgetConfiguration} />
				</ClayIconSpriteContext.Provider>
			</ShadowPortalContext.Provider>
		);
	}
}
