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

	cp sample-default-workspace/configs/local/portal-env.properties ${1}/configs/local
}

function refresh_liferay_learn_workspace {
	init_workspace liferay-learn-workspace
}

function refresh_sample_default_workspace {
	rm -fr sample-default-workspace

	mkdir sample-default-workspace

	cd sample-default-workspace

	${BLADE_PATH} init --liferay-version dxp-7.4-u52

	sed -i'.bak' "s/\"com.liferay.gradle.plugins.workspace\", version: \"4.0.5\"/\"com.liferay.gradle.plugins.workspace\", version: \"4.0.28\"/" settings.gradle

	echo -e "\n**/dist\n**/node_modules_cache\n.DS_Store" >> .gitignore

	echo -e "\n\nfeature.flag.LPS-153457=true" >> configs/local/portal-env.properties

	echo -e "\nliferay.workspace.docker.image.liferay=liferay/dxp:7.4.13-u52-d5.0.3-20221124061349" >> gradle.properties
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

	npx @liferay/cli new delta-remote-app --batch true --options refresh-files/delta-remote-app-options.json

	rm -fr sample-minimal-workspace/client-extensions/delta-remote-app

	mv delta-remote-app sample-minimal-workspace/client-extensions

	cp -R refresh-files/sample-minimal-workspace/* sample-minimal-workspace/

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