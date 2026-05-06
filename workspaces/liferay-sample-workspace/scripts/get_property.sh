#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail

cd "$(dirname "${BASH_SOURCE[0]}")"

function main {
	local key=${1:-}

	if [ -z ${key} ]
	then
		echo "Usage: ${0} <key>" >&2

		exit 1
	fi

	local value=$(read_property ${key} ../gradle-local.properties)

	if [ -z ${value} ]
	then
		value=$(read_property ${key} ../gradle.properties)
	fi

	if [ -z ${value} ]
	then
		echo "Property \"${key}\" was not found." >&2

		exit 1
	fi

	echo ${value}
}

function read_property {
	local key=${1}
	local file=${2}

	if [ -f ${file} ]
	then
		grep "^${key}=" ${file} | cut --delimiter "=" --fields 2- | tr --delete "[:space:]"
	fi
}

main "${@}"