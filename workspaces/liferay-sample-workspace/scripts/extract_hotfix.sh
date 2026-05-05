#!/bin/bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
WORKSPACE_DIR="${SCRIPT_DIR}/.."

PATCHING_DIR="${WORKSPACE_DIR}/build/docker/patching"

function main {
	local hotfix_url="${1:-}"

	if [ -z "${hotfix_url}" ]
	then
		hotfix_url="$("${SCRIPT_DIR}/get_property.sh" liferay.workspace.hotfix.url 2>/dev/null || true)"
	fi

	if [ -z "${hotfix_url}" ]
	then
		exit 0
	fi

	local hotfix_file
	hotfix_file="$(basename "${hotfix_url%%\?*}")"

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