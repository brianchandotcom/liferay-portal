#!/bin/bash

function check_health {
	docker inspect --format='{{.State.Health.Status}}' "${1}" | grep -q "healthy"

	if [[ $? -eq 0 ]]; then
		echo "Container ${1} is healthy."

		return 0
	fi

	echo "Container ${1} is not healthy."

	sleep 10

	check_health "${1}"
}

function check_logs {
	docker logs -f "${container}" | grep -q "${1}"

	if [[ $? -eq 0 ]]; then
		echo "Text ${1} found in logs."

		return 0
	fi

	echo "Text ${1} not found in logs."

	sleep 10

	check_logs "${1}"
}

function deploy {
	./gradlew :client-extensions:${1}:clean :client-extensions:${1}:deploy "-Ddeploy.docker.container.id=${container}"
}

function main {
	local container="liferay-partner-workspace-liferay"

	docker compose up -d

	check_health ${container}

	pushd .. > /dev/null

	deploy "liferay-partner-custom-element"
	deploy "liferay-partner-etc-cron"
	deploy "liferay-partner-etc-spring-boot"
	deploy "liferay-partner-site-initializer-code"

	check_logs "Initialized Liferay Partner"

	deploy "liferay-partner-site-initializer-quickstart"

	if command -v op &> /dev/null
	then
		op read "op://Customer Solutions/PRM UAT SFDC CX/notesPlain" --out-file ./client-extensions/liferay-partner-instance-settings-quickstart/client-extension.yaml

		deploy "liferay-partner-instance-settings-quickstart"
	fi

	popd > /dev/null

	docker compose up
}

main "${@}"