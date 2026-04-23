#!/bin/bash
#
# Start the liferay-one-workspace devcontainer stack.
#
# Usage: ./.devcontainer/up.sh [--core] [--foreground]
#
# --core Skip the extensions profile (cron + spring-boot).
# --foreground Attach to the compose process (default: detached).

set -eu

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

WORKSPACE_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"

cd "${WORKSPACE_DIR}"

profile="--profile extensions"
detach="-d"

for arg in "$@"
do
	case "${arg}" in
	--core)
		profile=""
		;;
	--foreground)
		detach=""
		;;
	*)
		echo "Unknown argument: ${arg}" >&2
		echo "Usage: $0 [--core] [--foreground]" >&2
		exit 2
		;;
	esac
done

DOCKER_GID="${DOCKER_GID:-$(getent group docker | cut -d: -f3 2>/dev/null || echo 969)}"
export DOCKER_GID

ports="1025 1080 3306 8000 8080 11311"

if [ -n "${profile}" ]
then
	ports="${ports} 58081"
fi

occupied=""

for port in ${ports}
do
	holder="$(ss -lnt "sport = :${port}" 2>/dev/null | awk 'NR==2 {print $1}')"

	if [ -n "${holder}" ]
	then
		owner="$(docker ps --filter "publish=${port}" --format '{{.Names}}' | head -1)"

		if [ -n "${owner}" ] && [ "${owner#liferay-one-workspace-}" != "${owner}" ]
		then
			continue
		fi

		if [ -n "${owner}" ]
		then
			occupied="${occupied}  ${port} → ${owner}\n"
		else
			occupied="${occupied}  ${port} (host process)\n"
		fi
	fi
done

if [ -n "${occupied}" ]
then
	echo "Port conflict. The following ports are in use by non-one processes:" >&2
	printf '%b' "${occupied}" >&2
	echo "Stop the conflicting containers/processes and retry." >&2
	exit 1
fi

echo "Starting liferay-one-workspace (DOCKER_GID=${DOCKER_GID}, profile=${profile:-none})."

exec docker compose --project-directory .devcontainer ${profile} up ${detach}