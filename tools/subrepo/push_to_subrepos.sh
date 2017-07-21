#!/bin/bash
#
# Author: Samuel Trong Tran <samuel.tran@liferay.com>
# Platform: Linux/Unix
#

set -o pipefail

get_content_var() {
	FILE=$1

	echo "${FILE}" | sed 's@[-./]@_@g'
}

get_remaining_rate() {
	curl --header "Authorization: token ${GITHUB_API_TOKEN}" -s https://api.github.com/rate_limit | tr '\n' ' ' | sed 's/ //g' | sed 's/},/\'$'\n/g' | grep '"rate"' | tr ',' '\n' | grep '"remaining"' | sed 's/.*://' | sed 's/[^0-9]//g' 2>&1
}

error() {
	MESSAGE=$1

	warn "error: ${MESSAGE}"

	exit 1
}

help() {
	info "Usage: ./push_to_subrepos.sh [-a] [-d] [-f] [-p PATTERN] [-v] [SUBREPO_NAME]"
	info " -a: All subrepos. Must omit -p and SUBREPO_NAME."
	info " -d: Debug mode. More verbose console logging."
	info " -f: Force update a subrepo that is either lacking gitrepo information or is not configured for pushing."
	info " -p: Update subrepos matching a regex pattern. Must omit -a and SUBREPO_NAME."
	info " -v: Verify only. Lists all subrepos/branches/files out of date."
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
OPTION_DEBUG=
OPTION_FORCE=
OPTION_VERIFY=
PATTERN=

while getopts "adfp:v" OPT
do
	case ${OPT} in
	a)
		OPTION_ALL=true
		;;
	d)
		OPTION_DEBUG=true
		;;
	f)
		OPTION_FORCE=true
		;;
	p)
		PATTERN="${OPTARG}"
		;;
	v)
		OPTION_VERIFY=true
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
elif [[ "${OPTION_FORCE}" ]] && [[ "${OPTION_ALL}" || "${PATTERN}" ]]
then
	error "Bad usage. The -f option can only be used with SUBREPO_NAME."
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

if [[ "$(git rev-parse --abbrev-ref HEAD)" != "master" ]]
then
	error "This script can only be run from the master branch."
fi

if [[ ! -e ../../../liferay-portal-ee ]]
then
	error "liferay-portal-ee does not exist at ../../../liferay-portal-ee"
fi

git update-index -q --ignore-submodules --refresh

git diff-files --quiet --ignore-submodules -- || error "Unstaged changes."

git diff-index --cached --quiet HEAD -- || error "Uncommitted changes."

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
	"7.0.x:../..:modules/apps"
	"ee-7.0.x:../../../liferay-portal-ee:modules/private/apps"
	"master:../..:modules/apps"
)

if [[ "${SUBREPO_NAME}" ]]
then
	if [[ "${OPTION_FORCE}" ]]
	then
		VALID_BRANCHES="
7.0.x
master
"

		if [[ "${SUBREPO_NAME}" == *-private ]]
		then
			VALID_BRANCHES=$(echo "${VALID_BRANCHES}" | sed 's/[a-z0-9]$/&-private/')
		fi

		OUTPUT=$(curl --header "Authorization: token ${GITHUB_API_TOKEN}" -s "https://api.github.com/repos/liferay/${SUBREPO_NAME}/branches" -X GET 2>&1)

		if [[ "$?" != "0" ]] || [[ "$(echo "${OUTPUT}" | grep -i '\"not found\"')" ]]
		then
			warn "${OUTPUT}"

			error "Failed to list the remote branches at liferay/${SUBREPO_NAME} via the GitHub API."
		fi

		REMOTE_BRANCHES=($(echo "${OUTPUT}" | grep '"name"' | sed 's/"[^"]*$//' | sed 's/.*"//'))

		if [[ -z "$(echo "${REMOTE_BRANCHES[@]}" | grep '[a-zA-Z]')" ]]
		then
			error "No branches found on GitHub at liferay/${SUBREPO_NAME}."
		fi

		SUBREPO_BRANCHES=()

		for REMOTE_BRANCH in "${REMOTE_BRANCHES[@]}"
		do
			if [[ "$(echo "${VALID_BRANCHES}" | grep "^${REMOTE_BRANCH}\$")" ]]
			then
				SUBREPO_BRANCHES=("${SUBREPO_BRANCHES[@]}" "${REMOTE_BRANCH}:${SUBREPO_NAME}")
			fi
		done

		if [[ -z "$(echo "${SUBREPO_BRANCHES[@]}" | grep '[a-zA-Z]')" ]]
		then
			error "No valid branches found on GitHub at liferay/${SUBREPO_NAME}."
		fi
	else
		SUBREPO_BRANCHES=()

		for SUBREPO_SEARCH_PARAMETER in "${SUBREPO_SEARCH_PARAMETERS[@]}"
		do
			BRANCH_NAME="${SUBREPO_SEARCH_PARAMETER%%:*}"
			REPO_PATH="$(echo "${SUBREPO_SEARCH_PARAMETER}" | sed 's/:[^:]*$//' | sed 's/.*://')"
			SUBREPOS_PATH="${SUBREPO_SEARCH_PARAMETER##*:}"

			GITREPO_PATH="$(git -C "${REPO_PATH}" grep -l "/${SUBREPO_NAME}.git" "${BRANCH_NAME}" -- '*.gitrepo' | grep ":${SUBREPOS_PATH}/" | sed 's/.*://')"

			if [[ -z "${GITREPO_PATH}" ]]
			then
				continue
			fi

			if [[ "$(git -C "${REPO_PATH}" grep 'mode.*=.*pull' "${BRANCH_NAME}" -- "${GITREPO_PATH##*:}")" ]]
			then
				SUBREPO_BRANCHES=("${SUBREPO_BRANCHES[@]}" "$(git -C "${REPO_PATH}" grep 'git@github.com'  "${BRANCH_NAME}" -- "${GITREPO_PATH##*:}" | sed 's@:.*/@:@' | sed 's/\.git//')")
			fi
		done

		if [[ -z "$(echo "${SUBREPO_BRANCHES[@]}" | grep '[a-zA-Z]')" ]]
		then
			error "Failed to find a valid .gitrepo file and validate mode for ${SUBREPO_NAME}. Use the -f flag to force update the subrepo."
		fi
	fi
else
	ALL_SUBREPOS=()

	for SUBREPO_SEARCH_PARAMETER in "${SUBREPO_SEARCH_PARAMETERS[@]}"
	do
		BRANCH_NAME="${SUBREPO_SEARCH_PARAMETER%%:*}"
		REPO_PATH="$(echo "${SUBREPO_SEARCH_PARAMETER}" | sed 's/:[^:]*$//' | sed 's/.*://')"
		SUBREPOS_PATH="${SUBREPO_SEARCH_PARAMETER##*:}"

		SUBREPO_SEARCH=($(git -C "${REPO_PATH}" grep -l 'mode.*=.*pull' "${BRANCH_NAME}" -- '*.gitrepo' | grep ":${SUBREPOS_PATH}/" | sed 's/.*://' | xargs git -C "${REPO_PATH}" grep 'git@github.com' "${BRANCH_NAME}" -- | sed 's@:.*/@:@' | sed 's/\.git//'))

		ALL_SUBREPOS=("${ALL_SUBREPOS[@]}" "${SUBREPO_SEARCH[@]}")
	done

	#
	# Fix for ee-7.0.x.
	#

	ALL_SUBREPOS=($(printf '%s\n' "${ALL_SUBREPOS[@]}" | sed 's/^ee-7.0.x/7.0.x-private/'))

	SUBREPO_BRANCHES=($(printf '%s\n' "${ALL_SUBREPOS[@]}" | grep "^[^:]*:.*${PATTERN}"))

	if [[ -z "$(echo "${SUBREPO_BRANCHES[@]}" | grep '[a-zA-Z]')" ]]
	then
		if [[ "${PATTERN}" ]]
		then
			error "No pull-mode subrepos found matching the specified filter."
		else
			error "No pull-mode subrepos found."
		fi
	fi
fi

#
# Parse gradle file info.
#

GRADLE_FILES=(
	"gradle/wrapper/gradle-wrapper.jar"
	"gradle/wrapper/gradle-wrapper.properties"
	"gradlew"
	"gradlew.bat"
)

for GRADLE_FILE in "${GRADLE_FILES[@]}"
do
	if [[ ! -e "${GRADLE_FILE}" ]]
	then
		error "${GRADLE_FILE} does not exist."
	fi

	CONTENT="$(cat "${GRADLE_FILE}" | base64 -b 60 | awk '{printf "%s\\n", $0}')"

	if [[ "$?" != "0" ]]
	then
		error "Failed to parse the sha for ${GRADLE_FILE}"
	fi

	CONTENT_VAR="$(get_content_var "${GRADLE_FILE}")"

	declare $CONTENT_VAR="${CONTENT}"
done

LOCAL_SHAS="$(
	for FILE in "${GRADLE_FILES[@]}"
	do
		SHA="$(git hash-object "${FILE}")"

		if [[ -z "${SHA}" ]]
		then
			error "Unable to parse sha for the file '${FILE}'."
		fi

		echo "${FILE}:${SHA}"
	done
)"

#
# Verify rate limit.
#

REMAINING_RATE="$(get_remaining_rate)"

if [[ "${REMAINING_RATE}" ]]
then
	info "Remaining GitHub rate limit: ${REMAINING_RATE}"
fi

TOTAL_SUBREPOS="$(printf '%s\n' "${SUBREPO_BRANCHES[@]}" | sed 's/.*://' | sort -u | wc -l | sed 's/ //g')"
TOTAL_BRANCHES="$(printf '%s\n' "${SUBREPO_BRANCHES[@]}" | wc -l | sed 's/ //g')"

if [[ "${OPTION_VERIFY}" ]]
then
	info "Verifying ${TOTAL_BRANCHES} branches across ${TOTAL_SUBREPOS} subrepos..."
else
	info "Updating ${TOTAL_BRANCHES} branches across ${TOTAL_SUBREPOS} subrepos..."
fi


MINIMUM_API_CALLS_PER_BRANCH=3
MAXIMUM_API_CALLS_PER_BRANCH=7

if [[ "${REMAINING_RATE}" ]]
then
	MINIMUM_RATE=$((MINIMUM_API_CALLS_PER_BRANCH*TOTAL_BRANCHES))
	MAXIMUM_RATE=$((MAXIMUM_API_CALLS_PER_BRANCH*TOTAL_BRANCHES))

	if ((REMAINING_RATE < MINIMUM_RATE))
	then
		error "Your current rate limit is insufficient, a minimum of ${MINIMUM_RATE} API calls will be made. Try again in an hour when your rate limit resets."
	fi

	if ((REMAINING_RATE < MAXIMUM_RATE))
	then
		warn "Warning: your current rate limit may be insufficient, up to ${MAXIMUM_RATE} API calls may be made."
	fi
fi

info

TOTAL_FILES_COUNTER=0

for SUBREPO_BRANCH in "${SUBREPO_BRANCHES[@]}"
do
	BRANCH="${SUBREPO_BRANCH%%:*}"
	SUBREPO="${SUBREPO_BRANCH##*:}"

	#
	# Retrieve the shas for the files in gradle/wrapper. Also check for any
	# errors interacting with the repo via the API.
	#

	GRADLE_WRAPPER_JSON=

	for i in {1..2}
	do
		GRADLE_WRAPPER_JSON=$(curl --header "Authorization: token ${GITHUB_API_TOKEN}" -s "https://api.github.com/repos/liferay/${SUBREPO}/contents/gradle/wrapper?ref=${BRANCH}" -X GET | tr '\n' ' ' | sed 's/ //g' | sed 's/},/\'$'\n/g')

		if [[ $(echo "${GRADLE_WRAPPER_JSON}" | grep '\"sha\"') ]]
		then
			break;
		fi
	done

	if [[ -z $(echo "${GRADLE_WRAPPER_JSON}" | grep '\"sha\"') ]] && [[ -z $(echo "${GRADLE_WRAPPER_JSON}" | grep -i '\"notfound\"') ]]
	then
		warn "Skipping liferay/${SUBREPO}:${BRANCH}."
		warn ".. Failed to get contents of the gradle/wrapper directory via the GitHub API."

		continue
	fi

	GRADLE_WRAPPER_JAR_REMOTE_SHA="$(echo "${GRADLE_WRAPPER_JSON}" | grep '"gradle-wrapper.jar"' | sed 's/",/&\'$'\n/g' | grep '"sha"' | sed 's/"[^"]*$//' | sed 's/.*"//')"
	GRADLE_WRAPPER_PROPERTIES_REMOTE_SHA="$(echo "${GRADLE_WRAPPER_JSON}" | grep '"gradle-wrapper.properties"' | sed 's/",/&\'$'\n/g' | grep '"sha"' | sed 's/"[^"]*$//' | sed 's/.*"//')"

	#
	# Retrieve the SHAs for gradlew and gradlew.bat. Also check the file mode
	# for gradlew.
	#

	for i in {1..2}
	do
		BRANCH_JSON="$(curl -s "https://api.github.com/repos/liferay/${SUBREPO}/branches/${BRANCH}" -X GET --header "Authorization: token ${GITHUB_API_TOKEN}")"

		if [[ "$(echo "${BRANCH_JSON}" | grep '"sha"')" ]]
		then
			break;
		fi
	done

	if [[ -z "$(echo "${BRANCH_JSON}" | grep '"sha"')" ]]
	then
		error "Failed to retrieve the branch information for ${BRANCH} via the GitHub API."
	fi

	CURRENT_TREE_URL="$(echo "${BRANCH_JSON}" | grep '/git/trees/' | sed 's/"[^"]*$//' | sed 's/.*"//')"

	CURRENT_TREE_SHA="${CURRENT_TREE_URL##*/}"

	CURRENT_BRANCH_SHA="$(echo "${BRANCH_JSON}" | tr '\n' ' ' | sed 's/\[.*\]//' | sed 's/ //g' | sed 's/"tree":{[^}]*}//' | tr ',' '\n' | grep '"sha"' | head -n 1 | sed 's/"[^"]*$//' | sed 's/.*"//')"

	for i in {1..2}
	do
		TREE_CONTENT="$(curl -s "${CURRENT_TREE_URL}" -X GET --header "Authorization: token ${GITHUB_API_TOKEN}" | tr '\n' ' ' | sed 's/ //g' | sed 's/},/\'$'\n/g')"

		if [[ "$(echo "${TREE_CONTENT}" | grep "\"${CURRENT_TREE_URL}\"")" ]]
		then
			break;
		fi
	done

	if [[ -z "$(echo "${TREE_CONTENT}" | grep "\"${CURRENT_TREE_URL}\"")" ]]
	then
		error "Failed to retrieve the tree content for ${BRANCH} via the GitHub API."
	fi

	GRADLEW_BAT_REMOTE_SHA="$(echo "${TREE_CONTENT}" | grep '"gradlew.bat"' | sed 's/.*"sha":"//' | sed 's/".*//')"
	GRADLEW_FILE_MODE="$(echo "${TREE_CONTENT}" | grep '"gradlew"' | sed 's/.*"mode":"//' | sed 's/".*//')"
	GRADLEW_REMOTE_SHA="$(echo "${TREE_CONTENT}" | grep '"gradlew"' | sed 's/.*"sha":"//' | sed 's/".*//')"

	REMOTE_SHAS="
gradle/wrapper/gradle-wrapper.jar:${GRADLE_WRAPPER_JAR_REMOTE_SHA}
gradle/wrapper/gradle-wrapper.properties:${GRADLE_WRAPPER_PROPERTIES_REMOTE_SHA}
gradlew:${GRADLEW_REMOTE_SHA}
gradlew.bat:${GRADLEW_BAT_REMOTE_SHA}
"

	GRADLEW_UPDATE=

	for GRADLE_FILE in "${GRADLE_FILES[@]}"
	do
		LOCAL_SHA="$(echo "${LOCAL_SHAS}" | grep "^${GRADLE_FILE}:" | sed 's/.*://')"
		REMOTE_SHA="$(echo "${REMOTE_SHAS}" | grep "^${GRADLE_FILE}:" | sed 's/.*://')"

		if [[ "${LOCAL_SHA}" != "${REMOTE_SHA}" ]]
		then
			if [[ "${OPTION_VERIFY}" ]]
			then
				info "liferay/${SUBREPO}:${BRANCH}:${GRADLE_FILE} content"
			else
				COMMIT_MESSAGE="LPS-0 Update ${GRADLE_FILE} content"
				CONTENT_VAR="$(get_content_var "${GRADLE_FILE}")"

				DATA="\"branch\":\"${BRANCH}\",\"content\":\"${!CONTENT_VAR}\",\"message\":\"${COMMIT_MESSAGE}\",\"path\": \"${GRADLE_FILE}\""

				if [[ "${REMOTE_SHA}" ]]
				then
					DATA="${DATA},\"sha\": \"${REMOTE_SHA}\""
				fi

				for i in {1..2}
				do
					OUTPUT=$(curl -d "{${DATA}}" --header "Authorization: token ${GITHUB_API_TOKEN}" -s "https://api.github.com/repos/liferay/${SUBREPO}/contents/${GRADLE_FILE}" -X PUT 2>&1)

					if [[ "$(echo ${OUTPUT} | grep "message.*${COMMIT_MESSAGE}")" ]]
					then
						break
					fi
				done

				if [[ -z "$(echo ${OUTPUT} | grep "message.*${COMMIT_MESSAGE}")" ]]
				then
					warn "${OUTPUT}"

					warn "Failed to update ${GRADLE_FILE} content at liferay/${SUBREPO}:${BRANCH}."

					if [[ "${GRADLE_FILE}" == "gradlew" ]]
					then
						GRADLEW_UPDATE=fail
					fi

					continue
				fi

				CURRENT_BRANCH_SHA="$(echo "${OUTPUT}" | tr '\n' ' ' | sed 's/\[.*\]//' | sed 's/ //g' | sed 's/},/\'$'\n/g' | grep '"commit"' | sed 's/.*"sha":"//' | sed 's/".*//')"
				CURRENT_TREE_SHA="$(echo "${OUTPUT}" | grep '/git/trees/' | sed 's@.*/@@' | sed 's/".*//')"
			fi

			if [[ "${GRADLE_FILE}" == "gradlew" ]]
			then
				GRADLEW_UPDATE=success
			fi

			let TOTAL_FILES_COUNTER++
		fi
	done

	if [[ "${GRADLEW_FILE_MODE}" != "100755" ]] && [[ "${GRADLEW_UPDATE}" != "fail" ]]
	then
		if [[ "${OPTION_VERIFY}" ]]
		then
			info "liferay/${SUBREPO}:${BRANCH}:gradlew file mode"
		else
			FILE_SHA="$(echo "${LOCAL_SHAS}" | grep "^gradlew:" | sed 's/.*://')"

			for i in {1..2}
			do
				OUTPUT=$(curl -d "{\"base_tree\": \"${CURRENT_TREE_SHA}\",\"tree\":[{\"mode\":\"100755\",\"path\": \"gradlew\",\"sha\":\"${FILE_SHA}\"}]}" --header "Authorization: token ${GITHUB_API_TOKEN}" -s "https://api.github.com/repos/liferay/${SUBREPO}/git/trees" -X POST)

				if [[ "$(echo ${OUTPUT} | grep '"sha"')" ]]
				then
					break
				fi
			done

			if [[ -z "$(echo ${OUTPUT} | grep '"sha"')" ]]
			then
				warn "${OUTPUT}"

				warn "Failed to update gradlew file mode (creating tree via GitHub API) at liferay/${SUBREPO}:${BRANCH}."

				continue
			fi

			NEW_TREE_SHA="$(echo "${OUTPUT}" | tr '\n' '%' | sed 's/\[.*\]//' | sed 's/ *//g' | sed 's/.*"sha":"//' | sed 's/".*//')"

			if [[ "${NEW_TREE_SHA}" == "${CURRENT_TREE_SHA}" ]]
			then
				continue
			fi

			for i in {1..2}
			do
				OUTPUT=$(curl -d "{\"message\": \"LPS-0 Update gradlew file mode\",\"tree\": \"${NEW_TREE_SHA}\",\"parents\": [\"${CURRENT_BRANCH_SHA}\"]}" --header "Authorization: token ${GITHUB_API_TOKEN}" -s "https://api.github.com/repos/liferay/${SUBREPO}/git/commits" -X POST)

				if [[ "$(echo ${OUTPUT} | grep '"sha"')" ]]
				then
					break
				fi
			done

			if [[ -z "$(echo ${OUTPUT} | grep '"sha"')" ]]
			then
				warn "${OUTPUT}"

				warn "Failed to update gradlew file mode (creating commit via GitHub API) at liferay/${SUBREPO}:${BRANCH}."

				continue
			fi

			NEW_COMMIT_SHA="$(echo "${OUTPUT}" | grep -v "${CURRENT_BRANCH_SHA}" | grep -v "${NEW_TREE_SHA}" | grep '"sha"' | sed 's/"[^"]*$//' | sed 's/.*"//')"

			for i in {1..2}
			do
				OUTPUT=$(curl -d "{\"sha\": \"${NEW_COMMIT_SHA}\"}" --header "Authorization: token ${GITHUB_API_TOKEN}" -s "https://api.github.com/repos/liferay/${SUBREPO}/git/refs/heads/${BRANCH}" -X PATCH)

				if [[ "$(echo ${OUTPUT} | grep '"ref"')" ]]
				then
					break
				fi
			done

			if [[ -z "$(echo ${OUTPUT} | grep '"ref"')" ]]
			then
				warn "${OUTPUT}"

				warn "Failed to update gradlew file mode (updating ref via GitHub API) at liferay/${SUBREPO}:${BRANCH}."

				continue
			fi
		fi

		if [[ -z "${GRADLEW_UPDATE}" ]]
		then
			let TOTAL_FILES_COUNTER++
		fi
	fi
done

info

if ((TOTAL_FILES_COUNTER > 0))
then
	if [[ "${OPTION_VERIFY}" ]]
	then
		info "${TOTAL_FILES_COUNTER} files require updating."
	else
		info "${TOTAL_FILES_COUNTER} files were successfully updated."
	fi
else
	info "All files up to date."
fi

REMAINING_RATE="$(get_remaining_rate)"

if [[ "${REMAINING_RATE}" ]]
then
	info "Remaining GitHub rate limit: ${REMAINING_RATE}"
fi