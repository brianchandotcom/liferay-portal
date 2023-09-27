/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// This is imported via "import maps". See how that client extension is
// declared in `client-extension.yaml`.

import {getBye, getHello} from 'my-utils';

class CustomElement extends HTMLElement {
	constructor() {
		super();

		const root = document.createElement('pre');

		root.innerHTML = `
Greetings in:

 · English:    ${getHello('en')}
 · French:     ${getHello('fr')}
 · Italian:    ${getHello('it')}
 · Portuguese: ${getHello('pt')}
 · Spanish:    ${getHello('es')}


Farewell in:

 · English:    ${getBye('en')}
 · French:     ${getBye('fr')}
 · Italian:    ${getBye('it')}
 · Portuguese: ${getBye('pt')}
 · Spanish:    ${getBye('es')}
`;

		this.attachShadow({mode: 'open'}).appendChild(root);
	}
}

if (!customElements.get('liferay-sample-etc-frontend-3-custom-element')) {
	customElements.define(
		'liferay-sample-etc-frontend-3-custom-element',
		CustomElement
	);
}
