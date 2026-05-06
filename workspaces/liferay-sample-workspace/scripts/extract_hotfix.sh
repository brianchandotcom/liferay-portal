#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail

SCRIPT_DIR=${BASH_SOURCE[0]%/*}

source "${SCRIPT_DIR}/_common.sh"

PATCHING_DIR="${SCRIPT_DIR}/../build/docker/patching"

function main {
	local hotfix_url="${1:-}"

	if [ -z "${hotfix_url}" ]
	then
		hotfix_url=$(get_gradle_property liferay.workspace.hotfix.url || true)
	fi

	if [ -z "${hotfix_url}" ]
	then
		exit 0
	fi

	local hotfix_file

	hotfix_file=$(basename "${hotfix_url%%\?*}")

	local dest="${PATCHING_DIR}/${hotfix_file}"

	if [ -f "${dest}" ]
	then
		echo "Hotfix already present: ${dest}"
		exit 0
	fi

	mkdir --parents "${PATCHING_DIR}"

	echo "Downloading ${hotfix_file} to ${dest}..."

	curl --fail --location --output "${dest}" "${hotfix_url}"

	echo "Hotfix ready at ${dest}"
}

main "${@}"