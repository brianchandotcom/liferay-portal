class SidebarWebComponent extends HTMLElement {
	constructor() {
		super();

		const root = document.createElement('div');

		root.innerHTML = '<div class="cx-sidebar">Sidebar</div>';

		this.appendChild(root);
	}
}

const SIDEBAR_ELEMENT_ID =
	'liferay-sample-etc-frontend-1-custom-element-sidebar';

if (!customElements.get(SIDEBAR_ELEMENT_ID)) {
	customElements.define(SIDEBAR_ELEMENT_ID, SidebarWebComponent);
}
