import React from 'react';
import ReactDOM from 'react-dom';

import HelloBar from './routes/hello-bar/pages/HelloBar';
import HelloFoo from './routes/hello-foo/pages/HelloFoo';
import Joke from './common/components/Joke';
import HelloWorld from './routes/hello-world/pages/HelloWorld';
import './common/styles/index.scss';
import api from './common/services/liferay/api';
import { Liferay } from './common/services/liferay/liferay';

const App = ({ oAuth2Client, route }) => {
	if (route === "hello-bar") {
		return <HelloBar />;
	}

	if (route === "hello-foo") {
		return <HelloFoo />;
	}

	return (
		<div>
			<HelloWorld />
			{Liferay.ThemeDisplay.isSignedIn() &&
				<div>
					<Joke oAuth2Client={oAuth2Client} />
				</div>
			}
		</div>
  );
};

class WebComponent extends HTMLElement {
	constructor() {
		super();

		this.oAuth2Client = Liferay.OAuth2Client.FromUserAgentApplication('easy-oauth-application-user-agent');
	}

	connectedCallback() {
		ReactDOM.render(
			<App oAuth2Client={this.oAuth2Client} route={this.getAttribute("route")} />,
			this
		);
		if (Liferay.ThemeDisplay.isSignedIn()) {
			api(
				'o/headless-admin-user/v1.0/my-user-account'
			).then(
				res => res.json()
			).then(res => {
				let nameEls = document.getElementsByClassName('hello-world-name');
				if (nameEls.length > 0){
					if (res.givenName) {
						nameEls[0].innerHTML = res.givenName;
					}
				}
			});
		}
	}
}

const ELEMENT_ID = 'fox-remote-app';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
