#!/bin/bash

cd "$(dirname "${BASH_SOURCE[0]}")"

source _common.sh

function main {
	local product

	product="$(get_gradle_property liferay.workspace.product)"

	local version_tag="${product#dxp-}"

	cd ..

	bash scripts/extract_hotfix.sh
	bash scripts/extract_license.sh

	echo "Building Docker image."
	./gradlew buildDockerImage

	local workspace_name

	workspace_name="$(basename "$(pwd)")"

	echo "Tagging ${workspace_name}-liferay:${version_tag} as liferay:local."
	docker tag "${workspace_name}-liferay:${version_tag}" "liferay:local"

	echo "Starting containers."
	docker compose --file docker-compose.yaml up --detach

	echo "Waiting for Liferay to be healthy."
	until curl --fail --max-time 5 --output /dev/null --silent "http://localhost:8080/c/portal/status"
	do
		printf '.'
		sleep 10
	done

	echo "Deploying artifacts to Liferay container."
	bash scripts/deploy_client_extensions.sh

	echo "Done. Liferay is running at http://localhost:8080."
}

main "${@}"