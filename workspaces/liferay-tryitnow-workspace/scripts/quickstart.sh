#!/bin/bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
WORKSPACE_DIR="${SCRIPT_DIR}/.."
CONTAINER_NAME="liferay-sample-workspace-liferay"

PRODUCT="$("${SCRIPT_DIR}/get-property.sh" liferay.workspace.product)"
VERSION_TAG="${PRODUCT#dxp-}"
IMAGE_NAME="liferay-sample-workspace-liferay"

echo "==> Extracting license..."
bash "${SCRIPT_DIR}/extract-license.sh"

echo "==> Extracting hotfix..."
bash "${SCRIPT_DIR}/extract-hotfix.sh"

echo "==> Building Docker image..."
"${WORKSPACE_DIR}/gradlew" --project-dir "${WORKSPACE_DIR}" buildDockerImage

echo "==> Tagging image as :local (${IMAGE_NAME}:${VERSION_TAG} -> ${IMAGE_NAME}:local)..."
docker tag "${IMAGE_NAME}:${VERSION_TAG}" "${IMAGE_NAME}:local"

echo "==> Starting containers..."
docker compose --file "${WORKSPACE_DIR}/docker-compose.yaml" up --detach

echo "==> Waiting for Liferay to be healthy..."
until curl --fail --max-time 5 --output /dev/null --silent "http://localhost:8080/c/portal/status"
do
	printf '.'
	sleep 10
done

echo "==> Deploying artifacts to Liferay container..."
bash "${SCRIPT_DIR}/deploy_client_extensions.sh"

echo "==> Done. Liferay is running at http://localhost:8080"