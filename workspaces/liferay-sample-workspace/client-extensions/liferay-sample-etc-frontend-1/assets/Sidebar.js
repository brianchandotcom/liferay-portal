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
