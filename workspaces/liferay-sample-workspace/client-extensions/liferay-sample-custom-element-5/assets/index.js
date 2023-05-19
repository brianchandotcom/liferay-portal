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

import ClayBadge from '@clayui/badge';
import React from 'react';
import ReactDOM from 'react-dom';

class CustomElement extends HTMLElement {
	constructor() {
		super();

		const root = document.createElement('div');

		this.appendChild(root);

		ReactDOM.render(
			React.createElement(ClayBadge, {
				displayType: 'success',
				label: 'Success!',
			}),
			root
		);
	}
}

const ELEMENT_NAME = 'liferay-sample-custom-element-5';

if (customElements.get(ELEMENT_NAME)) {
	// eslint-disable-next-line no-console
	console.log(
		`Skipping registration for <${ELEMENT_NAME}> (already registered)`
	);
}
else {
	customElements.define(ELEMENT_NAME, CustomElement);
}
