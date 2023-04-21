#!/bin/bash

cd $(dirname "${0}")

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

	${BLADE_PATH} init --liferay-version dxp-7.4-u72

	echo -en "\n**/dist\n**/node_modules_cache\n.DS_Store" >> .gitignore

	echo -en "\n\nfeature.flag.LPS-166479=true\n\nfeature.flag.LPS-177027=true" >> configs/local/portal-ext.properties

	#echo -en "\nliferay.workspace.docker.image.liferay=liferay/dxp:7.4.13-u54-d5.0.5-20221208173455" >> gradle.properties
	echo -en "\nliferay.workspace.node.package.manager=yarn" >> gradle.properties

	#
	# https://stackoverflow.com/questions/1654021/how-can-i-delete-a-newline-if-it-is-the-last-character-in-a-file
	# https://stackoverflow.com/questions/38256431/bash-sort-ignore-first-5-lines
	#

	{ head -n 5 gradle.properties ; tail -n +6 gradle.properties | sort | perl -e "chomp if eof" -p; } >gradle.properties.tmp

	mv gradle.properties.tmp gradle.properties

	sed -i 's/name: "biz.aQute.bnd", version: ".*"/name: "biz.aQute.bnd.gradle", version: "5.2.0"/' settings.gradle

	sed -i 's/name: "com.liferay.gradle.plugins.workspace", version: ".*"/name: "com.liferay.gradle.plugins.workspace", version: "5.0.3"/' settings.gradle

	popd

	cp ${temp_dir}/.gitignore liferay-sample-workspace
	cp ${temp_dir}/gradle.properties liferay-sample-workspace
	cp ${temp_dir}/gradlew liferay-sample-workspace
	cp ${temp_dir}/settings.gradle liferay-sample-workspace

	cp -R ${temp_dir}/gradle liferay-sample-workspace

	mkdir -p liferay-sample-workspace/configs/local

	cp ${temp_dir}/configs/local/portal-ext.properties liferay-sample-workspace/configs/local

	mkdir -p liferay-sample-workspace/modules

	echo "Client extensions are the recommended way of customizing Liferay. Modules and" > liferay-sample-workspace/modules/README.markdown
	echo "themes are supported for backwards compatibility." >> liferay-sample-workspace/modules/README.markdown

	mkdir -p liferay-sample-workspace/themes

	cp liferay-sample-workspace/modules/README.markdown liferay-sample-workspace/themes

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
		if (this.oAuth2Client) {
			this._request = this.oAuth2Client.fetch(
				'/dad/joke'
			).then(response => response.text()
			).then(text => {
				this._request = null;
				this.setState({"joke": text});
			});
		}
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

		try {
			this.oAuth2Client = Liferay.OAuth2Client.FromUserAgentApplication(
				'liferay-sample-oauth-application-user-agent'
			);
		}
		catch (error) {
			console.log("Unable to get user agent application");
		}
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
}

function main {
	check_blade

	refresh_liferay_sample_workspace
}

main "${@}"