/* global Liferay */

import React from 'react';
import { createRoot } from 'react-dom/client';
import {ClayIconSpriteContext} from '@clayui/icon';
import {ClayModalProvider} from '@clayui/modal';
import './index.css';
import MainWindow from "./components/instant-messaging/MainWindow";
import ChatBotManageView from "./components/chat-bot-manage/ManageView";

const ELEMENT_ID_INSTANT_MESSAGING = 'liferay-instant-messaging-ui';
const ELEMENT_ID_CHAT_BOT_MANAGE = 'liferay-chat-bot-manage';



class InstantMessagingMainWindow extends HTMLElement {

    connectedCallback() {

        this.root = createRoot(this);

        this.root.render( <ClayIconSpriteContext.Provider value={Liferay.Icons.spritemap}>
            <ClayModalProvider>
                <MainWindow/>
            </ClayModalProvider>
        </ClayIconSpriteContext.Provider>, this);
    }
    disconnectedCallback() {

        this.root.unmount();

        delete this.root;

    }
}

class ChatBotManageViewComponent extends HTMLElement {

    connectedCallback() {

        this.root = createRoot(this);

        this.root.render( <ClayIconSpriteContext.Provider value={Liferay.Icons.spritemap}>
            <ClayModalProvider>
                <ChatBotManageView/>
            </ClayModalProvider>
        </ClayIconSpriteContext.Provider>, this);
    }
    disconnectedCallback() {

        this.root.unmount();

        delete this.root;

    }
}


if (!customElements.get(ELEMENT_ID_INSTANT_MESSAGING)) {

    customElements.define(ELEMENT_ID_INSTANT_MESSAGING, InstantMessagingMainWindow);

}

if (!customElements.get(ELEMENT_ID_CHAT_BOT_MANAGE)) {

    customElements.define(ELEMENT_ID_CHAT_BOT_MANAGE, ChatBotManageViewComponent);

}
