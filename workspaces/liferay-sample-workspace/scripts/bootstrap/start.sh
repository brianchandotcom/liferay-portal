#!/usr/bin/env bash

cd "$(dirname "${BASH_SOURCE[0]}")"

source ../_common.sh

function main {
	if [[ -f pre-start.sh ]]
	then
		source pre-start.sh
	fi

	echo "Starting containers."

	docker_compose up --detach

	echo "Waiting for Liferay to be healthy."

	local liferay_url=http://localhost:${LIFERAY_HTTP_PORT:-8080}

	curl \
		--fail \
		--max-time 5 \
		--output /dev/null \
		--retry 60 \
		--retry-all-errors \
		--retry-delay 10 \
		--retry-max-time 600 \
		--silent \
		"${liferay_url}/c/portal/status"

	if [[ -f post-start.sh ]]
	then
		source post-start.sh
	fi

	echo "Liferay is running at ${liferay_url}."
}

main "${@}"