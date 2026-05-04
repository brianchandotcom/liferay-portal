#!/bin/bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
WORKSPACE_DIR="${SCRIPT_DIR}/.."

PATCHING_DIR="${WORKSPACE_DIR}/build/docker/patching"

HOTFIX_URL="${1:-}"

if [ -z "${HOTFIX_URL}" ]
then
	HOTFIX_URL="$("${SCRIPT_DIR}/get-property.sh" liferay.workspace.hotfix.url 2>/dev/null || true)"
fi

if [ -z "${HOTFIX_URL}" ]
then
	exit 0
fi

HOTFIX_FILE="$(basename "${HOTFIX_URL%%\?*}")"
DEST="${PATCHING_DIR}/${HOTFIX_FILE}"

if [ -f "${DEST}" ]
then
	echo "Hotfix already present: ${DEST}"
	exit 0
fi

mkdir -p "${PATCHING_DIR}"

echo "Downloading ${HOTFIX_FILE} to ${DEST}..."

curl --fail --location --output "${DEST}" "${HOTFIX_URL}"

echo "Hotfix ready at ${DEST}"