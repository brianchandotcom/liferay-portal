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
			--exclude "Jenkinsfile" \
			--exclude "README.md" \
			--exclude "build.gradle" \
			--exclude "client-extensions" \
			--exclude "language" \
			--exclude "modules" \
			--exclude "node_modules" \
			--exclude "node_modules_cache" \
			--exclude "package.json" \
			--exclude "poshi" \
			--exclude "quickstart" \
			--exclude "test.properties" \
			--exclude "themes" \
			--exclude "yarn.lock" \
			liferay-sample-workspace/ "${dir}"
	done
}

main "${@}"