import React from 'react';
import ReactDOM from 'react-dom';

import HelloBar from './routes/hello-bar/pages/HelloBar';
import HelloFoo from './routes/hello-foo/pages/HelloFoo';
import HelloWorld from './routes/hello-world/pages/HelloWorld';
import './common/styles/index.scss';
import { Liferay } from './common/services/liferay/liferay';
import api from './common/services/liferay/api';

const App = ({ route }) => {
	if (route === "hello-bar") {
		return <HelloBar />;
	}

	if (route === "hello-foo") {
		return <HelloFoo />;
	}

	return <HelloWorld />;
};

class WebComponent extends HTMLElement {
	connectedCallback() {
		ReactDOM.render(
			<App route={this.getAttribute("route")} />,
			this
		);

		api('o/headless-admin-user/v1.0/my-user-account')
			.then(response => response.json())
			.then(res => {
				let nameEls = document.getElementsByClassName('hello-world-name');
				if (nameEls.length > 0){
					if (res.givenName) {
						nameEls[0].innerHTML = res.givenName;
					}
				}
		});

		if (Liferay.ThemeDisplay.isSignedIn()) {
			fetch(
				"http://localhost:8081/random/number"
			).then(response => response.json())
				.then(res => {
					let nameEls = document.getElementsByClassName('random-number');
					if (nameEls.length > 0){
						if (res) {
							console.log("RESULT: ", res);
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
