#!/bin/bash
#
# Print a compact status of the liferay-one-workspace devcontainer: services,
# health, ports.
#
# Usage: ./.devcontainer/status.sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

WORKSPACE_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"

cd "${WORKSPACE_DIR}"

docker compose --project-directory .devcontainer ps --format 'table {{.Name}}\t{{.Service}}\t{{.Status}}\t{{.Ports}}'