#!/bin/bash

function check_blade {
	if [ -e ~/jpm/bin/blade ]
	then
		bladePath=~/jpm/bin/blade
	fi

	if [ -e ~/Library/PackageManager/bin/blade ]
	then
		bladePath=~/Library/PackageManager/bin/blade
	fi

	if [ -z "$bladePath" ]
	then
		echo "Blade CLI is not available. To install Blade CLI, execute the following command:"
		echo ""

		echo "curl -L https://raw.githubusercontent.com/liferay/liferay-blade-cli/master/cli/installers/local | sh"

		exit 1
	fi

	$bladePath  update -s
}

function refresh_sample_default_workspace {
	check_blade

	rm -fr sample-default-workspace

	mkdir sample-default-workspace

	cd sample-default-workspace

	$bladePath init --liferay-version dxp-7.4-u30

	echo -e "\n**/dist\n**/node_modules_cache\n.DS_Store" >> .gitignore

	echo -e "\n\nfeature.flag.LPS-153457=true" >> configs/local/portal-ext.properties

	echo -e "\nliferay.workspace.docker.image.liferay=liferay/dxp:7.4.13.nightly-d4.1.4-20220628211901" >> gradle.properties

	sed -i'.bak' 's/4.0.0/4.0.1/g' settings.gradle

	rm settings.gradle.bak

	touch modules/.touch
	touch themes/.touch

	cd ..

	rm -fr sample-default-workspace/client-extensions

	cp -R sample-minimal-workspace/client-extensions sample-default-workspace
}

function refresh_sample_minimal_workspace {
	cp sample-default-workspace/.gitignore sample-minimal-workspace
	cp sample-default-workspace/gradle.properties sample-minimal-workspace
	cp sample-default-workspace/gradlew sample-minimal-workspace
	cp sample-default-workspace/settings.gradle sample-minimal-workspace

	cp -R sample-default-workspace/gradle sample-minimal-workspace

	mkdir -p sample-minimal-workspace/configs/local

	cp sample-default-workspace/configs/local/portal-ext.properties sample-minimal-workspace/configs/local
}

function main {
	refresh_sample_default_workspace

	refresh_sample_minimal_workspace
}

main "${@}"