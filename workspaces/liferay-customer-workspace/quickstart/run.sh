#!/bin/bash

function check_health {
	docker inspect --format="{{.State.Health.Status}}" "${container_id}" | grep --quiet "${1}"

	if [[ $? -eq 0 ]]
	then
		echo "Container ${container_id} is ${1}."

		return 0
	fi

	echo "Container ${container_id} is not ${1}."

	sleep 10

	check_health "${1}"
}

function check_logs {
	docker logs "${container_id}" 2>&1 | grep --extended-regexp --quiet "${1}"

	if [[ $? -eq 0 ]]
	then
		echo "Text found in logs: ${1}"

		return 0
	fi

	echo "Text not found in logs: ${1}"

	sleep 10

	check_logs "${1}"
}

function download_hotfix {
	local files=(
		"https://releases-cdn.liferay.com/tools/patching-tool/patching-tool-4.0.3.zip"
		"https://releases-cdn.liferay.com/dxp/hotfix/2024.q2.7/liferay-dxp-2024.q2.7-hotfix-4.zip"
	)

	for file_url in "${files[@]}"
	do
		local file_name=$(basename "${file_url}")

		local file_path="./liferay/patching/${file_name}"

		if [ ! -f "${file_path}" ]
		then
			echo "Downloading ${file_url} to ${file_path}"

			mkdir --parents $(dirname "${file_path}")

			curl --location "${file_url}" --output "${file_path}"
		fi
	done
}

function get_container_id {
	local container_id=$(docker compose ps --quiet "${1}")

	if [[ -n "${container_id}" ]]
	then
		echo "${container_id}"

		return 0
	fi

	sleep 5

	get_container_id "${1}"
}

function main {
	download_hotfix

	docker compose up --detach

	local container_id=$(get_container_id "liferay")

	check_health "healthy"

	check_logs "Server startup in \[[0-9]+\] milliseconds"

	pushd .. > /dev/null

	./gradlew clean deploy "-Ddeploy.docker.container.id=${container_id}"

	popd > /dev/null

	docker compose up
}

main "${@}"