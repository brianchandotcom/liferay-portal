#!/bin/bash

function check_usage {
	export CUSTOM_ELEMENT_NAME="hello-world"
}

function create_react_app {
	local temp_dir=$(get_temp_dir)

	yarn create react-app ${temp_dir}

	cd ${temp_dir}

	yarn remove @testing-library/jest-dom @testing-library/react @testing-library/user-event web-vitals

	echo "SKIP_PREFLIGHT_CHECK=true" > ".env"

	sed -i -e "s|<div id=\"root\"></div>|<$CUSTOM_ELEMENT_NAME></$CUSTOM_ELEMENT_NAME>|g" public/index.html

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

	local temp_dir=$(get_temp_dir)

	vue create ${temp_dir} --default

	sed -i -e "s|<div id=\"app\"></div>|<${CUSTOM_ELEMENT_NAME}></${CUSTOM_ELEMENT_NAME}>|g" ${temp_dir}/public/index.html
	sed -i -e "s|#app|${CUSTOM_ELEMENT_NAME}|g" ${temp_dir}/src/main.js
}

function create_vue_3_app {
	echo create_vue_3_app
}

function date {
	export LC_ALL=en_US.UTF-8
	export TZ=America/Los_Angeles

	if [ -z ${1+x} ] || [ -z ${2+x} ]
	then
		if [ "$(uname)" == "Darwin" ]
		then
			/bin/date
		elif [ -e /bin/date ]
		then
			/bin/date --iso-8601=seconds
		else
			/usr/bin/date --iso-8601=seconds
		fi
	else
		if [ "$(uname)" == "Darwin" ]
		then
			/bin/date -jf "%a %b %e %H:%M:%S %Z %Y" "${1}" "${2}"
		elif [ -e /bin/date ]
		then
			/bin/date -d "${1}" "${2}"
		else
			/usr/bin/date -d "${1}" "${2}"
		fi
	fi
}

function get_temp_dir {
	local current_date=$(date)

	local timestamp=$(date "${current_date}" "+%Y%m%d%H%M%S")

	echo "temp-${timestamp}"
}

function main {
	check_usage

	#create_react_app
	create_vue_2_app
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
const {REACT_APP_LIFERAY_API = window.location.origin} = process.env;

export const getLiferayAuthenticationToken = () => {
	try {
		// eslint-disable-next-line no-undef
		const token = Liferay.authToken;

		return token;
	} catch (error) {
		console.warn('Not able to find Liferay auth token\n', error);

		return '';
	}
};

const baseFetch = async (url, {body, method = 'GET'} = {}) => {
	const response = await fetch(REACT_APP_LIFERAY_API + '/' + url, {
		...(body && {body: JSON.stringify(body)}),
		headers: {
			'Content-Type': 'application/json',
			'x-csrf-token': getLiferayAuthenticationToken(),
		},
		method,
	});

	const data = await response.json();

	return {data};
};

export {REACT_APP_LIFERAY_API};

export default baseFetch;
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

class WebComponent extends HTMLElement {
	connectedCallback() {
		ReactDOM.render(
			<HelloWorld />,
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

main