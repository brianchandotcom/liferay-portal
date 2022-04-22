import React from 'react';
import ReactDOM from 'react-dom';

import HelloBar from './routes/hello-bar/pages/HelloBar';
import HelloFoo from './routes/hello-foo/pages/HelloFoo';
import HelloWorld from './routes/hello-world/pages/HelloWorld';
import './common/styles/index.scss';
import { Liferay } from './common/services/liferay/liferay';
import WebClient from './common/services/liferay/webclient';
import NameForm from './common/components/NameForm';

const App = ({ route }) => {
	if (route === "hello-bar") {
		return <HelloBar />;
	}

	if (route === "hello-foo") {
		return <HelloFoo />;
	}

	return (
		<div>
			<HelloWorld />
			<NameForm />
		</div>
	);
};

class WebComponent extends HTMLElement {
	constructor() {
		super();

		this.customRestApp = Liferay.OAuth.getUserAgentApplication('custom-rest-service');

		this.webClient = new WebClient({
			clientId:  this.customRestApp.clientId
		});
	}

	webClient() {
		return this.webClient;
	}

	connectedCallback() {
		ReactDOM.render(
			<App route={this.getAttribute("route")} />,
			this
		);

		if (Liferay.ThemeDisplay.isSignedIn()) {
			this.webClient.fetch(
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
				`${this.customRestApp.homePageURL}/random/number`
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
