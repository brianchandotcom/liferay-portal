#!/bin/bash
#
# Author: Samuel Trong Tran <samuel.tran@liferay.com>
# Platform: Linux/Unix
#

set -o pipefail

error() {
	MESSAGE=$1

	warn "error: ${MESSAGE}"

	exit 1
}

get_module_reference() {
	BRANCH=$1

	MODULE_REFERENCE=

	for MODULE_PATH in $(git -C "${REPO_PATH}" ls-tree --name-only -r "refs/remotes/upstream/${BRANCH}" | grep '^modules/' | grep '/bnd.bnd$' | sed 's@/bnd.bnd$@@')
	do
		MODULE_REFERENCE="${MODULE_REFERENCE}$(git -C "${REPO_PATH}" ls-tree --full-tree "refs/remotes/upstream/${BRANCH}" "${MODULE_PATH}" | sed 's/[0-9]* tree //')\n"
	done

	MODULE_REFERENCE="$(echo -en "${MODULE_REFERENCE}")"
}

help() {
	info "Usage: ./verify_subrepo_contents.sh [-a] [-p PATTERN] [SUBREPO_NAME]"
	info " -a: All subrepos. Must omit -p and SUBREPO_NAME."
	info " -p: Verify subrepos matching a regex pattern. Must omit -a and SUBREPO_NAME."
}

info() {
	MESSAGE=$1

	echo "${MESSAGE}"
}

warn() {
	MESSAGE=$1

	echo "${MESSAGE}" >&2
}

#
# Verify environment.
#

if [[ "${PWD}" != *"/tools/subrepo" ]]
then
	error "This script can only be run from within the tools/subrepo directory."
fi

if [[ "${PWD}" != *"liferay-portal/tools/subrepo" ]]
then
	error "This script can only be run from within the liferay-portal repository."
fi

REPO_PATH="../../"

git -C "${REPO_PATH}" fetch -fq upstream "7.0.x:refs/remotes/upstream/7.0.x"
git -C "${REPO_PATH}" fetch -fq upstream "master:refs/remotes/upstream/master"

get_module_reference 7.0.x

SEVEN_0_X_MODULE_REFERENCE="${MODULE_REFERENCE}"

get_module_reference master

MASTER_MODULE_REFERENCE="${MODULE_REFERENCE}"

MASTER_MODULE_LIST="$(echo "${MASTER_MODULE_REFERENCE}" | sed 's/^[a-f0-9]* *//')"

SEVEN_0_X_MODULE_LIST="$(echo "${SEVEN_0_X_MODULE_REFERENCE}" | sed 's/^[a-f0-9]* *//')"

MODULE_MISSING_DIFF="$(diff -u <(echo "${MASTER_MODULE_LIST}") <(echo "${SEVEN_0_X_MODULE_LIST}"))"

echo "Modules in master that are missing in 7.0.x: $(echo "${MODULE_MISSING_DIFF}" | grep '^-[^-]' | sed 's/^-//' | wc -l)"
echo "Modules in 7.0.x that are missing in master: $(echo "${MODULE_MISSING_DIFF}" | grep '^+[^+]' | sed 's/^+//' | wc -l)"
echo "Modules that are difference between master and 7.0.x: $(diff -u <(echo "${MASTER_MODULE_REFERENCE}") <(echo "${SEVEN_0_X_MODULE_REFERENCE}") | grep -e '^+[^+]' -e '^-[^-]' | sed 's/^[+-]//' | sed 's/^[a-f0-9]* *//' | sort -u | wc -l)"