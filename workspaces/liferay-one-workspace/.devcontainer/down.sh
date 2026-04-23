#!/bin/bash
#
# Stop the liferay-one-workspace devcontainer stack. Volumes are preserved
# (use reset.sh to wipe data).
#
# Usage: ./.devcontainer/down.sh [--core]
#
# --core Skip the extensions profile (mirrors up.sh --core).

set -eu

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

WORKSPACE_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"

cd "${WORKSPACE_DIR}"

profile="--profile extensions"

for arg in "$@"
do
	case "${arg}" in
	--core)
		profile=""
		;;
	*)
		echo "Unknown argument: ${arg}" >&2
		echo "Usage: $0 [--core]" >&2
		exit 2
		;;
	esac
done

exec docker compose --project-directory .devcontainer ${profile} down