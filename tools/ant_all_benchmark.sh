#!/bin/bash

set -e

function echo_time {
	local duration=${SECONDS}

	echo "completed in $((${duration} / 60)) minutes and $((${duration} % 60)) seconds."
}

function main {
	pushd .. > /dev/null

	local binaries_cache_dir_name=liferay-binaries-cache-2020

	if [ ! -e "../${binaries_cache_dir_name}" ]
	then
		echo "Clone https://github.com/liferay/${binaries_cache_dir_name} into ../${binaries_cache_dir_name} and rerun ${0}."

		exit
	fi

	pushd ../${binaries_cache_dir_name} > /dev/null

	git pull upstream master

	popd > /dev/null

	git clean -dfx -e "*.${USER}.*" > /dev/null

	rm -fr ~/.liferay

	ant -Dmirrors.hostname= -f build-dist.xml unzip-tomcat > /dev/null

	ant setup-profile-dxp

	echo ""
	echo "Running \"ant all\" 3 times..."

	run_ant_all

	echo "Run 1 with a clean repository $(echo_time)"

	rm -fr .gradle/caches

	run_ant_all

	echo "Run 2 without Gracle cache $(echo_time)"

	run_ant_all

	echo "Run 3 with all caches $(echo_time)"

	popd > /dev/null
}

function run_ant_all {
	if [ -e tools/gradle*.zip ]
	then
		./gradlew --stop > /dev/null
	fi

	SECONDS=0;

	ant -Dmirrors.hostname= all > /dev/null
	#sleep 5

	echo ""
}

main "${@}"