#!/bin/bash

cd "$(dirname "${BASH_SOURCE[0]}")"

source _common.sh

function main {
	local dest="../build/docker/deploy/license.xml"

	if [[ -f ${dest} ]]
	then
		exit 0
	fi

	echo "Extracting trial license from liferay/dxp:latest."

	mkdir --parents "$(dirname "${dest}")"

	docker run \
		--entrypoint sh \
		--rm \
		--user root \
		--volume "$(dirname "${dest}"):/mnt/deploy" \
		"liferay/dxp:latest" \
		-c "cp /opt/liferay/deploy/trial-dxp-license*.xml /mnt/deploy/license.xml"
}

main "${@}"