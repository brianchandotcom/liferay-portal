/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import leafletCSS from 'leaflet/dist/leaflet.css?inline';
import {StrictMode} from 'react';
import {createRoot} from 'react-dom/client';

import App from './App.jsx';
import appCSS from './App.css?inline';

const SLOT_CSS = '::slotted(map-item) { display: contents; }';

class WebComponent extends HTMLElement {
	connectedCallback() {
		if (!this.shadow) {
			this.shadow = this.attachShadow({mode: 'open'});

			for (const css of [leafletCSS, appCSS, SLOT_CSS]) {
				const style = document.createElement('style');

				style.textContent = css;

				this.shadow.appendChild(style);
			}

			this.mountPoint = document.createElement('div');

			this.shadow.appendChild(this.mountPoint);
		}

		this.root = createRoot(this.mountPoint);

		this.root.render(
			<StrictMode>
				<App items={this._getMapItems()} />
			</StrictMode>
		);
	}

	disconnectedCallback() {
		this.root.unmount();

		delete this.root;
	}

	_getMapItems() {
		return Array.from(this.querySelectorAll('map-item')).map(
			(element, index) => {
				const slot = element.getAttribute('slot') ?? `item-${index}`;

				return {
					id: element.getAttribute('id') ?? slot,
					latitude: element.getAttribute('latitude'),
					longitude: element.getAttribute('longitude'),
					slot,
				};
			}
		);
	}
}

const ELEMENT_ID =
	'clarity-solution-custom-element-distributors-map-osm-with-slots';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
