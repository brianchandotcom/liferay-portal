/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// A jsImportMapsEntry only ships JavaScript, so the CSS is imported as strings
// and injected into the shadow root, where it neither leaks into the page nor
// gets overridden by it

import leafletCSS from 'leaflet/dist/leaflet.css?inline';
import {StrictMode} from 'react';
import {createRoot} from 'react-dom/client';

import App from './App.jsx';
import appCSS from './App.css?inline';

// Removes the <map-item> box so its children lay out directly in the tooltip

const SLOT_CSS = '::slotted(map-item) { display: contents; }';

// The <map-item> children stay in the light DOM, where the page author wrote
// them, and the browser projects each one into the <slot> of its marker's
// tooltip, so any HTML can be shown there without being copied

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

		this._render();

		// Fragments may move <map-item> children in after the first render, for
		// example out of a Collection Display

		this.observer = new MutationObserver(() => this._render());

		this.observer.observe(this, {childList: true});
	}

	disconnectedCallback() {
		this.observer.disconnect();
		this.root.unmount();

		delete this.observer;
		delete this.root;
	}

	// Only direct children can be assigned to a slot

	_getMapItems() {
		return Array.from(this.querySelectorAll(':scope > map-item')).map(
			(element, index) => {
				if (!element.slot) {
					element.slot = `item-${index}`;
				}

				const slot = element.slot;

				return {
					id: element.getAttribute('id') ?? slot,
					latitude: element.getAttribute('latitude'),
					longitude: element.getAttribute('longitude'),
					slot,
				};
			}
		);
	}

	_render() {
		this.root.render(
			<StrictMode>
				<App items={this._getMapItems()} />
			</StrictMode>
		);
	}
}

const ELEMENT_ID =
	'clarity-solution-custom-element-distributors-map-osm-with-slots';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
