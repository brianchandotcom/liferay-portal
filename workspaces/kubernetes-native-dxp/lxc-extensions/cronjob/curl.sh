#!/bin/sh

# env | sort

RESULT=$(curl \
  -s \
  --data client_id=$LIFERAY_OAUTH2_HEADLESS_SERVER_CLIENT_ID \
  --data client_secret=$LIFERAY_OAUTH2_HEADLESS_SERVER_CLIENT_SECRET \
  --data grant_type=client_credentials \
  --cacert ca.crt \
  "$LIFERAY_OAUTH2_TOKEN_URI")

ACCESS_TOKEN=$(echo "$RESULT" | jq -r '.access_token')

LCP_DOMAIN="${LCP_PROJECT_ID}.${LCP_SERVICE_DOMAIN}"
if [ "${WEBSERVER_SERVICE_HOST}x" != "x" ];then
  LCP_DOMAIN="webserver-${LCP_DOMAIN}"
fi

EMAIL_ADDRESS=$(curl \
  -L \
  -s \
  --cacert ca.crt \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  "https://${LCP_DOMAIN}/o/headless-admin-user/v1.0/my-user-account" \
  | jq '.emailAddress')

echo "Email address of service account: $EMAIL_ADDRESS"