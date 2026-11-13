#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

cd "$(dirname "${BASH_SOURCE[0]}")"

source _common.sh

function main {
	../../scripts/bootstrap.sh
}

main