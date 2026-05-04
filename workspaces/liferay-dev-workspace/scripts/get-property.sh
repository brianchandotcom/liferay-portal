#!/bin/bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
WORKSPACE_DIR="${SCRIPT_DIR}/.."

KEY="${1:?Usage: get-property.sh <key>}"

read_property() {
	local key="$1"
	local file="$2"

	if [ -f "${file}" ]; then
		grep "^${key}=" "${file}" | cut --delimiter== --fields=2- | tr --delete '[:space:]'
	fi
}

VALUE="$(read_property "${KEY}" "${WORKSPACE_DIR}/gradle-local.properties")"

if [ -z "${VALUE}" ]
then
	VALUE="$(read_property "${KEY}" "${WORKSPACE_DIR}/gradle.properties")"
fi

if [ -z "${VALUE}" ]
then
	echo "Property '${KEY}' not found." >&2
	exit 1
fi

echo "${VALUE}"