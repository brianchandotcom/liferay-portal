#!/bin/bash

ANT_ALL_RUN_COUNT=0
DURATION_ARRAY=( )
DURATION_LOG=false

set -e

function echo_time {
	local duration=${SECONDS}

	echo "completed in $((${duration} / 60)) minutes and $((${duration} % 60)) seconds."
}

function enable_duration_log {
	for arg in "${@}"
	do
		if [ "${arg}" == "--log" ]
		then
			DURATION_LOG=true

			echo "Log to record runs duration enabled"

			break
		fi
	done
}

function log_durations {
	if [ ${DURATION_LOG} == true ]
	then
		echo "" >> ant_all_duration.csv

		for (( i = 0 ; i < ${#DURATION_ARRAY[@]}; i++ ))
		do
			echo -n "${DURATION_ARRAY[${i}]}" >> ant_all_duration.csv

			if [ "${i}" -lt "$((${#DURATION_ARRAY[@]} - 1))" ]
			then
				echo -n "," >> ant_all_duration.csv
			fi
		done
	fi
}

function save_duration {
	if [ ${DURATION_LOG} == true ]
	then
		DURATION_ARRAY[${ANT_ALL_RUN_COUNT}]=${SECONDS}

		let "ANT_ALL_RUN_COUNT=${ANT_ALL_RUN_COUNT} + 1"
	fi
}

function main {
	enable_duration_log "${@}"

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

	git clean -dfx -e "*.${USER}.*" -e portal-ext.properties > /dev/null

	rm -fr ~/.liferay

	ant -Dmirrors.hostname= -f build-dist.xml unzip-tomcat > /dev/null

	ant setup-profile-dxp

	echo ""
	echo "Running \"ant all\" 3 times..."

	run_ant_all

	echo "Run 1 with a clean repository $(echo_time)"

	save_duration

	rm -fr .gradle/caches

	run_ant_all

	echo "Run 2 without Gradle cache $(echo_time)"

	save_duration

	run_ant_all

	echo "Run 3 with all caches $(echo_time)"

	save_duration

	log_durations

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