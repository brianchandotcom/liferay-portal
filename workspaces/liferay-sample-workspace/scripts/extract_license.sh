#!/bin/bash

cd "$(dirname "${BASH_SOURCE[0]}")"

source _common.sh

function main {
	local force=false

	if [[ ${1:-} == "-f" || ${1:-} == "--force" ]]
	then
		force=true
	fi

	local license_path="../build/docker/deploy/license.xml"

	if [[ -f ${license_path} && ${force} == false ]]
	then
		echo "Trial license already exists at ${license_path}."

		exit 0
	fi

	local license_file

	license_file=$(dirname "${license_path}")

	echo "Extracting trial license from liferay/dxp:latest."

	mkdir --parents "${license_file}"

	docker run \
		--entrypoint sh \
		--rm \
		--user root \
		--volume "${license_file}:/mnt/deploy" \
		"liferay/dxp:latest" \
		-c "cp /opt/liferay/deploy/trial-dxp-license*.xml /mnt/deploy/license.xml"
}

main "${@}"