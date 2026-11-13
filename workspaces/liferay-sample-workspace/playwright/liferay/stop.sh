#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

cd "$(dirname "${BASH_SOURCE[0]}")"

source _common.sh

function main {
	docker compose --project-name "${COMPOSE_PROJECT_NAME}" down --volumes
}

main