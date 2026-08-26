#!/usr/bin/env bash
# ---------------------------------------------------------------------------
# run-local.sh – Build and run the Forums microservice locally
#
# Usage:
# ./run-local.sh Build the bootJar and start the Spring Boot app
# ./run-local.sh --skip-build Start from an already-built jar (faster restart)
#
# Environment variables can be supplied via a .env file in this directory.
# See .env.example for the expected keys.
#
# The microservice receives Liferay Object Action webhooks (new ForumMessage /
# new ForumThread) and notifies forum subscribers by email and in-portal
# notification. It must point at the Liferay instance that ISSUES the
# object-action JWTs — the same instance whose JWKS validates them and whose
# headless APIs it calls back into. Set LIFERAY_BASE_URL / LIFERAY_DXP_HOST
# accordingly (see .env.example).
# ---------------------------------------------------------------------------
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "${SCRIPT_DIR}/../.." && pwd)"

# ── Load .env file if present ───────────────────────────────────────────────
if [[ -f "${SCRIPT_DIR}/.env" ]]; then
	echo "📄 Loading environment from ${SCRIPT_DIR}/.env"
	set -a
	# shellcheck disable=SC1091
	source "${SCRIPT_DIR}/.env"
	set +a
fi

# ── Create LXC configtree directories ───────────────────────────────────────
#
# On Liferay Cloud (LCP), the Spring Boot client extension library reads
# properties from "configtree" directories mounted by the platform:
#
# - /etc/liferay/lxc/dxp-metadata → DXP connection properties
# - /etc/liferay/lxc/ext-init-metadata → client extension OAuth metadata
#
# Each file in these directories becomes a Spring property where the filename
# is the property key and the file content is the value. When running locally,
# these directories do not exist, so the script creates them in a temporary
# location and points the env vars accordingly.
#
# The DXP metadata must point at the Liferay instance that ISSUES the
# object-action JWTs (the same instance whose JWKS validates them, and whose
# headless APIs the service calls back into).
# ---------------------------------------------------------------------------

LIFERAY_DXP_HOST="${LIFERAY_DXP_HOST:-localhost:8080}"
LIFERAY_DXP_PROTOCOL="${LIFERAY_DXP_PROTOCOL:-http}"

CONFIGTREE_DIR="${SCRIPT_DIR}/.configtree"
DXP_METADATA_DIR="${CONFIGTREE_DIR}/dxp-metadata"
EXT_METADATA_DIR="${CONFIGTREE_DIR}/ext-init-metadata"

mkdir -p "${DXP_METADATA_DIR}" "${EXT_METADATA_DIR}"

# DXP metadata – consumed by the spring-boot3 client-extension util library's
# OAuth2 resource-server security config (JWT validation).
printf '%s' "${LIFERAY_DXP_HOST}" > "${DXP_METADATA_DIR}/com.liferay.lxc.dxp.domains"
printf '%s' "${LIFERAY_DXP_HOST}" > "${DXP_METADATA_DIR}/com.liferay.lxc.dxp.mainDomain"
printf '%s' "${LIFERAY_DXP_PROTOCOL}" > "${DXP_METADATA_DIR}/com.liferay.lxc.dxp.server.protocol"

echo "📂 Created LXC configtree at ${CONFIGTREE_DIR}"
echo "   DXP domain:   ${LIFERAY_DXP_PROTOCOL}://${LIFERAY_DXP_HOST}"

# Point the env vars that application.properties references
export LIFERAY_ROUTES_DXP="${DXP_METADATA_DIR}"
export LIFERAY_ROUTES_CLIENT_EXTENSION="${EXT_METADATA_DIR}"

# ── Resolve Gradle wrapper ──────────────────────────────────────────────────
GRADLEW="${PROJECT_ROOT}/gradlew"
if [[ ! -x "${GRADLEW}" ]]; then
	echo "❌ Gradle wrapper not found or not executable at ${GRADLEW}"
	exit 1
fi

# ── Build (unless --skip-build) ─────────────────────────────────────────────
SKIP_BUILD=false
for arg in "$@"; do
	[[ "${arg}" == "--skip-build" ]] && SKIP_BUILD=true
done

if [[ "${SKIP_BUILD}" == false ]]; then
	echo "🔨 Building liferay-forums-etc-spring-boot bootJar …"
	"${GRADLEW}" -p "${PROJECT_ROOT}" :client-extensions:liferay-forums-etc-spring-boot:bootJar
	echo ""
fi

# ── Locate the boot jar ─────────────────────────────────────────────────────
# Exclude the "-plain.jar" produced alongside the bootJar (the java-library
# plugin's plain jar is not an executable Spring Boot jar).
BUILD_DIR="${SCRIPT_DIR}/build/libs"
BOOT_JAR=$(find "${BUILD_DIR}" -maxdepth 1 -name "*.jar" ! -name "*-plain.jar" -type f 2>/dev/null | head -1)

if [[ -z "${BOOT_JAR}" ]]; then
	echo "❌ No jar found in ${BUILD_DIR}. Run without --skip-build first."
	exit 1
fi

echo ""
echo "🚀 Starting liferay-forums-etc-spring-boot from: $(basename "${BOOT_JAR}")"
echo "   Port:  58082"
echo "   Ready: http://localhost:58082/ready"
echo ""

# ── Run the Spring Boot application ─────────────────────────────────────────
exec java \
	--add-opens=java.base/java.lang.invoke=ALL-UNNAMED \
	-XX:MaxRAMPercentage=50.0 \
	-Dspring.profiles.active=default,local \
	-jar "${BOOT_JAR}"