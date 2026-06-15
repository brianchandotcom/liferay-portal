#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail

function get_gradle_property {
	local key="${1}"

	local value

	value=$(_read_property "${key}" ../gradle-local.properties)

	if [[ -z ${value} ]]
	then
		value=$(_read_property "${key}" ../gradle.properties)
	fi

	if [[ -z ${value} ]]
	then
		echo "Property \"${key}\" was not found." >&2

		return 1
	fi

	echo "${value}"
}

function _read_property {
	local key="${1}"
	local file="${2}"

	if [[ -f ${file} ]]
	then
		grep "^${key}=" "${file}" | cut --delimiter = --fields 2- | tr --delete "[:space:]"
	fi
}