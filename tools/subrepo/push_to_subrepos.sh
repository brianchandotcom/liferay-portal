#!/bin/bash
#
# Author: Samuel Trong Tran <samuel.tran@liferay.com>
# Platform: MacOS
# Revision: 0.1.0
#

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
	warn "Usage: ./push_to_subrepos.sh [-a] [-p PATTERN] [-v] [SUBREPO_NAME]"
	warn " -a: All subrepos. Must omit -p and SUBREPO_NAME."
	warn " -p: Update subrepos matching a regex pattern. Must omit -a and SUBREPO_NAME."
	warn " -v: Verify only. Lists all subrepos/branches/files out of date."
}

info() {
	MESSAGE=$1

	echo "${MESSAGE}"
}

warn() {
	MESSAGE=$1

	echo "${MESSAGE}" >&2
}

PATTERN=
OPTION_ALL=
OPTION_VERIFY=

while getopts "ap:v" OPT
do
	case ${OPT} in
	a)
		OPTION_ALL=true
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

if [[ "$2" ]]; then
	help

	exit 1
fi

if [[ -z "${PATTERN}" ]] && [[ -z "${OPTION_ALL}" ]] && [[ -z "${SUBREPO_NAME}" ]]; then
	help

	exit 1
elif [[ "${SUBREPO_NAME}" ]] && [[ "${PATTERN}" || "${OPTION_ALL}" ]]; then
	error "Bad usage. Specifiying SUBREPO_NAME cannot be used together with -a or -p."
elif [[ "${PATTERN}" ]] && [[ "${OPTION_ALL}" ]]; then
	error "Bad usage. The -a option cannot be combined with -p."
fi

#
# Verify environment.
#

if [[ "$(uname)" != "Darwin" ]]; then
	error "This script is only compatible with MacOS systems."
fi

if [[ "${PWD}" != *"/tools/subrepo" ]]; then
	error "This script can only be run from within the tools/subrepo directory."
fi

if [[ "${PWD}" != *"liferay-portal/tools/subrepo" ]]; then
	error "This script can only be run from within the liferay-portal repository."
fi

# if [[ "$(git rev-parse --abbrev-ref HEAD)" != "master" ]]; then
# 	error "This script can only be run from the master branch."
# fi

if [[ ! -e ../../../liferay-portal-ee ]]; then
	error "liferay-portal-ee does not exist at ../../../liferay-portal-ee"
fi

# git update-index -q --ignore-submodules --refresh
#
# git diff-files --quiet --ignore-submodules -- || error "Unstaged changes."
#
# git diff-index --cached --quiet HEAD -- || error "Uncommitted changes."

if [[ -z "${GITHUB_API_TOKEN}" ]]; then
	GITHUB_API_TOKEN="$(git config github.token)"
fi

if [[ -z "${GITHUB_API_TOKEN}" ]]; then
	error "Your GitHub api token is not set. Set it using the GITHUB_API_TOKEN environment variable, or with 'git config github.token'."
fi

#
# Get subrepos and branches.
#

if [[ "${SUBREPO_NAME}" ]]; then
	VALID_BRANCHES="
7.0.x
master
"

	if [[ "${SUBREPO_NAME}" == *-private ]]; then
		VALID_BRANCHES=$(echo "${VALID_BRANCHES}" | sed 's/[a-z0-9]$/&-private/')
	fi

	OUTPUT=$(curl --header "Authorization: token ${GITHUB_API_TOKEN}" -s "https://api.github.com/repos/liferay/${SUBREPO_NAME}/branches" -X GET 2>&1)

	if [[ "$?" != "0" ]] || [[ "$(echo "${OUTPUT}" | grep -i '\"not found\"')" ]]; then
		warn "${OUTPUT}"

		error "Failed to list the remote branches at liferay/${SUBREPO_NAME} via the GitHub api."
	fi

	REMOTE_BRANCHES=($(echo "${OUTPUT}" | grep '"name"' | sed 's/"[^"]*$//' | sed 's/.*"//'))

	if [[ -z "${REMOTE_BRANCHES[@]}" ]]; then
		error "No valid branches found at liferay/${SUBREPO_NAME}."
	fi

	SUBREPO_BRANCHES=()

	for REMOTE_BRANCH in "${REMOTE_BRANCHES[@]}"; do
		if [[ "$(echo "${VALID_BRANCHES}" | grep "^${REMOTE_BRANCH}\$")" ]]; then
			SUBREPO_BRANCHES=("${SUBREPO_BRANCHES[@]}" "${REMOTE_BRANCH}:${SUBREPO_NAME}")
		fi
	done
else
	SUBREPOS_7_0_X=($(git  -C ../.. grep 'git@github.com' 7.0.x -- '*.gitrepo' | grep ':modules/apps/' | sed 's@:.*/@:@' | sed 's/\.git//'))
	SUBREPOS_7_0_X_PRIVATE=($(git -C ../../../liferay-portal-ee grep 'git@github.com' ee-7.0.x -- '*.gitrepo' | grep ':modules/private/apps/' | sed 's@:.*/@:@' | sed 's/\.git//' | sed 's/ee-7.0.x/7.0.x-private/'))
	SUBREPOS_MASTER=($(git  -C ../.. grep 'git@github.com' master -- '*.gitrepo' | grep ':modules/apps/' | sed 's@:.*/@:@' | sed 's/\.git//'))

	ALL_SUBREPOS=("${SUBREPOS_7_0_X[@]}" "${SUBREPOS_7_0_X_PRIVATE[@]}" "${SUBREPOS_MASTER[@]}")

	SUBREPO_BRANCHES=($(printf '%s\n' "${ALL_SUBREPOS[@]}" | grep "^[^:]*:.*${PATTERN}"))

	if [[ -z "${SUBREPO_BRANCHES[@]}" ]]; then
		if [[ "${PATTERN}" ]]; then
			error "No subrepos found matching the specified filter."
		else
			error "No subrepos found."
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

for GRADLE_FILE in "${GRADLE_FILES[@]}"; do
	if [[ ! -e "${GRADLE_FILE}" ]]; then
		error "${GRADLE_FILE} does not exist."
	fi

	CONTENT_VAR="$(get_content_var "${GRADLE_FILE}")"

	declare $CONTENT_VAR="$(cat "${GRADLE_FILE}" | base64 -b 60 | awk '{printf "%s\\n", $0}')"
done

LOCAL_SHAS="$(
	for FILE in "${GRADLE_FILES[@]}"; do
		SHA="$(git hash-object "${FILE}")"

		if [[ -z "${SHA}" ]]; then
			error "Unable to parse sha for the file '${FILE}'."
		fi

		echo "${FILE}:${SHA}"
	done
)"

#
# Verify rate limit.
#

REMAINING_RATE="$(get_remaining_rate)"

if [[ "${REMAINING_RATE}" ]]; then
	info "Remaining GitHub rate limit: ${REMAINING_RATE}"
fi

TOTAL_SUBREPOS="$(printf '%s\n' "${SUBREPO_BRANCHES[@]}" | sed 's/.*://' | sort -u | wc -l | sed 's/ //g')"
TOTAL_BRANCHES="$(printf '%s\n' "${SUBREPO_BRANCHES[@]}" | wc -l | sed 's/ //g')"

info "Updating ${TOTAL_BRANCHES} branches across ${TOTAL_SUBREPOS} subrepos..."
info

MINIMUM_API_CALLS_PER_BRANCH=3
MAXIMUM_API_CALLS_PER_BRANCH=7

MINIMUM_RATE=$((MINIMUM_API_CALLS_PER_BRANCH*TOTAL_BRANCHES))
MAXIMUM_RATE=$((MAXIMUM_API_CALLS_PER_BRANCH*TOTAL_BRANCHES))

if ((REMAINING_RATE < MINIMUM_RATE)); then
	error "Your current rate limit is insufficient, a minimum of ${MINIMUM_RATE} api calls will be made. Try again in an hour when your rate limit resets."
fi

if ((REMAINING_RATE < MAXIMUM_RATE)); then
	warn "Warning: your current rate limit may be insufficient, up to ${MAXIMUM_RATE} api calls may be made."
fi

TOTAL_FILES_COUNTER=0

for SUBREPO_BRANCH in "${SUBREPO_BRANCHES[@]}"; do
	BRANCH="${SUBREPO_BRANCH%%:*}"
	SUBREPO="${SUBREPO_BRANCH##*:}"

	#
	# Retrieve the shas for the files in gradle/wrapper.
	# Also check for any errors interacting with the repo via the api.
	#

	GRADLE_WRAPPER_JSON=

	for i in {1..2}; do
		GRADLE_WRAPPER_JSON=($(curl --header "Authorization: token ${GITHUB_API_TOKEN}" -s "https://api.github.com/repos/liferay/${SUBREPO}/contents/gradle/wrapper?ref=${BRANCH}" -X GET | tr '\n' ' ' | sed 's/ //g' | sed 's/},/\'$'\n/g'))

		if [[ $(echo "${GRADLE_WRAPPER_JSON[@]}" | grep '\"sha\"') ]]; then
			break;
		fi
	done

	if [[ -z $(echo "${GRADLE_WRAPPER_JSON[@]}" | grep '\"sha\"') ]] && [[ -z $(echo "${GRADLE_WRAPPER_JSON[@]}" | grep -i '\"notfound\"') ]]; then
		warn "Skipping liferay/${SUBREPO}:${BRANCH}."
		warn ".. Failed to get contents of the gradle/wrapper directory via the api."

		continue
	fi

	GRADLE_WRAPPER_JAR_REMOTE_SHA="$(printf '%s\n' "${GRADLE_WRAPPER_JSON[@]}" | grep '"gradle-wrapper.jar"' | sed 's/",/&\'$'\n/g' | grep '"sha"' | sed 's/"[^"]*$//' | sed 's/.*"//')"
	GRADLE_WRAPPER_PROPERTIES_REMOTE_SHA="$(printf '%s\n' "${GRADLE_WRAPPER_JSON[@]}" | grep '"gradle-wrapper.properties"' | sed 's/",/&\'$'\n/g' | grep '"sha"' | sed 's/"[^"]*$//' | sed 's/.*"//')"

	#
	# Retrieve the shas for gradlew and gradlew.bat.
	# Assume "Not Found" just means the file doesn't exist.
	#

	GRADLEW_JSON=

	for i in {1..2}; do
		GRADLEW_JSON="$(curl --header "Authorization: token ${GITHUB_API_TOKEN}" -s "https://api.github.com/repos/liferay/${SUBREPO}/contents/gradlew?ref=${BRANCH}" -X GET)"

		if [[ $(echo "${GRADLEW_JSON[@]}" | grep '\"sha\"') ]] || [[ $(echo "${GRADLEW_JSON[@]}" | grep -i '\"not found\"') ]]; then
			break;
		fi
	done

	GRADLEW_REMOTE_SHA="$(echo "${GRADLEW_JSON}" | grep '"sha"' | sed 's/"[^"]*$//' | sed 's/.*"//')"

	GRADLEW_BAT_JSON=

	for i in {1..2}; do
		GRADLEW_BAT_JSON="$(curl --header "Authorization: token ${GITHUB_API_TOKEN}" -s "https://api.github.com/repos/liferay/${SUBREPO}/contents/gradlew.bat?ref=${BRANCH}" -X GET)"

		if [[ $(echo "${GRADLEW_BAT_JSON[@]}" | grep '\"sha\"') ]] || [[ $(echo "${GRADLEW_BAT_JSON[@]}" | grep -i '\"not found\"') ]]; then
			break;
		fi
	done

	GRADLEW_BAT_REMOTE_SHA="$(echo "${GRADLEW_BAT_JSON}" | grep '"sha"' | sed 's/"[^"]*$//' | sed 's/.*"//')"

	REMOTE_SHAS="
gradle/wrapper/gradle-wrapper.jar:${GRADLE_WRAPPER_JAR_REMOTE_SHA}
gradle/wrapper/gradle-wrapper.properties:${GRADLE_WRAPPER_PROPERTIES_REMOTE_SHA}
gradlew:${GRADLEW_REMOTE_SHA}
gradlew.bat:${GRADLEW_BAT_REMOTE_SHA}
"

	for GRADLE_FILE in "${GRADLE_FILES[@]}"; do
		LOCAL_SHA="$(echo "${LOCAL_SHAS}" | grep "^${GRADLE_FILE}:" | sed 's/.*://')"
		REMOTE_SHA="$(echo "${REMOTE_SHAS}" | grep "^${GRADLE_FILE}:" | sed 's/.*://')"

		if [[ "${LOCAL_SHA}" != "${REMOTE_SHA}" ]]; then
			if [[ -z "${OPTION_VERIFY}" ]]; then
				COMMIT_MESSAGE="LPS-0 Update ${GRADLE_FILE}"
				CONTENT_VAR="$(get_content_var "${GRADLE_FILE}")"

				# DATA="\"branch\":\"${BRANCH}\",\"content\":\"${!CONTENT_VAR}\",\"message\":\"${COMMIT_MESSAGE}\",\"path\": \"${GRADLE_FILE}\""
				#
				# if [[ "${REMOTE_SHA}" ]]; then
				# 	DATA="${DATA},\"sha\": \"${REMOTE_SHA}\""
				# fi
				#
				# for i in {1..2}; do
				# 	OUTPUT=$(curl -d "{${DATA}}" --header "Authorization: token ${GITHUB_API_TOKEN}" -s "https://api.github.com/repos/liferay/${SUBREPO}/contents/${GRADLE_FILE}" -X PUT 2>&1)
				#
				# 	if [[ "$(echo ${OUTPUT} | grep "message.*${COMMIT_MESSAGE}")" ]]; then
				# 		break
				# 	fi
				# done
				#
				# if [[ -z "$(echo ${OUTPUT} | grep "message.*${COMMIT_MESSAGE}")" ]]; then
				# 	warn "${OUTPUT}"
				#
				# 	warn "Failed to update ${GRADLE_FILE} at liferay/${SUBREPO}:${BRANCH}."
				#
				# 	continue
				# fi
			fi

			let TOTAL_FILES_COUNTER++
		fi
	done
done

info
info "${TOTAL_FILES_COUNTER} files were successfully updated."

REMAINING_RATE="$(get_remaining_rate)"

if [[ "${REMAINING_RATE}" ]]; then
	info "Remaining GitHub rate limit: ${REMAINING_RATE}"
fi