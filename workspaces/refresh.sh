#!/bin/bash

function check_blade {
	if [ -e ~/jpm/bin/blade ]
	then
		BLADE_PATH=~/jpm/bin/blade
	fi

	if [ -e ~/Library/PackageManager/bin/blade ]
	then
		BLADE_PATH=~/Library/PackageManager/bin/blade
	fi

	if [ -z "${BLADE_PATH}" ]
	then
		echo "Blade CLI is not available. To install Blade CLI, execute the following command:"
		echo ""

		echo "curl -L https://raw.githubusercontent.com/liferay/liferay-blade-cli/master/cli/installers/local | sh"

		exit 1
	fi

	${BLADE_PATH} update -s > /dev/null
}

function copy_template {
	cp -R ../modules/apps/client-extension/client-extension-type-api/src/main/resources/com/liferay/client/extension/type/dependencies/templates/${1} "${2}"

	find "${2}" -not -path '*/*\.ico' -type f -exec sed -i'.bak' "s/\${id}/$(basename ${2})/g" {} +
	find "${2}" -not -path '*/*\.ico' -type f -exec sed -i'.bak' "s/\${name}/${3}/g" {} +
}

function init_workspace {
	cp sample-default-workspace/.gitignore ${1}
	cp sample-default-workspace/gradle.properties ${1}
	cp sample-default-workspace/gradlew ${1}
	cp sample-default-workspace/settings.gradle ${1}

	cp -R sample-default-workspace/gradle ${1}

	mkdir -p ${1}/configs/local

	cp sample-default-workspace/configs/local/portal-ext.properties ${1}/configs/local
}

function refresh_liferay_learn_workspace {
	init_workspace liferay-learn-workspace
}

function refresh_react_remote_app {
	../tools/create_remote_app.sh fox-remote-app react

	cat << EOF > fox-remote-app/client-extension.yaml
assemble:
    - from: build/
      include: "static/**/*"
      into: static/
fox-remote-app:
    cssURLs:
        - static/css/main.*.css
    friendlyURLMapping: fox-remote-app
    htmlElementName: fox-remote-app
    instanceable: false
    name: Fox Remote App
    portletCategoryName: category.remote-apps
    type: customElement
    urls:
        - static/js/main.*.js
        # To enable dev mode uncomment following url
        # Run gradle deploy && yarn start
        #- http://localhost:3000/static/js/bundle.js
    useESM: false
EOF

	cat << EOF > fox-remote-app/src/routes/hello-world/pages/HelloWorld.js
import React from 'react';

const HelloWorld = () => (
	<div className="hello-world">
		<h1>Hello <span className="hello-world-name">World</span></h1>
	</div>
);

export default HelloWorld;
EOF

	cat << EOF > fox-remote-app/src/index.js
import React from 'react';
import ReactDOM from 'react-dom';

import HelloBar from './routes/hello-bar/pages/HelloBar';
import HelloFoo from './routes/hello-foo/pages/HelloFoo';
import HelloWorld from './routes/hello-world/pages/HelloWorld';
import api from './common/services/liferay/api';
import { Liferay } from './common/services/liferay/liferay';

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
EOF
	sed -i'.bak' "s/^.*\"test\": \"react-scripts test\",.*$//" fox-remote-app/package.json
}

function refresh_sample_default_workspace {
	rm -fr sample-default-workspace

	mkdir sample-default-workspace

	cd sample-default-workspace

	${BLADE_PATH} init --liferay-version dxp-7.4-u53

	sed -i'.bak' "s/\"com.liferay.gradle.plugins.workspace\", version: \".*\"/\"com.liferay.gradle.plugins.workspace\", version: \"4.0.31\"/" settings.gradle

	echo -e "\n**/dist\n**/node_modules_cache\n.DS_Store" >> .gitignore

	echo -e "\n\nfeature.flag.LPS-153457=true" >> configs/local/portal-ext.properties

	echo -e "\nliferay.workspace.docker.image.liferay=liferay/dxp:7.4.13-u53-d5.0.3-20221201085420" >> gradle.properties
	echo -e "\nliferay.workspace.node.package.manager=yarn" >> gradle.properties

	sort -o gradle.properties gradle.properties

	touch modules/.touch
	touch themes/.touch

	cd ..
}

function refresh_sample_minimal_workspace {
	init_workspace sample-minimal-workspace

	rm -fr sample-minimal-workspace/client-extensions/able-*

	copy_template custom-element sample-minimal-workspace/client-extensions/able-custom-element "Able Custom Element"
	copy_template global-css sample-minimal-workspace/client-extensions/able-global-css "Able Global CSS"
	copy_template global-js sample-minimal-workspace/client-extensions/able-global-js "Able Global JS"
	copy_template iframe sample-minimal-workspace/client-extensions/able-iframe "Able IFrame"
	copy_template theme-css sample-minimal-workspace/client-extensions/able-theme-css "Able Theme CSS"
	copy_template theme-favicon sample-minimal-workspace/client-extensions/able-theme-favicon "Able Theme Favicon"

	refresh_react_remote_app

	rm -fr sample-minimal-workspace/client-extensions/fox-remote-app

	mv fox-remote-app sample-minimal-workspace/client-extensions

	rm -fr sample-default-workspace/client-extensions

	cp -R sample-minimal-workspace/client-extensions sample-default-workspace
}

function remove_bak_files {
	find . -name '*.bak' -exec rm {} +
}

function main {
	check_blade

	refresh_sample_default_workspace

	refresh_sample_minimal_workspace

	refresh_liferay_learn_workspace

	remove_bak_files
}

main "${@}"