/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import {ClayModalProvider} from '@clayui/modal';
import React from 'react';
import {createRoot} from 'react-dom/client';

import './index.css';
import ChatBotManageView from './components/chat-bot-manage/ManageView';
import MainWindow from './components/instant-messaging/MainWindow';

const ELEMENT_ID_INSTANT_MESSAGING = 'liferay-instant-messaging-ui';
const ELEMENT_ID_CHAT_BOT_MANAGE = 'liferay-chat-bot-manage';

class InstantMessagingMainWindow extends HTMLElement {
	connectedCallback() {
		this.root = createRoot(this);

		this.root.render(
			<ClayIconSpriteContext.Provider value={Liferay.Icons.spritemap}>
				<ClayModalProvider>
					<MainWindow />
				</ClayModalProvider>
			</ClayIconSpriteContext.Provider>,
			this
		);
	}
	disconnectedCallback() {
		this.root.unmount();

		delete this.root;
	}
}

class ChatBotManageViewComponent extends HTMLElement {
	connectedCallback() {
		this.root = createRoot(this);

		this.root.render(
			<ClayIconSpriteContext.Provider value={Liferay.Icons.spritemap}>
				<ClayModalProvider>
					<ChatBotManageView />
				</ClayModalProvider>
			</ClayIconSpriteContext.Provider>,
			this
		);
	}
	disconnectedCallback() {
		this.root.unmount();

		delete this.root;
	}
}

if (!customElements.get(ELEMENT_ID_INSTANT_MESSAGING)) {
	customElements.define(
		ELEMENT_ID_INSTANT_MESSAGING,
		InstantMessagingMainWindow
	);
}

if (!customElements.get(ELEMENT_ID_CHAT_BOT_MANAGE)) {
	customElements.define(
		ELEMENT_ID_CHAT_BOT_MANAGE,
		ChatBotManageViewComponent
	);
}
