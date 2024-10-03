#!/bin/bash

function check_health {
	docker inspect --format="{{.State.Health.Status}}" "${container_id}" | grep --quiet "healthy"

	if [[ $? -eq 0 ]]
	then
		echo "Container ${container_id} is healthy."

		return 0
	fi

	echo "Container ${container_id} is not healthy."

	sleep 10

	check_health
}

function download_hotfix {
	for file_url in \
		"https://releases-cdn.liferay.com/dxp/hotfix/2024.q2.7/liferay-dxp-2024.q2.7-hotfix-4.zip" \
		"https://releases-cdn.liferay.com/tools/patching-tool/patching-tool-4.0.3.zip"
	do
		local file_name="./liferay/patching/$(basename "${file_url}")"

		if [ ! -f "${file_name}" ]
		then
			echo "Downloading ${file_url} to ${file_name}."

			mkdir --parents $(dirname "${file_name}")

			curl --location "${file_url}" --output "${file_name}"
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

	pushd .. > /dev/null

	./gradlew clean build

	popd > /dev/null

	docker compose up --detach database liferay

	local container_id=$(get_container_id "liferay")

	check_health

	pushd .. > /dev/null

	./gradlew deploy "-Ddeploy.docker.container.id=${container_id}"

	popd > /dev/null

	docker compose up liferay
}

main "${@}"