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
	info "Usage: ./configure_subrepos.sh [-a] [-c command1,command2...] [-p PATTERN] [SUBREPO_NAME]"
	info " -a: All subrepos. Must omit -p and SUBREPO_NAME."
	info " -c: Commands to run for each subrepo. [create,branches,list,merges,users,webhooks]"
	info " -p: Configure subrepos matching a regex pattern. Must omit -a and SUBREPO_NAME."
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

while getopts "ac:p:" OPT
do
	case ${OPT} in
	a)
		OPTION_ALL=true
		;;
	c)
		COMMANDS="$(echo ${OPTARG} | tr ',' '\n')"
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

if [[ -z "${GITHUB_API_TOKEN}" ]]
then
	GITHUB_API_TOKEN="$(git config github.token)"
fi

if [[ -z "${GITHUB_API_TOKEN}" ]]
then
	error "Your GitHub API token is not set. Set it using the GITHUB_API_TOKEN environment variable, or with 'git config github.token'."
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

if [[ "${SUBREPO_NAME}" ]]
then
	ALL_GITREPOS=()

	for SUBREPO_SEARCH_PARAMETER in "${SUBREPO_SEARCH_PARAMETERS[@]}"
	do
		BRANCH_NAME="${SUBREPO_SEARCH_PARAMETER%%:*}"
		REPO_PATH="$(echo "${SUBREPO_SEARCH_PARAMETER}" | sed 's/:[^:]*$//' | sed 's/.*://')"
		SUBREPOS_PATH="${SUBREPO_SEARCH_PARAMETER##*:}"

		GITREPO_SEARCH="$(git -C "${REPO_PATH}" grep "/${SUBREPO_NAME}.git" "${BRANCH_NAME}" -- '*.gitrepo' | grep ":${SUBREPOS_PATH}/" | sed 's@/.gitrepo:.*@/.gitrepo@' | sed "s@:@:${REPO_PATH}:@")"

		if [[ -z "${GITREPO_SEARCH}" ]]
		then
			continue
		fi

		ALL_GITREPOS=("${ALL_GITREPOS[@]}" "${GITREPO_SEARCH}")
	done

	ALL_GITREPOS=($(printf '%s\n' "${ALL_GITREPOS[@]}" | sort -u))

	if [[ -z "$(echo "${ALL_GITREPOS[@]}" | grep '[a-zA-Z]')" ]]
	then
		error "Failed to find a valid .gitrepo file for ${SUBREPO_NAME}."
	fi
else
	ALL_GITREPOS=()

	for SUBREPO_SEARCH_PARAMETER in "${SUBREPO_SEARCH_PARAMETERS[@]}"
	do
		BRANCH_NAME="${SUBREPO_SEARCH_PARAMETER%%:*}"
		REPO_PATH="$(echo "${SUBREPO_SEARCH_PARAMETER}" | sed 's/:[^:]*$//' | sed 's/.*://')"
		SUBREPOS_PATH="${SUBREPO_SEARCH_PARAMETER##*:}"

		GITREPO_SEARCH=($(git -C "${REPO_PATH}" grep -l "github.com:liferay" "${BRANCH_NAME}" -- '*.gitrepo' | grep ":${SUBREPOS_PATH}/" | sed "s@:@:${REPO_PATH}:@"))

		ALL_GITREPOS=("${ALL_GITREPOS[@]}" "${GITREPO_SEARCH[@]}")
	done

	ALL_GITREPOS=($(printf '%s\n' "${ALL_GITREPOS[@]}" | sort -u | grep "${PATTERN}"))

	if [[ -z "$(echo "${ALL_GITREPOS[@]}" | grep '[a-zA-Z]')" ]]
	then
		if [[ "${PATTERN}" ]]
		then
			error "No subrepos found matching the specified filter."
		else
			error "No subrepos found."
		fi
	fi
fi

GITREPOS=()

for GITREPO in $(printf '%s\n' "${ALL_GITREPOS[@]}" | sed 's/.*://' | sort -u)
do
	if [[ "$(printf '%s\n' "${ALL_GITREPOS[@]}" | grep "^master:[^:]*:${GITREPO}\$")" ]]
	then
		GITREPOS=("${GITREPOS[@]}" "$(printf '%s\n' "${ALL_GITREPOS[@]}" | grep "^master:[^:]*:${GITREPO}\$" | head -n 1)")
	elif [[ "$(printf '%s\n' "${ALL_GITREPOS[@]}" | grep "^master-private:[^:]*:${GITREPO}\$")" ]]
	then
		GITREPOS=("${GITREPOS[@]}" "$(printf '%s\n' "${ALL_GITREPOS[@]}" | grep "^master-private:[^:]*:${GITREPO}\$" | head -n 1)")
	elif [[ "$(printf '%s\n' "${ALL_GITREPOS[@]}" | grep "^7.0.x-private:[^:]*:${GITREPO}\$")" ]]
	then
		GITREPOS=("${GITREPOS[@]}" "$(printf '%s\n' "${ALL_GITREPOS[@]}" | grep "^7.0.x-private:[^:]*:${GITREPO}\$" | head -n 1)")
	elif [[ "$(printf '%s\n' "${ALL_GITREPOS[@]}" | grep "^7.1.x-private:[^:]*:${GITREPO}\$")" ]]
	then
		GITREPOS=("${GITREPOS[@]}" "$(printf '%s\n' "${ALL_GITREPOS[@]}" | grep "^7.1.x-private:[^:]*:${GITREPO}\$" | head -n 1)")
	else
		GITREPOS=("${GITREPOS[@]}" "$(printf '%s\n' "${ALL_GITREPOS[@]}" | grep ":${GITREPO}\$" | head -n 1)")
	fi
done

info "Configuring ${#GITREPOS[@]} GitHub repositories..."
info

for GITREPO in "${GITREPOS[@]}"
do
	BRANCH_NAME="${GITREPO%%:*}"
	GITREPO_PATH="${GITREPO##*:}"
	PRIVATE_MIRROR=false
	REPO_PATH="$(echo "${GITREPO}" | sed 's/:[^:]*$//' | sed 's/.*://')"

	if [[ "${BRANCH_NAME}" == *-private ]]
	then
		PRIVATE_CREATE_PARAMETERS=",\"private\": \"true\""
	else
		PRIVATE_CREATE_PARAMETERS=
	fi

	if [[ "${GITREPO_PATH}" == */.gitrepo-private ]]
	then
		PRIVATE_CREATE_PARAMETERS=",\"private\": \"true\""
		PRIVATE_MIRROR=true
	fi

	GITREPO_PATH="${GITREPO_PATH%%-private}"

	REPO_NAME="$(git -C "${REPO_PATH}" show "${BRANCH_NAME}:${GITREPO_PATH}" | grep 'git@github.com' | sed 's@.*/@@' | sed 's/\.git//')"

	if [[ -z "${REPO_NAME}" ]]
	then
		warn "Failed to get remote name from ${REPO_PATH}:${BRANCH_NAME}:${GITREPO_PATH}"

		continue
	fi

	if [[ "${PRIVATE_MIRROR}" == "true" ]]
	then
		REPO_NAME="${REPO_NAME}-private"
	fi

	if [[ "$(echo "${COMMANDS}" | grep '^list$')" ]]
	then
		info "liferay/${REPO_NAME}"
	fi

	if [[ -z "${COMMANDS}" ]] || [[ "$(echo "${COMMANDS}" | grep '^create$')" ]]
	then
		OUTPUT="$(curl -H "Authorization: token ${GITHUB_API_TOKEN}" -L -s "https://api.github.com/repos/liferay/${REPO_NAME}" -X GET 2>&1)"

		if [[ -z "$(echo "${OUTPUT}" | grep "\"${REPO_NAME}\"")" ]]
		then
			info "Creating repo at liferay/${REPO_NAME}"

			OUTPUT="$(curl -d "{\"name\": \"${REPO_NAME}\"${PRIVATE_CREATE_PARAMETERS}}" -H "Authorization: token ${GITHUB_API_TOKEN}" -L -s "https://api.github.com/orgs/liferay/repos" -X POST 2>&1)"

			if [[ -z "$(echo "${OUTPUT}" | grep full_name)" ]]
			then
				warn "Failed to create repo at liferay/${REPO_NAME}."

				warn "${OUTPUT}"

				continue
			fi
		fi
	fi

	if [[ -z "${COMMANDS}" ]] || [[ "$(echo "${COMMANDS}" | grep '^branches$')" ]]
	then
		OUTPUT="$(curl -H "Accept: application/vnd.github.v3+json" -H "Authorization: token ${GITHUB_API_TOKEN}" -L -s "https://api.github.com/repos/liferay/${REPO_NAME}/branches" -X GET 2>&1)"

		if [[ -z "$(echo "${OUTPUT}" | grep '\[')" ]] || [[ -z "$(echo "${OUTPUT}" | grep '\]')" ]]
		then
			warn "Failed to get branches at liferay/${REPO_NAME}."

			warn "${OUTPUT}"

			continue
		fi

		if [[ "$(echo "${OUTPUT}" | grep '"name"')" ]]
		then
			PROTECTED_JSON="$(curl -H "Accept: application/vnd.github.v3+json" -H "Authorization: token ${GITHUB_API_TOKEN}" -L -s "https://api.github.com/repos/liferay/${REPO_NAME}/branches?protected=true" -X GET 2>&1)"

			if [[ -z "$(echo "${PROTECTED_JSON}" | grep '\[')" ]] || [[ -z "$(echo "${PROTECTED_JSON}" | grep '\]')" ]]
			then
				warn "Failed to get protected branches at liferay/${REPO_NAME}."

				warn "${PROTECTED_JSON}"

				continue
			fi

			PROTECTED_BRANCHES="
7.0.x
7.0.x-private
7.1.x
7.1.x-private
master
master-private
"

			BRANCHES=()

			for BRANCH_JSON in $(echo "${OUTPUT}" | tr '\n' ' ' | sed 's/ //g' | sed 's/"name"/\'$'\n&/g' | grep '"name"')
			do
				BRANCH="$(echo "${BRANCH_JSON}" | sed 's/.*"name":"//' | sed 's/".*//')"

				BRANCHES=("${BRANCHES[@]}" "${BRANCH}")

				if [[ "$(echo "${PROTECTED_BRANCHES}" | grep "^${BRANCH}\$")" ]] && [[ -z "$(echo "${PROTECTED_JSON}" | grep "\"name\":.*\"${BRANCH}\"")" ]]
				then
					info "Protecting branch ${BRANCH} at liferay/${REPO_NAME}."

					OUTPUT="$(curl -d "{\"enforce_admins\":false,\"required_pull_request_reviews\":null,\"required_status_checks\":null,\"restrictions\":null}" -H "Accept: application/vnd.github.v3+json" -H "Authorization: token ${GITHUB_API_TOKEN}" -L -s "https://api.github.com/repos/liferay/${REPO_NAME}/branches/${BRANCH}/protection" -X PUT 2>&1)"

					if [[ -z "$(echo "${OUTPUT}" | grep '"url"')" ]]
					then
						warn "Failed to protect branch ${BRANCH} at liferay/${REPO_NAME}."

						warn "${OUTPUT}"
					fi
				fi
			done

			OUTPUT="$(curl -H "Authorization: token ${GITHUB_API_TOKEN}" -L -s "https://api.github.com/repos/liferay/${REPO_NAME}" -X GET 2>&1)"

			if [[ -z "$(echo "${OUTPUT}" | grep '"default_branch"')" ]]
			then
				warn "Failed to get default_branch at liferay/${REPO_NAME}."

				warn "${OUTPUT}"

				continue
			fi

			CURRENT_DEFAULT_BRANCH="$(echo "${OUTPUT}" | grep '"default_branch"' | sed 's/"[^"]*$//' | sed 's/.*"//')"

			if [[ -z "${CURRENT_DEFAULT_BRANCH}" ]]
			then
				warn "Failed to parse default branch at liferay/${REPO_NAME}."

				continue
			fi

			CORRECT_DEFAULT_BRANCH=

			if [[ "$(printf '%s\n' "${BRANCHES[@]}" | grep '^master-private$')" ]]
			then
				CORRECT_DEFAULT_BRANCH=master-private
			elif [[ "$(printf '%s\n' "${BRANCHES[@]}" | grep '^7.0.x-private$')" ]]
			then
				CORRECT_DEFAULT_BRANCH=7.0.x-private
			elif [[ "$(printf '%s\n' "${BRANCHES[@]}" | grep '^7.1.x-private$')" ]]
			then
				CORRECT_DEFAULT_BRANCH=7.1.x-private
			elif [[ "$(printf '%s\n' "${BRANCHES[@]}" | grep '^master$')" ]]
			then
				CORRECT_DEFAULT_BRANCH=master
			fi

			if [[ "${CORRECT_DEFAULT_BRANCH}" ]] && [[ "${CURRENT_DEFAULT_BRANCH}" != "${CORRECT_DEFAULT_BRANCH}" ]]
			then
				info "Configuring default branch at liferay/${REPO_NAME}."

				OUTPUT="$(curl -d "{\"name\": \"${REPO_NAME}\",\"default_branch\": \"${CORRECT_DEFAULT_BRANCH}\"}" -H "Authorization: token ${GITHUB_API_TOKEN}" -L -s "https://api.github.com/repos/liferay/${REPO_NAME}" -X PATCH 2>&1)"

				if [[ -z "$(echo "${OUTPUT}" | grep "\"default_branch\":.*\"${CORRECT_DEFAULT_BRANCH}\"")" ]]
				then
					warn "Failed to change default branch to ${CORRECT_DEFAULT_BRANCH} at liferay/${REPO_NAME}."

					warn "${OUTPUT}"
				fi
			fi
		fi
	fi

	if [[ -z "${COMMANDS}" ]] || [[ "$(echo "${COMMANDS}" | grep '^merges$')" ]]
	then
		if [[ "$(git -C "${REPO_PATH}" show "${BRANCH_NAME}:${GITREPO_PATH}" | grep 'mergebuttonmergecommits.*true')" ]]
		then
			MERGES_PAYLOAD="{\"name\":\"${REPO_NAME}\",\"allow_merge_commit\":true,\"allow_rebase_merge\":true,\"allow_squash_merge\":false}"
		else
			MERGES_PAYLOAD="{\"name\":\"${REPO_NAME}\",\"allow_merge_commit\":false,\"allow_rebase_merge\":true,\"allow_squash_merge\":false}"
		fi

		OUTPUT="$(curl -d "${MERGES_PAYLOAD}" -H "Authorization: token ${GITHUB_API_TOKEN}" -L -s "https://api.github.com/repos/liferay/${REPO_NAME}" -X PATCH 2>&1)"

		if [[ -z "$(echo "${OUTPUT}" | grep "\"name\":.*\"${REPO_NAME}\"")" ]]
		then
			warn "Failed to configure merging at liferay/${REPO_NAME}."

			warn "${OUTPUT}"

			continue
		fi
	fi

	if [[ -z "${COMMANDS}" ]] || [[ "$(echo "${COMMANDS}" | grep '^users$')" ]]
	then
		curl -d "{\"permission\": \"push\"}" "https://api.github.com/repos/liferay/${REPO_NAME}/collaborators/liferay-continuous-integration" -H "Authorization: token ${GITHUB_API_TOKEN}" -s -X PUT

		if [[ "${REPO_NAME}" == *-private ]]
		then
			curl -d "{\"permission\": \"pull\"}" "https://api.github.com/teams/65863/repos/liferay/${REPO_NAME}" -H "Authorization: token ${GITHUB_API_TOKEN}" -s -X PUT
		fi
	fi

	if [[ -z "${COMMANDS}" ]] || [[ "$(echo "${COMMANDS}" | grep '^webhooks$')" ]]
	then
		OUTPUT="$(curl -H "Authorization: token ${GITHUB_API_TOKEN}" -L -s "https://api.github.com/repos/liferay/${REPO_NAME}/hooks" -X GET 2>&1)"

		if [[ -z "$(echo "${OUTPUT}" | grep '\[')" ]] || [[ -z "$(echo "${OUTPUT}" | grep '\]')" ]]
		then
			warn "Failed to get webhooks at liferay/${REPO_NAME}."

			warn "${OUTPUT}"

			continue
		fi

		if [[ -z "$(echo "${OUTPUT}" | grep "\"url\":.*\"http://webhook.liferay.com\"")" ]]
		then
			info "Creating webhook at liferay/${REPO_NAME}."

			OUTPUT="$(curl -d "{\"active\":true,\"config\":{\"url\":\"http://webhook.liferay.com\",\"content_type\":\"json\"},\"events\":[\"*\"],\"name\":\"web\"}" -H "Authorization: token ${GITHUB_API_TOKEN}" -L -s "https://api.github.com/repos/liferay/${REPO_NAME}/hooks" -X POST 2>&1)"

			if [[ -z "$(echo "${OUTPUT}" | grep "\"url\":.*\"http://webhook.liferay.com\"")" ]]
			then
				warn "Error creating webhook at liferay/${REPO_NAME}."

				warn "${OUTPUT}"

				continue
			fi
		fi
	fi
done