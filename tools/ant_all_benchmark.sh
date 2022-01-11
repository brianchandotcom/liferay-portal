#!/bin/bash

set -e

function echo_time {
	local duration=${SECONDS}

	echo "completed in $((${duration} / 60)) minutes and $((${duration} % 60)) seconds."
}

function main {
	pushd .. > /dev/null

	echo "Running... wait for all 3 runs to complete."
	echo ""

	git clean -dfx -e "*.${USER}.*"  > /dev/null

	rm -fr ~/.liferay

	ant -Dmirrors.hostname= -f build-dist.xml unzip-tomcat > /dev/null

	ant setup-profile-dxp

	run_ant_all

	echo "Run 1 with a clean repo $(echo_time)"

	rm -fr .gradle/caches

	run_ant_all

	echo "Run 2 without Gracle cache $(echo_time)"

	run_ant_all

	echo "Run 3 with all caches $(echo_time)"

	popd > /dev/null
}

function run_ant_all {
	SECONDS=0;

	ant all > /dev/null
	#sleep 5
}

main "${@}"