#!/bin/bash

cd "$(dirname "${BASH_SOURCE[0]}")"

source _common.sh

function main {
	local site_initializer="${1}"

	if [ -z "${site_initializer}" ]; then
		echo "Usage: $(basename "${0}") <route-name>"
		exit 1
	fi

	local project_name

	project_name="$(basename "$(cd .. && pwd)")"

	local container="${project_name}-liferay"
	local routes_base="/opt/liferay/routes/default"
	local routes="${routes_base}/${site_initializer}"

	local client_id

	client_id="$(docker exec "${container}" cat "${routes}/${site_initializer}-oahs.oauth2.headless.server.client.id")"

	local client_secret

	client_secret="$(docker exec "${container}" cat "${routes}/${site_initializer}-oahs.oauth2.headless.server.client.secret")"

	local env_file="../.env"

	if [ ! -f "${env_file}" ]
	then
		printf "OAUTH_CLIENT_ID=\nOAUTH_CLIENT_SECRET=\n" > "${env_file}"
	fi

	grep --quiet "^OAUTH_CLIENT_ID=" "${env_file}" || echo "OAUTH_CLIENT_ID=" >> "${env_file}"
	grep --quiet "^OAUTH_CLIENT_SECRET=" "${env_file}" || echo "OAUTH_CLIENT_SECRET=" >> "${env_file}"

	sed --in-place "s|^OAUTH_CLIENT_ID=.*|OAUTH_CLIENT_ID=${client_id}|" "${env_file}"
	sed --in-place "s|^OAUTH_CLIENT_SECRET=.*|OAUTH_CLIENT_SECRET=${client_secret}|" "${env_file}"

	echo "OAuth credentials written to .env."
	echo "OAUTH_CLIENT_ID=${client_id}"
	echo "OAUTH_CLIENT_SECRET=${client_secret}"
}

main "${@}"