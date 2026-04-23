#!/bin/bash

set -eu

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
WORKSPACE_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"

cd "${WORKSPACE_DIR}"

echo "Installing JavaScript dependencies."

yarn install --frozen-lockfile

echo "Building all client extensions."

./gradlew build

liferay_id=$(docker inspect --format="{{.Id}}" liferay-one-workspace-liferay)

echo "Deploying client extensions into liferay-one-workspace-liferay (${liferay_id})."

./gradlew deploy "-Ddeploy.docker.container.id=${liferay_id}"