/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Root, createRoot} from 'react-dom/client';

import App from './App';
import {AppContextProvider} from './manage-app-state/AppManageState';

const GRAVATAR_API = `https://www.gravatar.com/avatar`;

class WebComponent extends HTMLElement {
	private root: Root | undefined;

	connectedCallback() {
		if (!this.root) {
			this.root = createRoot(this);

			this.root.render(
				<React.StrictMode>
					<AppContextProvider gravatarAPI={GRAVATAR_API}>
						<App route={this.getAttribute('route') || '/'} />
					</AppContextProvider>
				</React.StrictMode>
			);
		}
	}
}
const ELEMENT_ID = 'liferay-marketplace-custom-element';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
