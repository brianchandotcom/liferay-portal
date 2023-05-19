import React from 'react';
import ReactDOM from 'react-dom';

class CustomElement extends HTMLElement {
	constructor() {
		super();

		const root = document.createElement('div');

		const Greeting = React.createElement(
			'h1',
			{className: 'greeting'},
			'Hello ',
			React.createElement('i', null, name),
			'. Welcome!'
		);

		ReactDOM.render(Greeting, root);

		this.appendChild(root);
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
