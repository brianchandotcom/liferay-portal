#!/usr/bin/env bash
set -euo pipefail

CONTAINER=liferay-one-workspace-liferay
ROUTES=/opt/liferay/routes/default/liferay-one-site-initializer

CLIENT_ID=$(docker exec "$CONTAINER" cat "$ROUTES/liferay-one-site-initializer-oahs.oauth2.headless.server.client.id")
CLIENT_SECRET=$(docker exec "$CONTAINER" cat "$ROUTES/liferay-one-site-initializer-oahs.oauth2.headless.server.client.secret")

ENV_FILE="$(dirname "$0")/../tests/.env"

sed -i "s|^ONE_OAUTH_CLIENT_ID=.*|ONE_OAUTH_CLIENT_ID=$CLIENT_ID|" "$ENV_FILE"
sed -i "s|^ONE_OAUTH_CLIENT_SECRET=.*|ONE_OAUTH_CLIENT_SECRET=$CLIENT_SECRET|" "$ENV_FILE"

echo "OAuth credentials written to tests/.env"