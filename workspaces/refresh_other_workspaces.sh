#!/bin/bash

cd $(dirname "${0}")

function main {
	for dir in "./"*
	do
		if [ ${dir} = "./liferay-sample-workspace" ] ||
		   [ -f ${dir} ]
		then
			continue
		fi

		rsync \
			-a --delete \
			--exclude "README.md" \
			--exclude "client-extensions" \
			--exclude "language" \
			--exclude "modules" \
			--exclude "node_modules" \
			--exclude "node_modules_cache" \
			--exclude "quickstart" \
			--exclude "poshi/build.gradle" \
			--exclude "poshi/poshi-ext.properties" \
			--exclude "poshi/src" \
			--exclude "themes" \
			liferay-sample-workspace/ ${dir}
	done
}

main "${@}"