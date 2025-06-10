/* global Liferay */

import React from 'react';
import { createRoot } from 'react-dom/client';
import ManageView from "./components/streams/ManageView";
import {ClayIconSpriteContext} from '@clayui/icon';
import {ClayModalProvider} from '@clayui/modal';
import './index.css';

class ManageNotifications extends HTMLElement {

    connectedCallback() {

        this.root = createRoot(this);

        this.root.render( <ClayIconSpriteContext.Provider value={Liferay.Icons.spritemap}>
            <ClayModalProvider>
                <ManageView />
            </ClayModalProvider>
        </ClayIconSpriteContext.Provider>, this);
    }
    disconnectedCallback() {

        this.root.unmount();

        delete this.root;

    }
}


const ELEMENT_ID = 'lr-sh-ui-stream-manage';

if (!customElements.get(ELEMENT_ID)) {

    customElements.define(ELEMENT_ID, ManageNotifications);

}
