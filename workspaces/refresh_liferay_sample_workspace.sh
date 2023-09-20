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

	${BLADE_PATH} init -v $(${BLADE_PATH} init --all --list | grep dxp-7.4 | head -1)

	echo -en "\n**/dist\n**/node_modules_cache\n.DS_Store" >> .gitignore

	echo -en "\n\nfeature.flag.LPS-166479=true\nfeature.flag.LPS-172903=true\nfeature.flag.LPS-164563=true\nfeature.flag.LPS-177027=true" >> configs/local/portal-ext.properties

	#echo -en "\nliferay.workspace.docker.image.liferay=liferay/dxp:7.4.13-u54-d5.0.5-20221208173455" >> gradle.properties
	echo -en "\nliferay.workspace.node.package.manager=yarn" >> gradle.properties

	#
	# https://stackoverflow.com/questions/1654021/how-can-i-delete-a-newline-if-it-is-the-last-character-in-a-file
	# https://stackoverflow.com/questions/38256431/bash-sort-ignore-first-5-lines
	#

	{ head -n 5 gradle.properties ; tail -n +6 gradle.properties | sort | perl -e "chomp if eof" -p; } >gradle.properties.tmp

	mv gradle.properties.tmp gradle.properties

	sed -i 's/name: "biz.aQute.bnd", version: ".*"/name: "biz.aQute.bnd.gradle", version: "5.2.0"/' settings.gradle
	sed -i 's/name: "com.liferay.gradle.plugins.workspace", version: ".*"/name: "com.liferay.gradle.plugins.workspace", version: "9.0.1"/' settings.gradle

	echo -en "\ninclude \"poshi\"" >> settings.gradle

	popd

	cat <<EOF > liferay-sample-workspace/.gitignore
.DS_Store
/bundles
/poshi/poshi-ext.properties
/poshi/test-results
/poshi/tests
bin
build
dist
node_modules
node_modules_cache
EOF

	truncate -s -1 liferay-sample-workspace/.gitignore

	cp ${temp_dir}/gradle.properties liferay-sample-workspace
	cp ${temp_dir}/gradlew liferay-sample-workspace
	cp ${temp_dir}/settings.gradle liferay-sample-workspace

	cp -R ${temp_dir}/gradle liferay-sample-workspace

	mkdir -p liferay-sample-workspace/configs/local

	cp ${temp_dir}/configs/local/portal-ext.properties liferay-sample-workspace/configs/local

	mkdir -p liferay-sample-workspace/modules

	echo "Client extensions are the recommended way of customizing Liferay. Modules and" > liferay-sample-workspace/modules/README.markdown
	echo -n "themes are supported for backwards compatibility." >> liferay-sample-workspace/modules/README.markdown

	mkdir -p liferay-sample-workspace/themes

	cp liferay-sample-workspace/modules/README.markdown liferay-sample-workspace/themes

	#
	# Client Extension: Sample Custom Element 2
	#

	rm -fr liferay-sample-workspace/client-extensions/liferay-sample-custom-element-2

	../tools/create_custom_element.sh liferay-sample-custom-element-2 react

	sed '/"eslintConfig": {/,/},/d' liferay-sample-custom-element-2/package.json | awk '!/^,$/' > temp.json && mv temp.json liferay-sample-custom-element-2/package.json

	mkdir -p liferay-sample-custom-element-2/src/common/components

	cat <<EOF > liferay-sample-custom-element-2/src/common/components/Comic.js
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {Liferay} from '../services/liferay/liferay.js';

let oAuth2Client;

try {
	oAuth2Client = Liferay.OAuth2Client.FromUserAgentApplication(
		'liferay-sample-etc-node-oauth-application-user-agent'
	);
}
catch (error) {
	console.error(error);
}

function Comic() {
	const [comicData, setComicData] = React.useState(null);

	React.useEffect(() => {
		oAuth2Client
			?.fetch('/comic')
			.then((comic) => {
			setComicData({
				alt: comic.alt,
				img: comic.img,
				title: comic.safe_title,
			});
		});
	}, []);

	return !comicData ? (
		<div>Loading...</div>
	) : (
		<div>
			<h2>{comicData.title}</h2>

			<p>
				<img alt={comicData.alt} src={comicData.img} />
			</p>
		</div>
	);
}

export default Comic;
EOF

	cat <<EOF > liferay-sample-custom-element-2/src/common/components/DadJoke.js
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {Liferay} from '../services/liferay/liferay.js';

let oAuth2Client;

try {
	oAuth2Client = Liferay.OAuth2Client.FromUserAgentApplication(
		'liferay-sample-etc-spring-boot-oauth-application-user-agent'
	);
}
catch (error) {
	console.error(error);
}

function DadJoke() {
	const [joke, setJoke] = React.useState(null);

	React.useEffect(() => {
		oAuth2Client
			?.fetch('/dad/joke')
			.then((response) => response.text())
			.then((joke) => {
				setJoke(joke);
			})
			// eslint-disable-next-line no-console
			.catch((error) => console.log(error));
	}, []);

	if (!joke) {
		return <div>Loading...</div>;
	}

	return <div>{joke}</div>;
}

export default DadJoke;
EOF

	cat <<EOF > liferay-sample-custom-element-2/src/index.js
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {createRoot} from 'react-dom/client';

import Comic from './common/components/Comic.js';
import DadJoke from './common/components/DadJoke.js';
import api from './common/services/liferay/api.js';
import {Liferay} from './common/services/liferay/liferay.js';
import HelloBar from './routes/hello-bar/pages/HelloBar.js';
import HelloFoo from './routes/hello-foo/pages/HelloFoo.js';
import HelloWorld from './routes/hello-world/pages/HelloWorld.js';

import './common/styles/index.scss';

const App = ({route}) => {
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
					<Comic />

					<hr />

					<DadJoke />
				</div>
			)}
		</div>
	);
};

class WebComponent extends HTMLElement {
	connectedCallback() {
		createRoot(this).render(
			<App route={this.getAttribute('route')} />,
			this
		);

		if (Liferay.ThemeDisplay.isSignedIn()) {
			api('o/headless-admin-user/v1.0/my-user-account')
				.then((response) => response.json())
				.then((response) => {
					if (response.givenName) {
						const nameElements =
							document.getElementsByClassName('hello-world-name');

						if (nameElements.length) {
							nameElements[0].innerHTML = response.givenName;
						}
					}
				})
				.catch((error) => {
					// eslint-disable-next-line no-console
					console.log(error);
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