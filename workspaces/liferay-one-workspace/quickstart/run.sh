#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

source "${SCRIPT_DIR}/.env"

function clean {
	cd "${SCRIPT_DIR}"

	docker compose down --volumes
}

function download_hotfix {
	local dest="${SCRIPT_DIR}/liferay/patching/${HOTFIX_URL##*/}"

	if [[ -f "${dest}" ]]
	then
		return 0
	fi

	echo "Downloading ${HOTFIX_URL}."

	mkdir -p "$(dirname "${dest}")"

	curl --fail --location "${HOTFIX_URL}" --output "${dest}"
}

function download_license {
	local dest="${SCRIPT_DIR}/liferay/deploy/license.xml"

	if [[ -f "${dest}" ]]
	then
		return 0
	fi

	echo "Extracting trial license from DXP image."

	mkdir -p "$(dirname "${dest}")"

	docker run \
		--entrypoint sh \
		--rm \
		--user root \
		--volume "${SCRIPT_DIR}/liferay:/mnt/liferay" \
		"${LIFERAY_DOCKER_IMAGE}" \
		-c "cp /opt/liferay/deploy/trial-dxp-license*.xml /mnt/liferay/deploy/license.xml"
}

function down {
	cd "${SCRIPT_DIR}"

	docker compose down
}

function main {
	local command="${1:-}"

	if [[ "${command}" == "clean" ]]
	then
		clean
	elif [[ "${command}" == "down" ]]
	then
		down
	elif [[ "${command}" == "up" ]]
	then
		up
	else
		usage
	fi
}

function up {
	cd "${SCRIPT_DIR}"

	download_hotfix &

	local pid_hotfix

	pid_hotfix=$!

	download_license &

	local pid_license

	pid_license=$!

	wait "${pid_hotfix}"
	wait "${pid_license}"

	pushd .. > /dev/null

	./gradlew build

	popd > /dev/null

	docker compose up --detach

	wait_for_container liferay

	local container_id

	container_id="$(docker compose ps --quiet liferay)"

	echo "Deploying to container ${container_id}."

	pushd .. > /dev/null

	./gradlew deploy "-Ddeploy.docker.container.id=${container_id}"

	popd > /dev/null

	docker compose up --detach liferay
}

function usage {
	echo "Usage: $(basename "${BASH_SOURCE[0]}") {clean|down|up}"
	echo ""
	echo "  clean  Stop and remove all containers and volumes."
	echo "  down   Stop and remove all containers (volumes preserved)."
	echo "  up     Build the workspace, deploy to Docker, and attach to Liferay logs."

	exit 1
}

function wait_for_container {
	local service="$1"
	local status

	status="$(docker compose ps "${service}" --format '{{.Health}}' 2>/dev/null || true)"

	if [[ "${status}" == "healthy" ]]
	then
		return 0
	fi

	echo "Starting service ${service}."

	docker compose up --detach "${service}"

	while [[ "${status}" != "healthy" ]]
	do
		sleep 10

		echo "Waiting for service ${service}..."

		docker compose logs "${service}" --since=10s

		status="$(docker compose ps "${service}" --format '{{.Health}}' 2>/dev/null || true)"
	done

	echo "Service ${service} is healthy."
}

main "${@}"