/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
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
