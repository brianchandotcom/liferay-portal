#!/usr/bin/env bash

cd "$(dirname "${BASH_SOURCE[0]}")"

source ../_common.sh

function main {
	local force=false

	if [[ ${1:-} == -f ]] ||
	   [[ ${1:-} == --force ]]
	then
		force=true
	fi

	touch ../../.env

	if command grep --quiet "^LIFERAY_OAUTH2_JWT_ACCESS_TOKEN_SIGNING_JSON_WEB_KEY=" ../../.env &&
	   [[ ${force} == false ]]
	then
		echo "An OAuth2 JWT signing key already exists in .env. Run with --force to replace it."

		exit 0
	fi

	if ! command -v node > /dev/null
	then
		_die "Node.js is required to generate the signing key."
	fi

	local json_web_key

	json_web_key=$(node --eval '
		const {generateKeyPairSync, randomUUID} = require("node:crypto");

		const {privateKey} = generateKeyPairSync("rsa", {modulusLength: 2048});

		const jsonWebKey = privateKey.export({format: "jwk"});

		jsonWebKey.alg = "RS256";
		jsonWebKey.kid = randomUUID();
		jsonWebKey.use = "sig";

		console.log(JSON.stringify(jsonWebKey));
	')

	sed \
		--in-place \
		--regexp-extended \
		--expression "/^LIFERAY_OAUTH2_(ISSUE_JWT_ACCESS_TOKEN|JWT_ACCESS_TOKEN_SIGNING_JSON_WEB_KEY)=/d" \
		../../.env

	{
		echo "LIFERAY_OAUTH2_ISSUE_JWT_ACCESS_TOKEN=true"
		echo "LIFERAY_OAUTH2_JWT_ACCESS_TOKEN_SIGNING_JSON_WEB_KEY='${json_web_key}'"
	} >> ../../.env

	echo "The OAuth2 JWT signing key was written to .env. Recreate the Liferay container with ./scripts/bootstrap/start.sh to apply it."
}

main "${@}"