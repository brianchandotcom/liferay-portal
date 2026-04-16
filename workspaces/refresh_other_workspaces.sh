#!/bin/bash

cd "$(dirname "${0}")" || exit

function main {
	for dir in "./"*
	do
		if [ "${dir}" = "./liferay-sample-workspace" ] ||
		   [ -f "${dir}" ]
		then
			continue
		fi

		rsync \
			-a --delete \
			--exclude "build.gradle" \
			--exclude "package.json" \
			--exclude "README.md" \
			--exclude "test.properties" \
			--exclude "client-extensions" \
			--exclude "language" \
			--exclude "modules" \
			--exclude "node_modules" \
			--exclude "node_modules_cache" \
			--exclude "quickstart" \
			--exclude "poshi" \
			--exclude "themes" \
			liferay-sample-workspace/ "${dir}"
	done
}

main "${@}"