#!/bin/bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

PROJECT_NAME=$(basename "$(cd "${SCRIPT_DIR}/../.." && pwd)")
CONTAINER="${PROJECT_NAME}-liferay"

ROUTES_BASE=/opt/liferay/routes/default
SITE_INITIALIZER=$(docker exec "${CONTAINER}" ls "${ROUTES_BASE}" | head -1)
ROUTES="${ROUTES_BASE}/${SITE_INITIALIZER}"

CLIENT_ID=$(docker exec "${CONTAINER}" cat "${ROUTES}/${SITE_INITIALIZER}-oahs.oauth2.headless.server.client.id")
CLIENT_SECRET=$(docker exec "${CONTAINER}" cat "${ROUTES}/${SITE_INITIALIZER}-oahs.oauth2.headless.server.client.secret")

ENV_FILE="${SCRIPT_DIR}/../.env"

sed -i "s|^OAUTH_CLIENT_ID=.*|OAUTH_CLIENT_ID=${CLIENT_ID}|" "${ENV_FILE}"
sed -i "s|^OAUTH_CLIENT_SECRET=.*|OAUTH_CLIENT_SECRET=${CLIENT_SECRET}|" "${ENV_FILE}"

echo "OAuth credentials written to .env"