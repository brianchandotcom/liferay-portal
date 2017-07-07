#!/bin/bash
#
# Author: Samuel Trong Tran <samuel.tran@liferay.com>
# Platform: Linux/Unix
# Revision: 0.1.0
#

error() {
	MESSAGE=$1

	echo "error: ${MESSAGE}" >&2

	exit 1
}

if [[ "${PWD}" != *"/tools/subrepo" ]]; then
	error "This script can only be run from within the tools/subrepo directory."
fi

if [[ "${PWD}" != *"liferay-portal/tools/subrepo" ]]; then
	error "This script can only be run from within the liferay-portal repository."
fi

if [[ "$(git rev-parse --abbrev-ref HEAD)" != "master" ]]; then
	error "This script can only be run from the master branch."
fi

if [[ -z "${GITHUB_API_TOKEN}" ]]; then
	GITHUB_API_TOKEN="$(git config github.token)"
fi

if [[ -z "${GITHUB_API_TOKEN}" ]]; then
	error "Your GitHub api token is not set. Set it using the GITHUB_API_TOKEN environment variable, or with 'git config github.token'."
fi

GRADLE_FILES=(
	"gradle/wrapper/gradle-wrapper.jar"
	"gradle/wrapper/gradle-wrapper.properties"
	"gradlew"
	"gradlew.bat"
)

LOCAL_SHAS=($(
	for FILE in "${GRADLE_FILES[@]}"; do
		SHA="$(git hash-object "${FILE}")"

		if [[ -z "${SHA}" ]]; then
			error "Unable to parse sha for the file '${FILE}'."
		fi

		echo "${FILE}:${SHA}"
	done
))

SUBREPO=$1

if [[ -z "${SUBREPO}" ]]; then
	echo "Usage: ./push_to_subrepos.sh SUBREPO" >&2

	exit 1
fi

# if [[ "${SUBREPO}" == *"-private" ]]; then
# 	BRANCHES=(
# 		"7.0.x-private"
# 		"master-private"
# 	)
# else
# 	BRANCHES=(
# 		"7.0.x"
# 		"7.0.x-private"
# 		"master"
# 		"master-private"
# 	)
# fi

BRANCHES=(
	"7.0.x"
	"master"
)

for BRANCH in "${BRANCHES[@]}"; do
	REMOTE="liferay/${SUBREPO}"

	if [[ "${BRANCH}" == *"-private" ]] && [[ "${SUBREPO}" != *"-private" ]]; then
		REMOTE="${REMOTE}-private"
	fi

	GRADLE_WRAPPER_JSON=

	for i in {1..2}; do
		GRADLE_WRAPPER_JSON=($(curl --header "Authorization: token ${GITHUB_API_TOKEN}" -s "https://api.github.com/repos/liferay/${SUBREPO}/contents/gradle/wrapper?ref=${BRANCH}" -X GET | tr '\n' ' ' | sed 's/ //g' | sed 's/},/\'$'\n/g'))

		if [[ $(echo "${GRADLE_WRAPPER_JSON[@]}" | grep '\"sha\"') ]]; then
			break;
		fi
	done

	if [[ -z $(echo "${GRADLE_WRAPPER_JSON[@]}" | grep '\"sha\"') ]] && [[ -z $(echo "${GRADLE_WRAPPER_JSON[@]}" | grep '\"NotFound\"') ]]; then
		error "Failed to get contents of gradle/wrapper directory via the api for liferay/${SUBREPO}:${BRANCH}"
	fi

	GRADLE_WRAPPER_JAR_REMOTE_SHA="$(printf '%s\n' "${GRADLE_WRAPPER_JSON[@]}" | grep '"gradle-wrapper.jar"' | sed 's/",/&\'$'\n/g' | grep '"sha"' | sed 's/"[^"]*$//' | sed 's/.*"//')"
	GRADLE_WRAPPER_PROPERTIES_REMOTE_SHA="$(printf '%s\n' "${GRADLE_WRAPPER_JSON[@]}" | grep '"gradle-wrapper.properties"' | sed 's/",/&\'$'\n/g' | grep '"sha"' | sed 's/"[^"]*$//' | sed 's/.*"//')"

	GRADLEW_REMOTE_SHA="$(curl --header "Authorization: token ${GITHUB_API_TOKEN}" -s "https://api.github.com/repos/liferay/${SUBREPO}/contents/gradlew?ref=${BRANCH}" -X GET | grep '"sha"' | sed 's/"[^"]*$//' | sed 's/.*"//')"
	GRADLEW_BAT_REMOTE_SHA="$(curl --header "Authorization: token ${GITHUB_API_TOKEN}" -s "https://api.github.com/repos/liferay/${SUBREPO}/contents/gradlew.bat?ref=${BRANCH}" -X GET | grep '"sha"' | sed 's/"[^"]*$//' | sed 's/.*"//')"

	REMOTE_SHAS=(
		"gradle/wrapper/gradle-wrapper.jar:${GRADLE_WRAPPER_JAR_REMOTE_SHA}"
		"gradle/wrapper/gradle-wrapper.properties:${GRADLE_WRAPPER_PROPERTIES_REMOTE_SHA}"
		"gradlew:${GRADLEW_REMOTE_SHA}"
		"gradlew.bat:${GRADLEW_BAT_REMOTE_SHA}"
	)
done