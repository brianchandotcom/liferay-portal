#!/bin/bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
WORKSPACE_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"

project_name="$(basename "${WORKSPACE_DIR}")"

"${WORKSPACE_DIR}/gradlew" deploy \
	-Ddeploy.docker.container.id="$(docker ps --quiet --filter "name=${project_name}-liferay")"