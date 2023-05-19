import {upperCase} from './shared-utils';

class HeaderWebComponent extends HTMLElement {
	constructor() {
		super();

		const root = document.createElement('div');

		root.innerHTML = `<div class="cx-header">${upperCase('header')}</div>`;

		this.appendChild(root);
	}
}

const HEADER_ELEMENT_ID = 'liferay-sample-etc-frontend-2-custom-element';

if (!customElements.get(HEADER_ELEMENT_ID)) {
	customElements.define(HEADER_ELEMENT_ID, HeaderWebComponent);
}
