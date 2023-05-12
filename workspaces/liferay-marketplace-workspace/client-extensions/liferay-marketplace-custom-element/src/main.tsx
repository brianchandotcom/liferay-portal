import React from 'react';
import ReactDOM from 'react-dom';

import App from './App';
import {AppContextProvider} from './manage-app-state/AppManageState';

const GRAVATAR_API = `https://www.gravatar.com/avatar`;

class WebComponent extends HTMLElement {
	connectedCallback() {
		// eslint-disable-next-line @liferay/portal/no-react-dom-render
		ReactDOM.render(
			<React.StrictMode>
				<AppContextProvider gravatarAPI={GRAVATAR_API}>
					<App route={this.getAttribute('route') || '/'} />
				</AppContextProvider>
			</React.StrictMode>,
			this
		);
	}
}
const ELEMENT_ID = 'liferay-marketplace-custom-element';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
