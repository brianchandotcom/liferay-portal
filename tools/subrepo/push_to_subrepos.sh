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

GRADLE_FILE_SHAS=($(
  for FILE in "${GRADLE_FILES[@]}"; do
    SHA="$(git hash-object "${FILE}")"

    if [[ -z "${SHA}" ]]; then
      error "Unable to parse sha for the file '${FILE}'."
    fi

    echo "${FILE}:${SHA}"
  done
))

SUBREPO=$1

# if [[ "${SUBREPO}" == *"-private" ]]; then
#   BRANCHES=(
#     "7.0.x-private"
#     "master-private"
#   )
# else
#   BRANCHES=(
#     "7.0.x"
#     "7.0.x-private"
#     "master"
#     "master-private"
#   )
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
done