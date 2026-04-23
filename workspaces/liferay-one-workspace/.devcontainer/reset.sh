#!/bin/bash
#
# DESTRUCTIVE. Stop the stack and wipe all named volumes (database, document
# library, routes). Equivalent to `docker compose down --volumes`.
#
# Usage: ./.devcontainer/reset.sh [--yes]

set -eu

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

WORKSPACE_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"

cd "${WORKSPACE_DIR}"

if [ "${1:-}" != "--yes" ]
then
	echo "This deletes the database and document library for liferay-one-workspace."
	echo "Pass --yes to proceed."
	exit 1
fi

docker compose --project-directory .devcontainer --profile extensions down --volumes