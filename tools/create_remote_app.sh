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

function create_react_app {
	yarn create react-app ${REMOTE_APP_DIR}

	cd ${REMOTE_APP_DIR}

	yarn add sass
	yarn remove @testing-library/jest-dom @testing-library/react @testing-library/user-event web-vitals

	echo "SKIP_PREFLIGHT_CHECK=true" > ".env"

	sed -i -e "s|<div id=\"root\"></div>|<$CUSTOM_ELEMENT_NAME route=\"hello-world\"></$CUSTOM_ELEMENT_NAME>|g" public/index.html

	rm -f public/favicon.ico public/logo* public/manifest.json public/robots.txt

	cd src

	rm -f App* index* logo.svg reportWebVitals.js setupTests.js

	mkdir -p routes/hello-bar/components routes/hello-bar/pages
	mkdir -p routes/hello-foo/components routes/hello-foo/pages
	mkdir -p routes/hello-world/components routes/hello-world/pages
	mkdir -p common/services/liferay common/styles

	write_gitignore
	write_react_app_files

	cd ..
}

function create_vue_2_app {
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
/**
 * Whenever you need to access the global variable Liferay, use this
 * to avoid runtime errors when you run the React application out of Liferay Portal
 */

export const Liferay = window.Liferay || {
ThemeDisplay: {
	getCompanyGroupId: () => 0,
	getScopeGroupId: () => 0,
	getSiteGroupId: () => 0,
},
authToken: "",
};
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

import HelloBar from './routes/hello-bar/pages/HelloBar';
import HelloFoo from './routes/hello-foo/pages/HelloFoo';
import HelloWorld from './routes/hello-world/pages/HelloWorld';
import './common/styles/index.scss';

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
		<h1>Hello World</h1>
	</div>
);

export default HelloWorld;
EOF
}

main "${@}"