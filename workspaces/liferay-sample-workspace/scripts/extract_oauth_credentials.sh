#!/bin/bash

cd "$(dirname "${BASH_SOURCE[0]}")"

source _common.sh

function main {
	local oauth_application_name="${1}"

	if [[ -z ${oauth_application_name} ]]
	then
		echo "Please pass in the name of your OAuth application."
		exit 1
	fi

	local container_name="liferay"

	local routes_dir="/opt/liferay/routes/default/${oauth_application_name}"

	local client_id

	client_id="$(docker exec "${container_name}" cat "${routes_dir}/${oauth_application_name}.oauth2.headless.server.client.id")"

	local client_secret

	client_secret="$(docker exec "${container_name}" cat "${routes_dir}/${oauth_application_name}.oauth2.headless.server.client.secret")"

	local env_file="../.env"

	if [[ ! -f ${env_file} ]]
	then
		printf "OAUTH_CLIENT_ID=\nOAUTH_CLIENT_SECRET=\n" > "${env_file}"
	fi

	grep --quiet "^OAUTH_CLIENT_ID=" "${env_file}" || echo "OAUTH_CLIENT_ID=" >> "${env_file}"
	grep --quiet "^OAUTH_CLIENT_SECRET=" "${env_file}" || echo "OAUTH_CLIENT_SECRET=" >> "${env_file}"

	sed --in-place "s|^OAUTH_CLIENT_ID=.*|OAUTH_CLIENT_ID=${client_id}|" "${env_file}"
	sed --in-place "s|^OAUTH_CLIENT_SECRET=.*|OAUTH_CLIENT_SECRET=${client_secret}|" "${env_file}"

	echo "OAuth credentials were written to .env."
	echo "OAUTH_CLIENT_ID=${client_id}"
	echo "OAUTH_CLIENT_SECRET=${client_secret}"
}

main "${@}"