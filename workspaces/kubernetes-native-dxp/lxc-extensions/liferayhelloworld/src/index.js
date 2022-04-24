import React from 'react';
import ReactDOM from 'react-dom';

import HelloWorld from './routes/hello-world/pages/HelloWorld';
import './common/styles/index.scss';
import api from './common/services/liferay/api';
import { Liferay } from './common/services/liferay/liferay';
import WebClient from './common/services/liferay/webclient';
import NameForm from './common/components/NameForm';

const App = ({ webClient }) => {
	return (
		<div>
			<HelloWorld />
			<NameForm webClient={webClient} />
		</div>
	);
};

class WebComponent extends HTMLElement {
	constructor() {
		super();

		this.webClient = WebClient.FromUserAgentApplication('customrestservice');
	}

	connectedCallback() {
		ReactDOM.render(
			<App webClient={this.webClient} />,
			this
		);

		if (Liferay.ThemeDisplay.isSignedIn()) {
			api(
				'o/headless-admin-user/v1.0/my-user-account'
			).then(res => {
				let nameEls = document.getElementsByClassName('hello-world-name');
				if (nameEls.length > 0){
					if (res.givenName) {
						nameEls[0].innerHTML = res.givenName;
					}
				}
			});

			this.webClient.fetch(
				'random/number'
			).then(res => {
				let nameEls = document.getElementsByClassName('random-number');
				if (nameEls.length > 0) {
					if (res) {
						nameEls[0].innerHTML = res;
					}
				}
			});
		}
	}
}

const ELEMENT_ID = 'liferay-hello-world';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
