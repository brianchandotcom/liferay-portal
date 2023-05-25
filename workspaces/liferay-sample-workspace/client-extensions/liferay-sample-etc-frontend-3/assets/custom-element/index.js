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

import {getBye, getHello} from 'my-utils';

class CustomElement extends HTMLElement {
	constructor() {
		super();

		const root = document.createElement('pre');

		root.innerHTML = `
Grettings in:

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
