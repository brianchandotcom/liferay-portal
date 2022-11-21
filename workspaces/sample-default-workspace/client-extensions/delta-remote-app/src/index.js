import React from 'react';
import ReactDOM from 'react-dom';

import AppComponent from './AppComponent';

class CustomElement extends HTMLElement {
	constructor() {
		super();

		const root = document.createElement('div');

		ReactDOM.render(
			<AppComponent/>, 
			root
		);

		this.attachShadow({mode: 'open'}).appendChild(root);
	}

	connectedCallback() {
	}

	disconnectedCallback() {
	}

}

if (customElements.get('delta-remote-app-element')) {
	console.log(
		'Skipping registration for <delta-remote-app-element> (already registered)'
	);
} else {
	customElements.define('delta-remote-app-element', CustomElement);
}
