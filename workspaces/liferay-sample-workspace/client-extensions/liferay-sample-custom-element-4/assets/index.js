/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import ReactDOM from 'react-dom';

const api = async (url, options = {}) => {
	return fetch(window.location.origin + '/' + url, {
		headers: {
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		...options,
	});
};

function Greeting() {
	return React.createElement(
		'h1',
		{className: 'greeting'},
		'Hello ',
		React.createElement('i', null, name),
		'. Welcome!'
	);
}

class CustomElement extends HTMLElement {
	connectedCallback() {
		ReactDOM.render(React.createElement(Greeting), this);

		if (Liferay.ThemeDisplay.isSignedIn()) {
			api('o/headless-admin-user/v1.0/my-user-account')
				.then((response) => response.json())
				.then((response) => {
					if (response.givenName) {
						const nameElements = document.getElementsByTagName('i');

						if (nameElements.length) {
							nameElements[0].innerHTML = response.givenName;
						}
					}
				})
				.catch((error) => {
					// eslint-disable-next-line no-console
					console.log(error);
				});
		}
	}

	disconnectedCallback() {
		ReactDOM.unmountComponentAtNode(this);
	}
}

const ELEMENT_NAME = 'liferay-sample-custom-element-4';

if (customElements.get(ELEMENT_NAME)) {
	// eslint-disable-next-line no-console
	console.log(
		'Skipping registration for <liferay-sample-custom-element-4> (already registered)'
	);
}
else {
	customElements.define(ELEMENT_NAME, CustomElement);
}
