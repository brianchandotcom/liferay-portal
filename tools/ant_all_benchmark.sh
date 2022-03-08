#!/bin/bash

ANT_ALL_DURATION_CSV_PATH="tools/ant_all_duration.csv"
ANT_ALL_RUN_COUNT=0
CHECK_PERFORMANCE=false
DURATION_ARRAY=( )
DURATION_LOG=false

set -e

function check_performance {
	if [ ${CHECK_PERFORMANCE} == true ]
	then
		local durations_min_lenght=2
		local lines_count="$(($(cat ${ANT_ALL_DURATION_CSV_PATH} | wc -l) + 1))"

		if [ "${lines_count}" -ge $((${durations_min_lenght} + 1)) ]
		then
			local actual_durations="$(tail -n 1 ${ANT_ALL_DURATION_CSV_PATH})"
			local previous_durations="$(tail -n 2 ${ANT_ALL_DURATION_CSV_PATH} | head -n 1)"
			local one_minute=60

			for (( i = 2 ; i <= 4; i++ ))
			do
				local actual_duration="$(echo ${actual_durations} | cut -d ',' -f ${i})"
				local previous_duration="$(echo ${previous_durations} | cut -d ',' -f ${i})"

				if [ $(("${actual_duration}" - "${previous_duration}")) -gt "${one_minute}" ]
				then
					if [ "${i}" -eq 2 ]
					then
						echo "Warning: performance reduced using clean repository"
					fi

					if [ "${i}" -eq 3 ]
					then
						echo "Warning: performance reduced NOT using Gradle cache"
					fi

					if [ "${i}" -eq 4 ]
					then
						echo "Warning: performance reduced using all caches"
					fi
				fi
			done
		fi
	fi
}

function echo_time {
	local duration=${SECONDS}

	echo "completed in $((${duration} / 60)) minutes and $((${duration} % 60)) seconds."
}

function enable_optional_settings {
	for arg in "${@}"
	do
		if [ "${arg}" == "--check-performance" ]
		then
			CHECK_PERFORMANCE=true

			echo "Performance check enabled"
		fi

		if [ "${arg}" == "--log" ]
		then
			DURATION_LOG=true

			echo "Log to record runs duration enabled"
		fi
	done
}

function log_durations {
	if [ ${DURATION_LOG} == true ]
	then
		echo "" >> ${ANT_ALL_DURATION_CSV_PATH}
		echo -n "$(date +%c)," >> ${ANT_ALL_DURATION_CSV_PATH}

		for (( i = 0 ; i < ${#DURATION_ARRAY[@]}; i++ ))
		do
			echo -n "${DURATION_ARRAY[${i}]}" >> ${ANT_ALL_DURATION_CSV_PATH}

			if [ "${i}" -lt "$((${#DURATION_ARRAY[@]} - 1))" ]
			then
				echo -n "," >> ${ANT_ALL_DURATION_CSV_PATH}
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
	enable_optional_settings "${@}"

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

	check_performance

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