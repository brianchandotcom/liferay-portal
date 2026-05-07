#!/bin/bash

cd "$(dirname "${BASH_SOURCE[0]}")/.."

source _common.sh

function main {
	local product

	product="$(get_gradle_property liferay.workspace.product)"

	local version_tag="${product#dxp-}"

	cd ..

	if [ -f scripts/bootstrap/pre-build.sh ]
	then
		source scripts/bootstrap/pre-build.sh
	fi

	./gradlew clean

	bash scripts/bootstrap/extract_hotfix.sh
	bash scripts/bootstrap/extract_license.sh

	echo "Building Docker image."
	./gradlew buildDockerImage

	local workspace_name

	workspace_name="$(basename "$(pwd)")"

	echo "Tagging ${workspace_name}-liferay:${version_tag} as liferay:local."
	docker tag "${workspace_name}-liferay:${version_tag}" "liferay:local"

	if [ -f scripts/bootstrap/post-build.sh ]
	then
		source scripts/bootstrap/post-build.sh
	fi
}

main "${@}"