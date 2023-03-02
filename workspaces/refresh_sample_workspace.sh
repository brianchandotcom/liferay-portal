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

function refresh_sample_workspace {

	#
	# Sample Workspace
	#

	local temp_dir=$(mktemp -d)

	pushd ${temp_dir}

	${BLADE_PATH} init --liferay-version dxp-7.4-u63

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

	sed -i 's/name: "com.liferay.gradle.plugins.workspace", version: ".*"/name: "com.liferay.gradle.plugins.workspace", version: "4.1.13"/' settings.gradle

	popd

	cp ${temp_dir}/.gitignore sample-workspace
	cp ${temp_dir}/gradle.properties sample-workspace
	cp ${temp_dir}/gradlew sample-workspace
	cp ${temp_dir}/settings.gradle sample-workspace

	cp -R ${temp_dir}/gradle sample-workspace

	mkdir -p sample-workspace/configs/local

	cp ${temp_dir}/configs/local/portal-ext.properties sample-workspace/configs/local

	mkdir -p sample-workspace/modules

	echo "Client extensions are the recommended way of customizing Liferay. Modules and" > sample-workspace/modules/README.txt
	echo "themes are supported for backwards compatibility." >> sample-workspace/modules/README.txt

	mkdir -p sample-workspace/themes

	cp sample-workspace/modules/README.txt sample-workspace/themes

	#
	# Client Extension: Sample Custom Element 2
	#

	rm -fr sample-workspace/client-extensions/sample-custom-element-2

	../tools/create_remote_app.sh sample-custom-element-2 react

	mkdir -p sample-custom-element-2/src/common/components

	cat <<EOF > sample-custom-element-2/src/common/components/DadJoke.js
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

	sed -i "s/react-scripts test/react-scripts test --passWithNoTests --watchAll=false/" sample-custom-element-2/package.json

	mv sample-custom-element-2 sample-workspace/client-extensions

	#
	# Client Extension: Sample Custom Element 3
	#

	rm -fr sample-workspace/client-extensions/sample-custom-element-3

	../tools/create_remote_app.sh sample-custom-element-3 angular

	mv sample-custom-element-3 sample-workspace/client-extensions
}

function main {
	check_blade

	refresh_sample_workspace
}

main "${@}"