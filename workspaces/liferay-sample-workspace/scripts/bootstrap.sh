#!/bin/bash

cd "$(dirname "${BASH_SOURCE[0]}")"

source _common.sh

function main {
	local reset="false"

	for arg in "${@}"
	do
		if [ "${arg}" == "--reset" ]
		then
			reset="true"
		fi
	done

	cd ..

	if [ "${reset}" == "true" ]
	then
		echo "Tearing down containers and volumes."
		docker compose --file docker-compose.yaml down --volumes
	fi

	bash scripts/bootstrap/build.sh

	bash scripts/bootstrap/start.sh
}

main "${@}"