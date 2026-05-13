#!/bin/bash

cd "$(dirname "${BASH_SOURCE[0]}")"

source _common.sh

function main {
	cd ..

	./gradlew deploy \
		-Ddeploy.docker.container.id="$(docker ps --quiet --filter "name=liferay")"
}

main "${@}"