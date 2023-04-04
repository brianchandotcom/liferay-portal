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

	#
	# Update Blade with Blade.
	#

	#${BLADE_PATH} update -s > /dev/null

	#
	# Update Blade directly with JPM.
	#

	#jpm install -f https://repository-cdn.liferay.com/nexus/service/local/repositories/liferay-public-releases/content/com/liferay/blade/com.liferay.blade.cli/4.1.1/com.liferay.blade.cli-4.1.1.jar
}

function refresh_liferay_sample_workspace {

	#
	# Sample Workspace
	#

	local temp_dir=$(mktemp -d)

	pushd ${temp_dir}

	${BLADE_PATH} init --liferay-version dxp-7.4-u65

	echo -en "\n**/dist\n**/node_modules_cache\n.DS_Store" >> .gitignore

	echo -en "\n\nfeature.flag.LPS-166479=true" >> configs/local/portal-ext.properties

	#echo -en "\nliferay.workspace.docker.image.liferay=liferay/dxp:7.4.13-u54-d5.0.5-20221208173455" >> gradle.properties
	echo -en "\nliferay.workspace.node.package.manager=yarn" >> gradle.properties

	#
	# https://stackoverflow.com/questions/1654021/how-can-i-delete-a-newline-if-it-is-the-last-character-in-a-file
	# https://stackoverflow.com/questions/38256431/bash-sort-ignore-first-5-lines
	#

	{ head -n 5 gradle.properties ; tail -n +6 gradle.properties | sort | perl -e "chomp if eof" -p; } >gradle.properties.tmp

	mv gradle.properties.tmp gradle.properties

	sed -i 's/name: "com.liferay.gradle.plugins.workspace", version: ".*"/name: "com.liferay.gradle.plugins.workspace", version: "4.2.1"/' settings.gradle

	popd

	cp ${temp_dir}/.gitignore liferay-sample-workspace
	cp ${temp_dir}/gradle.properties liferay-sample-workspace
	cp ${temp_dir}/gradlew liferay-sample-workspace
	cp ${temp_dir}/settings.gradle liferay-sample-workspace

	cp -R ${temp_dir}/gradle liferay-sample-workspace

	mkdir -p liferay-sample-workspace/configs/local

	cp ${temp_dir}/configs/local/portal-ext.properties liferay-sample-workspace/configs/local

	mkdir -p liferay-sample-workspace/modules

	echo "Client extensions are the recommended way of customizing Liferay. Modules and" > liferay-sample-workspace/modules/README.txt
	echo "themes are supported for backwards compatibility." >> liferay-sample-workspace/modules/README.txt

	mkdir -p liferay-sample-workspace/themes

	cp liferay-sample-workspace/modules/README.txt liferay-sample-workspace/themes

	#
	# Client Extension: Sample Custom Element 2
	#

	rm -fr liferay-sample-workspace/client-extensions/liferay-sample-custom-element-2

	../tools/create_custom_element.sh liferay-sample-custom-element-2 react

	mkdir -p liferay-sample-custom-element-2/src/common/components

	cat <<EOF > liferay-sample-custom-element-2/src/common/components/DadJoke.js
import React from 'react';

class DadJoke extends React.Component {
	constructor(props) {
		super(props);

		this.oAuth2Client = props.oAuth2Client;
		this.state = {"joke": ""};
	}

	componentDidMount() {
		this._request = this.oAuth2Client.fetch(
			'/dad/joke'
		).then(response => response.text()
		).then(text => {
			this._request = null;
			this.setState({"joke": text});
		});
	}

	componentWillUnmount() {
		if (this._request) {
			this._request.cancel();
		}
	}

	render() {
		if (this.state === null) {
			return <div>Loading...</div>
		}
		else {
			return <div>{this.state.joke}</div>
		}
	}
}

export default DadJoke;
EOF

	cat <<EOF > liferay-sample-custom-element-2/src/index.js
import React from 'react';
import {createRoot} from 'react-dom/client';

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
			'liferay-sample-oauth-application-user-agent'
		);
	}

	connectedCallback() {
		createRoot(this).render(
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

const ELEMENT_ID = 'liferay-sample-custom-element-2';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
EOF

	sed -i "s/react-scripts test/react-scripts test --passWithNoTests --watchAll=false/" liferay-sample-custom-element-2/package.json

	mv liferay-sample-custom-element-2 liferay-sample-workspace/client-extensions

	#
	# Client Extension: Sample Custom Element 3
	#

	rm -fr liferay-sample-workspace/client-extensions/liferay-sample-custom-element-3

	../tools/create_custom_element.sh liferay-sample-custom-element-3 angular

	mv liferay-sample-custom-element-3 liferay-sample-workspace/client-extensions

	#
	# Client Extension: Sample Theme Spritemap 2
	#

	rm -fr liferay-sample-workspace/client-extensions/liferay-sample-theme-spritemap-2

	pushd liferay-sample-workspace/client-extensions

	cat <<EOF > liferay-sample-theme-spritemap-2-config.json
{
	"extendClay": false,
	"platform": "dxp-7.4",
	"target": "Liferay Theme Spritemap Client Extension"
}
EOF

	npx --yes @liferay/cli new liferay-sample-theme-spritemap-2 --batch --options liferay-sample-theme-spritemap-2-config.json

	rm liferay-sample-theme-spritemap-2-config.json
	rm liferay-sample-theme-spritemap-2/README.md

	echo "assemble:" > liferay-sample-theme-spritemap-2/client-extension.yaml
	echo "    - from: build" >> liferay-sample-theme-spritemap-2/client-extension.yaml
	echo "      into: static" >> liferay-sample-theme-spritemap-2/client-extension.yaml
	echo "liferay-sample-theme-spritemap-2:" >> liferay-sample-theme-spritemap-2/client-extension.yaml
	echo "    name: Liferay Sample Theme Spritemap 2" >> liferay-sample-theme-spritemap-2/client-extension.yaml
	echo "    type: themeSpritemap" >> liferay-sample-theme-spritemap-2/client-extension.yaml
	echo -n "    url: spritemap.svg" >> liferay-sample-theme-spritemap-2/client-extension.yaml

	cat <<EOF > liferay-sample-theme-spritemap-2/src/cog.svg
<svg viewBox="0 0 512 512" xmlns="http://www.w3.org/2000/svg">
	<path class="lexicon-icon-outline books-1-spine-top" d="M96.1,32h-64c-17.7,0-32,14.3-32,32v32h128V64C128.1,46.3,113.8,32,96.1,32z" />
	<path class="lexicon-icon-outline books-1-spine-bottom" d="M0.1,448c0,17.7,14.3,32,32,32h64c17.7,0,32-14.3,32-32v-32H0.1V448z" />
	<rect class="lexicon-icon-outline books-1-spine" height="256" width="128" x="0.1" y="128" />
	<path class="lexicon-icon-outline books-2-spine-top" d="M256.1,32h-64c-17.7,0-32,14.3-32,32v32h128V64C288.1,46.3,273.8,32,256.1,32z" />
	<path class="lexicon-icon-outline books-2-spine-bottom" d="M160.1,448c0,17.7,14.3,32,32,32h64c17.7,0,32-14.3,32-32v-32h-128V448z" />
	<rect class="lexicon-icon-outline books-2-spine" height="256" width="128" x="160.1" y="128" />
	<path class="lexicon-icon-outline books-3-spine-top" d="M359.2,35.2l-31,8.1c-17.1,4.5-27.4,21.9-22.9,39l8.1,31l92.9-24.2l-8.1-31C393.7,41,376.3,30.7,359.2,35.2z" />
	<rect class="lexicon-icon-outline books-3-spine" height="256" transform="matrix(0.9678 -0.2518 0.2518 0.9678 -51.571 108.9927)" width="96" x="352.1" y="128" />
	<path class="lexicon-icon-outline books-3-spine-bottom" d="M402,453.9c4.5,17.1,21.9,27.4,39,22.9l31-8.1c17.1-4.5,27.4-21.9,22.9-39l-8.1-31l-92.9,24.2L402,453.9z" />
</svg>
EOF

	#
	# Client Extension: Sample Theme Spritemap 3
	#

	rm -fr liferay-sample-workspace/client-extensions/liferay-sample-theme-spritemap-3

	cat <<EOF > liferay-sample-theme-spritemap-3-config.json
{
	"extendClay": true,
	"platform": "dxp-7.4",
	"target": "Liferay Theme Spritemap Client Extension"
}
EOF

	npx --yes @liferay/cli new liferay-sample-theme-spritemap-3 --batch --options liferay-sample-theme-spritemap-3-config.json

	rm liferay-sample-theme-spritemap-3-config.json
	rm liferay-sample-theme-spritemap-3/README.md

	cp liferay-sample-theme-spritemap-2/client-extension.yaml liferay-sample-theme-spritemap-3

	sed -i 's/2/3/' liferay-sample-theme-spritemap-3/client-extension.yaml

	cp liferay-sample-theme-spritemap-2/src/cog.svg liferay-sample-theme-spritemap-3/src

	popd
}

function main {
	check_blade

	refresh_liferay_sample_workspace
}

main "${@}"