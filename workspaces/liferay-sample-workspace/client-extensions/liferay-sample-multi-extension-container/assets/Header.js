class HeaderWebComponent extends HTMLElement {
	constructor() {
		super();

		const root = document.createElement('div');

		root.innerHTML = '<div class="cx-header">Header</div>';

		this.appendChild(root);
	}
}

const HEADER_ELEMENT_ID = 'sidebar-liferay-sample-multi-extension-container';

if (!customElements.get(HEADER_ELEMENT_ID)) {
	customElements.define(HEADER_ELEMENT_ID, HeaderWebComponent);
}