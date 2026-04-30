#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

function build {
	download_license &

	local pid_license
	pid_license=$!

	wait "${pid_license}"

	"${SCRIPT_DIR}/../gradlew" -p "${SCRIPT_DIR}/.." buildDockerImage

	local product_version
	product_version=$(grep "^liferay.workspace.product=" "${SCRIPT_DIR}/../gradle.properties" | cut -d= -f2 | sed 's/^dxp-//')

	local project_name
	project_name=$(basename "$(cd "${SCRIPT_DIR}/.." && pwd)")

	docker tag "${project_name}-liferay:${product_version}" "${project_name}-liferay:local"
}

function clean {
	docker compose --file "${SCRIPT_DIR}/docker-compose.yaml" --profile extensions down --volumes
}

function deploy {
	local project_name
	project_name=$(basename "$(cd "${SCRIPT_DIR}/.." && pwd)")

	"${SCRIPT_DIR}/../gradlew" -p "${SCRIPT_DIR}/.." deployDev \
		-Ddeploy.docker.container.id="$(docker ps -qf "name=${project_name}-liferay")"
}

function down {
	docker compose --file "${SCRIPT_DIR}/docker-compose.yaml" --profile extensions down
}

function download_license {
	local dest="${SCRIPT_DIR}/../build/docker/deploy/license.xml"

	if [ -f "${dest}" ]
	then
		return 0
	fi

	echo "Extracting trial license from liferay/dxp:latest."

	mkdir -p "$(dirname "${dest}")"

	docker run \
		--entrypoint sh \
		--rm \
		--user root \
		--volume "${SCRIPT_DIR}/../build/docker/deploy:/mnt/deploy" \
		"liferay/dxp:latest" \
		-c "cp /opt/liferay/deploy/trial-dxp-license*.xml /mnt/deploy/license.xml"
}

function help {
	echo "Usage: $(basename "${BASH_SOURCE[0]}") [build|clean|deploy|down|up|help]"
	echo ""
	echo "  (no args)  Build the workspace Docker image and start all services."
	echo ""
	echo "  build   Build the workspace Docker image."
	echo "  clean   Stop and remove all containers and volumes."
	echo "  deploy  Deploy modules to the running container."
	echo "  down    Stop and remove all containers (volumes preserved)."
	echo "  help    Show this help message."
	echo "  up      Start all services (assumes image is already built)."
}

function main {
	local command="${1:-}"

	if [ -z "${command}" ]
	then
		build
		up
	elif [ "${command}" == "build" ]
	then
		build
	elif [ "${command}" == "clean" ]
	then
		clean
	elif [ "${command}" == "deploy" ]
	then
		deploy
	elif [ "${command}" == "down" ]
	then
		down
	elif [ "${command}" == "up" ]
	then
		up
	else
		help
	fi
}

function up {
	docker compose --file "${SCRIPT_DIR}/docker-compose.yaml" --profile extensions up --detach
}

main "${@}"