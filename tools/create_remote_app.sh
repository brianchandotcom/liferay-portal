#!/bin/bash

function check_usage {
	if [ ! "${#}" -eq 2 ]
	then
		echo "Usage: create_remote_app.sh <custom-element-name> <js-framework>"
		echo ""
		echo "  custom-element-name: liferay-hello-world"
		echo "  js-framework: react, vue2, vue3"
		echo ""
		echo "Example: create_remote_app.sh liferay-hello-world react"

		exit 1
	fi

	if [[ ${#1} -le 2 ]]
	then
		echo "Custom element name is too short."

		exit 1
	fi

	if [[ ${1} == *[A-Z]* ]]
	then
		echo "Custom element name must not contain upper case letters."

		exit 1
	fi

	if [[ ${1} != *-* ]]
	then
		echo "Custom element names must contain a dash."

		exit 1
	fi

	if [[ ${1} != *[a-z0-9] ]]
	then
		echo "Custom element names must end with a lower case letter or number."

		exit 1
	fi

	if [[ ${1} == *--* ]]
	then
		echo "Custom element names must not contain 2 dashes in a row."

		exit 1
	fi

	if [[ ${1} != [a-z]* ]]
	then
		echo "Custom element names must start with a lower case letter."

		exit 1
	fi

	if ! [[ ${1} =~ ^[a-z0-9-]+$ ]]
	then
		echo "Custom element name is not valid."

		exit 1
	fi
}

function check_utils {

	#
	# https://stackoverflow.com/a/677212
	#

	for util in "${@}"
	do
		command -v ${util} >/dev/null 2>&1 || { echo >&2 "The utility ${util} is not installed."; exit 1; }
	done
}

function create_react_app {
	check_utils yarn

	yarn create react-app ${REMOTE_APP_DIR}

	cd ${REMOTE_APP_DIR}

	yarn add sass
	yarn remove @testing-library/jest-dom @testing-library/react @testing-library/user-event web-vitals

	mv README.md README.markdown

	echo "SKIP_PREFLIGHT_CHECK=true" > ".env"

	sed -i -e "s|<div id=\"root\"></div>|<${CUSTOM_ELEMENT_NAME} route=\"hello-world\"></${CUSTOM_ELEMENT_NAME}>|g" public/index.html

	rm -f public/favicon.ico public/logo* public/manifest.json public/robots.txt

	write_client_extension

	cd src

	rm -f App* index* logo.svg reportWebVitals.js setupTests.js

	mkdir -p routes/hello-bar/components routes/hello-bar/pages

	touch routes/hello-bar/components/.gitkeep

	mkdir -p routes/hello-foo/components routes/hello-foo/pages

	touch routes/hello-foo/components/.gitkeep

	mkdir -p routes/hello-world/components routes/hello-world/pages

	touch routes/hello-world/components/.gitkeep

	mkdir -p common/services/liferay common/styles

	write_gitignore
	write_react_app_files

	cd ..
}

function create_vue_2_app {
	check_utils npm

	npm i -g @vue/cli

	vue create ${REMOTE_APP_DIR} --default

	sed -i -e "s|<div id=\"app\"></div>|<${CUSTOM_ELEMENT_NAME}></${CUSTOM_ELEMENT_NAME}>|g" ${REMOTE_APP_DIR}/public/index.html
	sed -i -e "s|#app|${CUSTOM_ELEMENT_NAME}|g" ${REMOTE_APP_DIR}/src/main.js
}

function create_vue_3_app {
	echo ""
}

function main {
	check_usage "${@}"

	CUSTOM_ELEMENT_NAME="${1}"

	CUSTOM_ELEMENT_DISPLAY_NAME="$(echo ${CUSTOM_ELEMENT_NAME} | sed -r 's/^(.)|-(.)/ \U\1\U\2/g' | sed -r 's/^ (.*)/\1/')"

	REMOTE_APP_DIR="${1}"

	if [ -e ${REMOTE_APP_DIR} ]
	then
		REMOTE_APP_DIR=${REMOTE_APP_DIR}-$(random_letter)$(random_digit)$(random_letter)$(random_digit)
	fi

	if [ "${2}" == "react" ]
	then
		create_react_app
	elif [ "${2}" == "vue2" ]
	then
		create_vue_2_app
	elif [ "${2}" == "vue3" ]
	then
		create_vue_3_app
	else
		echo "Unknown JavaScript framework: ${2}."

		exit 1
	fi
}

function random_digit {
	echo $(((RANDOM % 10) + 1))
}

function random_letter {
	echo cat /dev/urandom | tr -cd 'a-z' | head -c 1
}

function write_client_extension {
	echo "assemble:" > client-extension.yaml
	echo "    - from: build/" >> client-extension.yaml
	echo "      include: \"static/**/*\"" >> client-extension.yaml
	echo "      into: static/" >> client-extension.yaml
	echo "${CUSTOM_ELEMENT_NAME}:" >> client-extension.yaml
	echo "    cssURLs:" >> client-extension.yaml
	echo "        - static/css/main.*.css" >> client-extension.yaml
	echo "    friendlyURLMapping: ${CUSTOM_ELEMENT_NAME}" >> client-extension.yaml
	echo "    htmlElementName: ${CUSTOM_ELEMENT_NAME}" >> client-extension.yaml
	echo "    instanceable: false" >> client-extension.yaml
	echo "    name: ${CUSTOM_ELEMENT_DISPLAY_NAME}" >> client-extension.yaml
	echo "    portletCategoryName: category.client-extensions" >> client-extension.yaml
	echo "    type: customElement" >> client-extension.yaml
	echo "    urls:" >> client-extension.yaml
	echo "        - static/js/main.*.js" >> client-extension.yaml
	echo -n "    useESM: false" >> client-extension.yaml
}

function write_gitignore {
	cat <<EOF > .gitignore
EOF
}

function write_react_app_files {
	#
	# common/services/liferay/api.js
	#

	cat <<EOF > common/services/liferay/api.js
import { Liferay } from "./liferay";

const { REACT_APP_LIFERAY_HOST = window.location.origin } = process.env;

const baseFetch = async (url, options = {}) => {
	return fetch(REACT_APP_LIFERAY_HOST + "/" + url, {
		headers: {
			"Content-Type": "application/json",
			"x-csrf-token": Liferay.authToken,
		},
		...options,
	});
};

export default baseFetch;
EOF

	#
	# common/services/liferay/liferay.js
	#

	cat <<EOF > common/services/liferay/liferay.js
export const Liferay = window.Liferay || {
	OAuth2: {
		getAuthorizeURL: () => '',
		getBuiltInRedirectURL: () => '',
		getIntrospectURL: () => '',
		getTokenURL: () => '',
		getUserAgentApplication: (serviceName) => {},
	},
	OAuth2Client: {
		FromParameters: (options) => {
			return {};
		},
		FromUserAgentApplication: (userAgentApplicationId) => {
			return {};
		},
		fetch: (url, options = {}) => {},
	},
	ThemeDisplay: {
		getCompanyGroupId: () => 0,
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
		isSignedIn: () => {
			return false;
		},
	},
	authToken: '',
}
EOF

	#
	# common/styles/hello-world.scss
	#

	cat <<EOF > common/styles/hello-world.scss
.hello-world {
	h1 {
		color: \$primary-color;
		font-weight: bold;
	}
}
EOF

	#
	# common/styles/index.scss
	#

	cat <<EOF > common/styles/index.scss
${CUSTOM_ELEMENT_NAME} {
	@import 'variables';
	@import 'hello-world.scss';
}
EOF

	#
	# common/styles/variables.scss
	#

	cat <<EOF > common/styles/variables.scss
\$primary-color: #295ccc;
EOF

	#
	# index.js
	#

	cat <<EOF > index.js
import React from 'react';
import ReactDOM from 'react-dom';

import DadJoke from './common/components/DadJoke';
import api from './common/services/liferay/api';
import {Liferay} from './common/services/liferay/liferay';
import HelloBar from './routes/hello-bar/pages/HelloBar';
import HelloFoo from './routes/hello-foo/pages/HelloFoo';
import HelloWorld from './routes/hello-world/pages/HelloWorld';

import './common/styles/index.scss';

const App = ({oAuth2Client, route}) => {
	if (route === 'hello-bar') {
		return <HelloBar />;
	}

	if (route === 'hello-foo') {
		return <HelloFoo />;
	}

	return (
		<div>
			<HelloWorld />

			{Liferay.ThemeDisplay.isSignedIn() && (
				<div>
					<DadJoke oAuth2Client={oAuth2Client} />
				</div>
			)}
		</div>
	);
};

class WebComponent extends HTMLElement {
	constructor() {
		super();

		this.oAuth2Client = Liferay.OAuth2Client.FromUserAgentApplication(
			'easy-oauth-application-user-agent'
		);
	}

	connectedCallback() {
		ReactDOM.render(
			<App
				oAuth2Client={this.oAuth2Client}
				route={this.getAttribute('route')}
			/>,
			this
		);

		if (Liferay.ThemeDisplay.isSignedIn()) {
			api('o/headless-admin-user/v1.0/my-user-account')
				.then((response) => response.json())
				.then((response) => {
					if (response.givenName) {
						const nameElements = document.getElementsByClassName(
							'hello-world-name'
						);

						if (nameElements.length) {
							nameElements[0].innerHTML = response.givenName;
						}
					}
				});
		}
	}
}

const ELEMENT_ID = '${CUSTOM_ELEMENT_NAME}';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
EOF

	#
	# routes/hello-bar/pages/HelloBar.js
	#

	cat <<EOF > routes/hello-bar/pages/HelloBar.js
import React from 'react';

const HelloBar = () => (
	<div className="hello-bar">
		<h1>Hello Bar</h1>
	</div>
);

export default HelloBar;
EOF

	#
	# routes/hello-foo/pages/HelloFoo.js
	#

	cat <<EOF > routes/hello-foo/pages/HelloFoo.js
import React from 'react';

const HelloFoo = () => (
	<div className="hello-foo">
		<h1>Hello Foo</h1>
	</div>
);

export default HelloFoo;
EOF

	#
	# routes/hello-world/pages/HelloWorld.js
	#

	cat <<EOF > routes/hello-world/pages/HelloWorld.js
import React from 'react';

const HelloWorld = () => (
	<div className="hello-world">
		<h1>Hello <span className="hello-world-name">World</span></h1>
	</div>
);

export default HelloWorld;
EOF
}

main "${@}"