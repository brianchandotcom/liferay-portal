/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
class HeaderWebComponent extends HTMLElement {
	constructor() {
		super();

		const root = document.createElement('div');

		root.innerHTML = '<div class="cx-header">Header</div>';

		this.appendChild(root);
	}
}

const HEADER_ELEMENT_ID = 'liferay-sample-etc-frontend-1-custom-element-header';

if (!customElements.get(HEADER_ELEMENT_ID)) {
	customElements.define(HEADER_ELEMENT_ID, HeaderWebComponent);
}
