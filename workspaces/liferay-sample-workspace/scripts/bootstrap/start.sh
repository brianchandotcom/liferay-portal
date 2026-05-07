#!/bin/bash

cd "$(dirname "${BASH_SOURCE[0]}")"

source ../_common.sh

function main {
	cd ../..

	if [ -f scripts/bootstrap/pre-start.sh ]
	then
		source scripts/bootstrap/pre-start.sh
	fi

	echo "Starting containers."
	docker compose --file docker-compose.yaml up --detach

	echo "Waiting for Liferay to be healthy."

	curl \
		--fail \
		--max-time 5 \
		--output /dev/null \
		--retry 60 \
		--retry-all-errors \
		--retry-delay 10 \
		--retry-max-time 600 \
		--silent \
		"http://localhost:8080/c/portal/status"

	if [ -f scripts/bootstrap/post-start.sh ]
	then
		source scripts/bootstrap/post-start.sh
	fi

	echo "Done. Liferay is running at http://localhost."
}

main "${@}"