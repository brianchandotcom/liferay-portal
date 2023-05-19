/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

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
