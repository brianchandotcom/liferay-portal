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

function copy_template {
	cp -R ../modules/apps/client-extension/client-extension-type-api/src/main/resources/com/liferay/client/extension/type/dependencies/templates/${1} "${2}"

	find "${2}" -not -path '*/*\.ico' -type f -exec sed -i "s/\${id}/$(basename ${2})/g" {} +
	find "${2}" -not -path '*/*\.ico' -type f -exec sed -i "s/\${name}/${3}/g" {} +
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

function refresh_sample_default_workspace {
	rm -fr sample-default-workspace

	mkdir sample-default-workspace

	cd sample-default-workspace

	${BLADE_PATH} init --liferay-version dxp-7.4-u62

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

	sed -i 's/name: "com.liferay.gradle.plugins.workspace", version: ".*"/name: "com.liferay.gradle.plugins.workspace", version: "4.1.12"/' settings.gradle

	touch modules/.touch
	touch themes/.touch

	cd ..
}

function refresh_sample_minimal_workspace {
	init_workspace sample-minimal-workspace

	#
	# Sample default workspace
	#

	rm -fr sample-default-workspace/client-extensions

	cp -R sample-minimal-workspace/client-extensions sample-default-workspace
}

function main {
	check_blade

	refresh_sample_default_workspace

	refresh_sample_minimal_workspace

	refresh_liferay_learn_workspace
}

main "${@}"