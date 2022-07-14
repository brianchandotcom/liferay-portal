#!/bin/bash

function check_blade {
	local bladePath

	if [ -e ~/jpm/bin/blade ]
	then
		bladePath=~/jpm/bin/blade
	fi

	if [ -e ~/Library/PackageManager/bin/blade ]
	then
		bladePath=~/Library/PackageManager/bin/blade
	fi

	if [ -z "${bladePath}" ]
	then
		echo "Blade CLI is not available. To install Blade CLI, execute the following command:"
		echo ""

		echo "curl -L https://raw.githubusercontent.com/liferay/liferay-blade-cli/master/cli/installers/local | sh"

		exit 1
	fi

	${bladePath} update -s > /dev/null

	echo "${bladePath}"
}

function copy_template {
	cp -R ../modules/apps/client-extension/client-extension-type-api/src/main/resources/com/liferay/client/extension/type/dependencies/templates/${1} "${2}"

	find "${2}" -not -path '*/*\.ico' -type f -exec sed -i'.bak' "s/\${id}/$(basename ${2})/g" {} +
	find "${2}" -not -path '*/*\.ico' -type f -exec sed -i'.bak' "s/\${name}/${3}/g" {} +
}

function refresh_sample_default_workspace {
	local bladePath=$(check_blade)

	rm -fr sample-default-workspace

	mkdir sample-default-workspace

	cd sample-default-workspace

	${bladePath} init --liferay-version dxp-7.4-u32

	echo -e "\n**/dist\n**/node_modules_cache\n.DS_Store" >> .gitignore

	echo -e "\n\nfeature.flag.LPS-153457=true" >> configs/local/portal-ext.properties

	echo -e "\nliferay.workspace.docker.image.liferay=liferay/7.4.13.nightly-d4.1.4-20220707214146" >> gradle.properties

	sort -o gradle.properties gradle.properties

	touch modules/.touch
	touch themes/.touch

	cd ..
}

function refresh_sample_minimal_workspace {
	cp sample-default-workspace/.gitignore sample-minimal-workspace
	cp sample-default-workspace/gradle.properties sample-minimal-workspace
	cp sample-default-workspace/gradlew sample-minimal-workspace
	cp sample-default-workspace/settings.gradle sample-minimal-workspace

	cp -R sample-default-workspace/gradle sample-minimal-workspace

	mkdir -p sample-minimal-workspace/configs/local

	cp sample-default-workspace/configs/local/portal-ext.properties sample-minimal-workspace/configs/local

	rm -fr sample-minimal-workspace/client-extensions/able-*

	copy_template custom-element sample-minimal-workspace/client-extensions/able-custom-element "Able Custom Element"
	copy_template global-css sample-minimal-workspace/client-extensions/able-global-css "Able Global CSS"
	copy_template global-js sample-minimal-workspace/client-extensions/able-global-js "Able Global JS"
	copy_template iframe sample-minimal-workspace/client-extensions/able-iframe "Able IFrame"
	copy_template theme-css sample-minimal-workspace/client-extensions/able-theme-css "Able Theme CSS"
	copy_template theme-favicon sample-minimal-workspace/client-extensions/able-theme-favicon "Able Theme Favicon"

	rm -fr sample-default-workspace/client-extensions

	cp -R sample-minimal-workspace/client-extensions sample-default-workspace
}

function main {
	refresh_sample_default_workspace

	refresh_sample_minimal_workspace
}

main "${@}"