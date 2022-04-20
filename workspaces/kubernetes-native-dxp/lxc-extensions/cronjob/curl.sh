#!/bin/sh

RESULT=$(curl \
  -s \
  --data client_id=$CLIENT_ID \
  --data client_secret=$CLIENT_SECRET \
  --data grant_type=client_credentials \
  --cacert ca.crt \
  "$TOKEN_URI")

ACCESS_TOKEN=$(echo "$RESULT" | jq -r '.access_token')

EMAIL_ADDRESS=$(curl \
  -L \
  -s \
  --cacert ca.crt \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  "$SERVICE_URI/o/headless-admin-user/v1.0/my-user-account" \
  | jq '.emailAddress')

echo "Email address of service account: $EMAIL_ADDRESS"