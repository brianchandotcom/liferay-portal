/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import React, {Suspense} from 'react';
import {Root, createRoot} from 'react-dom/client';

import ErrorBoundary from './components/ErrorBoundary';
import {PropertiesProvider} from './context/PropertiesContext';
import {getIconSpriteMap} from './liferay/constants';
import {baseAttributes, getAttributes} from './utils/attributes';

type RouterComponent = React.ComponentType;

const routers: Record<string, React.LazyExoticComponent<RouterComponent>> = {
	'admin': React.lazy(() => import('./pages/Admin/AdminRouter')),
	'my-account': React.lazy(() => import('./pages/MyAccount/MyAccountRouter')),
};

class WebComponent extends HTMLElement {
	private root: Root | undefined;

	static observedAttributes = [...baseAttributes];

	private renderApp() {
		const Router = routers[this.getAttribute('route') ?? ''];

		if (!Router || !this.root) {
			return;
		}

		this.root.render(
			<ErrorBoundary>
				<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
					<PropertiesProvider value={getAttributes(this)}>
						<Suspense fallback={null}>
							<Router />
						</Suspense>
					</PropertiesProvider>
				</ClayIconSpriteContext.Provider>
			</ErrorBoundary>
		);
	}

	connectedCallback() {
		if (!this.root) {
			const route = this.getAttribute('route') ?? '';
			const Router = routers[route];

			if (!Router) {
				console.error(
					`[liferay-one-custom-element] Unknown or missing "route" attribute: "${route}". Expected one of: ${Object.keys(routers).join(', ')}`
				);

				return;
			}

			this.root = createRoot(this);
		}

		this.renderApp();
	}

	attributeChangedCallback() {
		this.renderApp();
	}

	disconnectedCallback() {
		this.root?.unmount();
		this.root = undefined;
	}
}

const ELEMENT_ID = 'liferay-one-custom-element';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
