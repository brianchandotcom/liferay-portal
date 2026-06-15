#!/bin/bash

cd "$(dirname "${BASH_SOURCE[0]}")"

source _common.sh

function main {
	local oauth_application_name=${1:-}

	if [[ -z ${oauth_application_name} ]]
	then
		echo "The OAuth application name was not provided." >&2

		exit 1
	fi

	local routes_dir=/opt/liferay/routes/default/${oauth_application_name}

	local client_id

	client_id=$(docker exec liferay cat "${routes_dir}/${oauth_application_name}.oauth2.headless.server.client.id")

	local client_secret

	client_secret=$(docker exec liferay cat "${routes_dir}/${oauth_application_name}.oauth2.headless.server.client.secret")

	local env_file=../.env

	touch "${env_file}"

	sed --in-place --regexp-extended "/^OAUTH_CLIENT_(ID|SECRET)=/d" "${env_file}"

	{
		echo "OAUTH_CLIENT_ID=${client_id}"
		echo "OAUTH_CLIENT_SECRET=${client_secret}"
	} >> "${env_file}"

	echo "The OAuth credentials were written to ${env_file}."
}

main "${@}"