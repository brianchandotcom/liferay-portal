#!/bin/bash
#
# Tail logs for a service in the liferay-one-workspace devcontainer.
#
# Usage: ./.devcontainer/logs.sh [service] [--follow] [--tail N]
#
# Default service: liferay.

set -eu

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

WORKSPACE_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"

cd "${WORKSPACE_DIR}"

service="liferay"
follow=""
tail="80"

while [ $# -gt 0 ]
do
	case "$1" in
	--follow|-f)
		follow="--follow"
		shift
		;;
	--tail)
		tail="$2"
		shift 2
		;;
	-*)
		echo "Unknown flag: $1" >&2
		exit 2
		;;
	*)
		service="$1"
		shift
		;;
	esac
done

exec docker compose --project-directory .devcontainer logs --tail "${tail}" ${follow} "${service}"