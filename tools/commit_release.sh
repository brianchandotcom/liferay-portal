#!/bin/bash

function get_property {
	echo $(grep "${1}=" release.properties | cut -d"=" -f2)
}

function main {
	pushd $(git rev-parse --show-toplevel) > /dev/null

	local branch

	branch=$(parse_git_current_branch)

	local repo_dir=${PWD}

	local repo_name

	repo_name=$(basename ${PWD})

	if [ ${repo_name} == "liferay-portal" ]
	then
		local release_info_version_trivial

		release_info_version_trivial=$(get_property "release.info.version.trivial")

		git clean -df && ant setup-profile-portal && ant setup-sdk && cd modules && ant build-app-all -Dartifact.exclude=true -Dcheck.stale.artifacts.skip=true -Ddownload.app.sources=false -Dgit.commit.app.ticket="7.4.3.${release_info_version_trivial} GA${release_info_version_trivial}"

		cd ..

		git clean -df && ant setup-profile-dxp && ant setup-sdk && cd modules && ant build-app-all -Dartifact.exclude=true -Dcheck.stale.artifacts.skip=true -Ddownload.app.sources=false -Dgit.commit.app.ticket="7.4.13 Update ${release_info_version_trivial}"

		git push origin ${branch}

		git clean -df

		cd ../../liferay-portal-ee

		git reset --hard HEAD

		if [ ${branch} == $(parse_git_current_branch) ]
		then
			git pull ../liferay-portal ${branch}:${branch}
		else
			git fetch ../liferay-portal ${branch}:${branch}
		fi

		git push origin ${branch}

		cd ../liferay-portal
	elif [ ${repo_name} == "liferay-portal-7.3.x" ]
	then
		local release_info_version_display_name

		release_info_version_display_name=$(get_property "release.info.version.display.name\[7.3.x-private\]")

		git clean -df && ant setup-profile-dxp && ant setup-sdk && cd modules && ant build-app-all -Dartifact.exclude=true -Dcheck.stale.artifacts.skip=true -Ddownload.app.sources=false -Dgit.commit.app.ticket="${release_info_version_display_name}"

		cd ..

		git push origin ${branch}
	else
		echo "${repo_dir} is an invalid directory for committing a release."
	fi

	popd > /dev/null
}

function parse_git_current_branch {
	git rev-parse --abbrev-ref HEAD 2>/dev/null
}

main "${@}"