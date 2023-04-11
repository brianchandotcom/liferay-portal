import {upperCase} from './shared-utils.js';

class HeaderWebComponent extends HTMLElement {
	constructor() {
		super();

		const root = document.createElement('div');

		root.innerHTML = `<div class="cx-header">${upperCase('header')}</div>`;

		this.appendChild(root);
	}
}

const HEADER_ELEMENT_ID = 'header-liferay-sample-multi-extension-container-with-shared-code';

if (!customElements.get(HEADER_ELEMENT_ID)) {
	customElements.define(HEADER_ELEMENT_ID, HeaderWebComponent);
}
