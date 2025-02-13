#!/bin/bash

function delete_branch {
	git checkout master

	git br -D ${1}

	git push origin :${1}
}

function get_property {
	echo $(grep "${1}=" release.properties | cut -d"=" -f2)
}

function main {
	echo "Skipping release tagging since merge of https://liferay.atlassian.net/browse/LPD-45794"

	return

	pushd $(git rev-parse --show-toplevel) > /dev/null

	local branch=$(parse_git_current_branch)
	local repo_dir=${PWD}
	local repo_name=$(basename ${PWD})

	if [ ${repo_name} == "liferay-portal" ]
	then
		local hash=$(git rev-parse HEAD)
		local release_info_version_trivial=$(get_property "release.info.version.trivial")

		push_tag 7.4.3.${release_info_version_trivial}-ga${release_info_version_trivial} ${hash}

		if [ ${branch} != "master" ]
		then
			delete_branch release-7.4.13.${release_info_version_trivial}
		fi

		cd ../liferay-portal-ee

		git fetch -f ../liferay-portal ${hash}

		push_tag 7.4.3.${release_info_version_trivial}-ga${release_info_version_trivial} ${hash}

		push_tag 7.4.13-u${release_info_version_trivial} ${hash}

		if [ ${branch} != "master" ]
		then
			delete_branch release-7.4.13.${release_info_version_trivial}
		fi
	else
		echo "${repo_dir} is an invalid directory for committing a release."
	fi

	popd > /dev/null
}

function parse_git_current_branch {
	git rev-parse --abbrev-ref HEAD 2>/dev/null
}

function push_tag {
	git tag ${1} ${2}

	git push origin ${1}
	git push upstream ${1}
}

main "${@}"