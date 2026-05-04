#!/bin/bash

function main {
	local SCRIPT_DIR

	SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

	local TESTS_DIR

	TESTS_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"

	local WORKSPACE_DIR

	WORKSPACE_DIR="$(cd "${TESTS_DIR}/.." && pwd)"

	cd "${WORKSPACE_DIR}"

	if [[ ! -f "${WORKSPACE_DIR}/.env" ]]
	then
		cp "${WORKSPACE_DIR}/.env.example" "${WORKSPACE_DIR}/.env"

		echo "Created .env from .env.example."
	else
		echo ".env already exists — leaving it alone."
	fi

	if [[ ! -d "${WORKSPACE_DIR}/node_modules" ]] || [[ ! -d "${TESTS_DIR}/node_modules/@playwright" ]]
	then
		echo "Running yarn install at workspace root."

		yarn install
	fi

	cd "${TESTS_DIR}"

	echo "Installing Playwright Chromium (skipped if already current)."

	yarn playwright install chromium

	echo "Bootstrap complete. Next: ./.devcontainer/up.sh && ./.devcontainer/doctor.sh"
}

main "${@}"