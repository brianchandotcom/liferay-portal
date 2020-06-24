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

OPTION_ALL=
PATTERN=

while getopts "ap:" OPT
do
	case ${OPT} in
	a)
		OPTION_ALL=true
		;;
	p)
		PATTERN="${OPTARG}"
		;;
	*)
		help

		exit 1
		;;
	esac
done

shift $((OPTIND-1))

SUBREPO_NAME=$1

if [[ "$2" ]]
then
	help

	exit 1
fi

if [[ -z "${PATTERN}" ]] && [[ -z "${OPTION_ALL}" ]] && [[ -z "${SUBREPO_NAME}" ]]
then
	help

	exit 1
elif [[ "${SUBREPO_NAME}" ]] && [[ "${PATTERN}" || "${OPTION_ALL}" ]]
then
	error "Bad usage. Specifiying SUBREPO_NAME cannot be used together with -a or -p."
elif [[ "${PATTERN}" ]] && [[ "${OPTION_ALL}" ]]
then
	error "Bad usage. The -a option cannot be combined with -p."
fi

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

if [[ ! -e ../../../liferay-portal-ee ]]
then
	error "liferay-portal-ee does not exist at ../../../liferay-portal-ee"
fi

if [[ "$(uname)" == "Darwin" ]]
then
	MD5=md5
else
	MD5=md5sum
fi

#
# Get subrepos and branches.
# BRANCH_NAME:REPO_PATH:SUBREPOS_PATH
#

SUBREPO_SEARCH_PARAMETERS=(
	"7.0.x:../../../liferay-portal-ee:modules"
	"7.0.x-private:../../../liferay-portal-ee:modules/dxp"
	"7.1.x:../../../liferay-portal-ee:modules"
	"7.1.x-private:../../../liferay-portal-ee:modules/dxp"
	"master-private:../../../liferay-portal-ee:modules/dxp"
	"master:../..:modules"
)

for SUBREPO_SEARCH_PARAMETER in "${SUBREPO_SEARCH_PARAMETERS[@]}"
do
	BRANCH_NAME="${SUBREPO_SEARCH_PARAMETER%%:*}"
	REPO_PATH="$(echo "${SUBREPO_SEARCH_PARAMETER}" | sed 's/:[^:]*$//' | sed 's/.*://')"

	git -C "${REPO_PATH}" fetch -fq upstream "${BRANCH_NAME}:refs/remotes/upstream/${BRANCH_NAME}"
done

if [[ "${SUBREPO_NAME}" ]]
then
	GITREPO_BRANCHES=()

	for SUBREPO_SEARCH_PARAMETER in "${SUBREPO_SEARCH_PARAMETERS[@]}"
	do
		BRANCH_NAME="${SUBREPO_SEARCH_PARAMETER%%:*}"
		REPO_PATH="$(echo "${SUBREPO_SEARCH_PARAMETER}" | sed 's/:[^:]*$//' | sed 's/.*://')"
		SUBREPOS_PATH="${SUBREPO_SEARCH_PARAMETER##*:}"

		GITREPO_SEARCH="$(git -C "${REPO_PATH}" grep "/${SUBREPO_NAME}.git" "refs/remotes/upstream/${BRANCH_NAME}" -- '*.gitrepo' | grep ":${SUBREPOS_PATH}/" | tr '\t' ' ' | sed 's@: .*/@:@' | sed 's/\.git$//' | sed 's@refs/remotes/upstream/@@')"

		if [[ -z "${GITREPO_SEARCH}" ]]
		then
			continue
		fi

		GITREPO_BRANCHES=("${GITREPO_BRANCHES[@]}" "${GITREPO_SEARCH}")
	done

	if [[ -z "$(echo "${GITREPO_BRANCHES[@]}" | grep '[a-zA-Z]')" ]]
	then
		error "Failed to find a valid .gitrepo file for ${SUBREPO_NAME}."
	fi
else
	ALL_SUBREPOS=()

	for SUBREPO_SEARCH_PARAMETER in "${SUBREPO_SEARCH_PARAMETERS[@]}"
	do
		BRANCH_NAME="${SUBREPO_SEARCH_PARAMETER%%:*}"
		REPO_PATH="$(echo "${SUBREPO_SEARCH_PARAMETER}" | sed 's/:[^:]*$//' | sed 's/.*://')"
		SUBREPOS_PATH="${SUBREPO_SEARCH_PARAMETER##*:}"

		SUBREPO_SEARCH=($(git -C "${REPO_PATH}" grep -l 'mode.*=.*pull' "refs/remotes/upstream/${BRANCH_NAME}" -- '*.gitrepo' | grep ":${SUBREPOS_PATH}/" | sed 's/.*://' | xargs git -C "${REPO_PATH}" grep 'git@github.com' "refs/remotes/upstream/${BRANCH_NAME}" -- | tr '\t' ' ' | sed 's@: .*/@:@' | sed 's/\.git$//' | sed 's@refs/remotes/upstream/@@'))

		ALL_SUBREPOS=("${ALL_SUBREPOS[@]}" "${SUBREPO_SEARCH[@]}")
	done

	GITREPO_BRANCHES=($(printf '%s\n' "${ALL_SUBREPOS[@]}" | grep "^[^:]*:.*${PATTERN}"))

	if [[ -z "$(echo "${GITREPO_BRANCHES[@]}" | grep '[a-zA-Z]')" ]]
	then
		if [[ "${PATTERN}" ]]
		then
			error "No pull-mode subrepos found matching the specified filter."
		else
			error "No pull-mode subrepos found."
		fi
	fi
fi

TOTAL_SUBREPOS="$(printf '%s\n' "${GITREPO_BRANCHES[@]}" | sed 's/.*://' | sort -u | wc -l | sed 's/ //g')"
TOTAL_BRANCHES="$(printf '%s\n' "${GITREPO_BRANCHES[@]}" | wc -l | sed 's/ //g')"

info "Verifying ${TOTAL_BRANCHES} branches across ${TOTAL_SUBREPOS} subrepos..."
info

BRANCH_COUNTER=0

for GITREPO_BRANCH in "${GITREPO_BRANCHES[@]}"
do
	SUBREPO_BRANCH="${GITREPO_BRANCH%%:*}"
	GITREPO_PATH="$(echo "${GITREPO_BRANCH}" | sed 's/:[^:]*$//' | sed 's/.*://')"
	SUBREPO_NAME="${GITREPO_BRANCH##*:}"

	SUBREPO_PATH="../../../${SUBREPO_NAME}"

	if [[ "${SUBREPO_BRANCH}" == "master" ]]
	then
		CENTRAL_PATH="../.."
	else
		CENTRAL_PATH="../../../liferay-portal-ee"
	fi

	if [[ ! -e "${SUBREPO_PATH}" ]]
	then
		(
			cd ../../..

			git clone "git@github.com:liferay/${SUBREPO_NAME}.git"
		)
	fi

	SUBREPO_COMMIT="$(git -C "${CENTRAL_PATH}" grep 'commit = ' "refs/remotes/upstream/${SUBREPO_BRANCH}" -- "${GITREPO_PATH}" | sed 's/.* //')"

	if [[ -z "${SUBREPO_COMMIT}" ]]
	then
		warn "Skipping ${SUBREPO_NAME}:${SUBREPO_BRANCH}, failed to retrieve subrepo.commit sha at ${GITREPO_PATH}."

		let BRANCH_COUNTER++

		continue
	elif [[ "${SUBREPO_COMMIT}" == "0000000000000000000000000000000000000000" ]]
	then
		continue
	fi

	CENTRAL_TREE=$(git -C "${CENTRAL_PATH}" ls-tree --full-tree -r "refs/remotes/upstream/${SUBREPO_BRANCH}" "${GITREPO_PATH%/.gitrepo}" | sed "s@${GITREPO_PATH%/.gitrepo}/@@" | grep -v '.gitrepo' | sort -k 4)

	if [[ -z $(git -C "${SUBREPO_PATH}" ls-remote "git@github.com:liferay/${SUBREPO_NAME}.git" 2>/dev/null | grep "refs/heads/${SUBREPO_BRANCH}\$") ]]
	then
		warn "Skipping ${SUBREPO_NAME}:${SUBREPO_BRANCH}, ref not found in subrepo remote."

		let BRANCH_COUNTER++

		continue
	fi

	if [[ -z $(git -C "${SUBREPO_PATH}" show "${SUBREPO_COMMIT}" 2>/dev/null) ]]
	then
		git -C "${SUBREPO_PATH}" fetch -q "git@github.com:liferay/${SUBREPO_NAME}.git" "${SUBREPO_BRANCH}"

		if [[ -z $(git -C "${SUBREPO_PATH}" show "${SUBREPO_COMMIT}" 2>/dev/null) ]]
		then
			warn "Skipping ${SUBREPO_NAME}:${SUBREPO_BRANCH}, failed to fetch sha subrepo sha ${SUBREPO_COMMIT}"

			let BRANCH_COUNTER++

			continue
		fi
	fi

	SUBREPO_TREE=$(git -C "${SUBREPO_PATH}" ls-tree --full-tree -r "${SUBREPO_COMMIT}" . | egrep -v '\sgradle.properties' | egrep -v '\sgradlew' | egrep -v '\sgradle/wrapper' | sort -k 4)

	if [[ "${CENTRAL_TREE}" != "${SUBREPO_TREE}" ]]
	then
		info "${SUBREPO_BRANCH}:${SUBREPO_NAME} and ${SUBREPO_BRANCH}:${GITREPO_PATH%/.gitrepo} out-of-sync."

		diff -u <(echo "${CENTRAL_TREE}") <(echo "${SUBREPO_TREE}")

		let BRANCH_COUNTER++
	fi
done

info

if [[ "${BRANCH_COUNTER}" != "0" ]]
then
	info "${BRANCH_COUNTER} branches are out-of-sync."
else
	info "All branches up-to-date."
fi