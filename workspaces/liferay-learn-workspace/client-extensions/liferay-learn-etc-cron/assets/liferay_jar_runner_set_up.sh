#!/bin/bash

function clone_repository {
	eval $(ssh-agent -s)

	echo -e "-----BEGIN OPENSSH PRIVATE KEY-----\n${LIFERAY_LEARN_ETC_CRON_GITHUB_DEPLOY_KEY}\n-----END OPENSSH PRIVATE KEY-----"| ssh-add -

	local github_branch=master

	if [ ! -z "${LIFERAY_LEARN_ETC_CRON_GITHUB_BRANCH}" ]
	then
		github_branch=${LIFERAY_LEARN_ETC_CRON_GITHUB_BRANCH}
	fi

	local github_user="liferay"

	if [ ! -z "${LIFERAY_LEARN_ETC_CRON_GITHUB_USER}" ]
	then
		github_user=${LIFERAY_LEARN_ETC_CRON_GITHUB_USER}
	fi

	local github_url=git@github.com:${github_user}/liferay-learn.git

	export GIT_SSH_COMMAND="ssh -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -q"

	if [ -d "${LIFERAY_LEARN_ETC_CRON_GIT_REPOSITORY_DIR}" ]
	then
		pushd ${LIFERAY_LEARN_ETC_CRON_GIT_REPOSITORY_DIR}

		rm -f .git/index.lock

		git reset --hard origin/master && git clean -xfe .latest_hash && git pull

		popd
	else
		git clone --branch ${github_branch} --depth 1 --single-branch ${github_url} ${LIFERAY_LEARN_ETC_CRON_GIT_REPOSITORY_DIR}
	fi

	if [ ! -e "$HOME/.gitconfig" ]
	then
		git config --global --add safe.directory ${LIFERAY_LEARN_ETC_CRON_GIT_REPOSITORY_DIR}
	fi

	git -C ${LIFERAY_LEARN_ETC_CRON_GIT_REPOSITORY_DIR} log

	local git_log=$(git -C ${LIFERAY_LEARN_ETC_CRON_GIT_REPOSITORY_DIR} log -1 --pretty="%B %H %aN")

	send_slack_message "Cloned *${github_url}*: *${git_log//$\"\n\"/}*"
}

function copy_examples {

	#
	# Include must come before exclude.
	#

	rsync --include="*.zip" --include="*/" --exclude="*" --prune-empty-dirs --recursive ${LIFERAY_LEARN_ETC_CRON_GIT_REPOSITORY_DIR}/site /public_html
}

function copy_images {

	#
	# Include must come before exclude.
	#

	rsync --include="images/*" --include="*/" --exclude="*" --prune-empty-dirs --recursive ${LIFERAY_LEARN_ETC_CRON_GIT_REPOSITORY_DIR}/docs/ /public_html/images
}

function copy_reference_docs {

	#
	# Include must come before exclude.
	#

	rsync --include="reference/*" --include="*/" --exclude="*" --prune-empty-dirs --recursive ${LIFERAY_LEARN_ETC_CRON_GIT_REPOSITORY_DIR}/site /public_html/reference
}

function copy_resources {
	copy_examples
	copy_images
	copy_reference_docs
}

function generate_docs {
	pushd ${LIFERAY_LEARN_ETC_CRON_GIT_REPOSITORY_DIR}

	export LIFERAY_LEARN_SKIP_DIFF_CHECK=${LIFERAY_LEARN_ETC_SKIP_DIFF_CHECK}

	./generate_docs.sh

	popd
}

function main {
	clone_repository

	replace_tokens

	generate_docs

	copy_resources

	prepare_import
}

function prepare_import {
	export JAVA_HOME=/usr/lib/jvm/zulu-11-amd64
	export PATH=${JAVA_HOME}/bin:${PATH}

	java -version

	if [ -z "${LIFERAY_LEARN_ETC_CRON_GIT_REPOSITORY_DIR}" ]
	then
		export LIFERAY_LEARN_ROOT_DIR=${LIFERAY_LEARN_ETC_CRON_GIT_REPOSITORY_DIR}
	fi

	if [ -z "${LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_ID}" ]
	then
		export LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_ID=$(cat /etc/liferay/lxc/ext-init-metadata/liferay-learn-etc-cron-oauth-application-headless-server.oauth2.headless.server.client.id)
	fi

	if [ -z "${LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_SECRET}" ]
	then
		export LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_SECRET=$(cat /etc/liferay/lxc/ext-init-metadata/liferay-learn-etc-cron-oauth-application-headless-server.oauth2.headless.server.client.secret)
	fi

	if [ -z "${LIFERAY_LEARN_ETC_CRON_LIFERAY_URL}" ]
	then
		export LIFERAY_LEARN_ETC_CRON_LIFERAY_URL="https://$(cat /etc/liferay/lxc/dxp-metadata/com.liferay.lxc.dxp.mainDomain)"
	fi
}

function replace_tokens {
	pushd ~/liferay-learn/docs

	./replace_tokens.sh

	popd
}

function send_slack_message {
	local slack_message=${1}

	if [ -z "${LIFERAY_LEARN_ETC_CRON_SLACK_ENDPOINT}" ]
	then
		return
	fi

	local log_url="https://console.${LCP_INFRASTRUCTURE_DOMAIN}/projects/${LCP_PROJECT_ID}/services/${LCP_SERVICE_ID}/logs?instanceId=${HOSTNAME}&logServiceId=${LCP_SERVICE_ID}"

	local text="$(date) *${LCP_PROJECT_ID}*->*${LCP_SERVICE_ID}* <${log_url}|${HOSTNAME}> \n>${slack_message}"

	eval curl \
		-X POST \
		-d "'{\"channel\": \"${LIFERAY_LEARN_ETC_CRON_SLACK_CHANNEL}\", \"icon_emoji\": \":robot_face:\", \"text\": \"${text}\", \"username\": \"devopsbot\"}'" ${LIFERAY_LEARN_ETC_CRON_SLACK_ENDPOINT}
}

main "${@}"