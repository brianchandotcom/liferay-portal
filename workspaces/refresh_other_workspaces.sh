#!/bin/bash

cd "$(dirname "${0}")" || exit

function main {
	for dir in "./"*
	do
		if [ "${dir}" = "./liferay-sample-workspace" ] ||
		   [ ! -d "${dir}" ]
		then
			continue
		fi

		rsync \
			-a --delete \
			--exclude "build.gradle" \
			--exclude "docker-compose.yaml" \
			--exclude "gradle-local.properties" \
			--exclude "package.json" \
			--exclude "portal-env.properties" \
			--exclude "README.md" \
			--exclude "test.properties" \
			--exclude "yarn.lock" \
			--exclude "client-extensions" \
			--exclude "language" \
			--exclude "modules" \
			--exclude "node_modules" \
			--exclude "node_modules_cache" \
			--exclude "poshi" \
			--exclude "themes" \
			liferay-sample-workspace/ "${dir}"
	done
}

main "${@}"