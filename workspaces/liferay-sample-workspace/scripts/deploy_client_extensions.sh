#!/bin/bash

cd "$(dirname "${BASH_SOURCE[0]}")"

source _common.sh

function main {
	cd ..

	local project_name

	project_name="$(basename "$(pwd)")"

	./gradlew deploy \
		-Ddeploy.docker.container.id="$(docker ps --quiet --filter "name=${project_name}-liferay")"
}

main "${@}"