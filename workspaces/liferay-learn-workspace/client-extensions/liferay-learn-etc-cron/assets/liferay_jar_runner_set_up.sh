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

	GIT_SSH_COMMAND="ssh -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -q" \
		git clone --branch ${github_branch} --depth 1 --single-branch ${github_url} ~/liferay-learn

	git -C ~/liferay-learn log

	local git_log=$(git -C ~/liferay-learn log -1 --pretty="%B %H %aN")

	send_slack_message "Cloned *${github_url}*: *${git_log//$\"\n\"/}*"
}

function copy_images {
	# Do not sort. The exclude option must follow the include options.
	rsync --include="images/*" --include="*/" --exclude="*" --prune-empty-dirs --recursive ~/liferay-learn/docs /public_html/images
}

function generate_zip_files {
	if [ ! -z "${LIFERAY_LEARN_ETC_CRON_SKIP_GENERATE_ZIP_FILES}" ]
	then
		return
	fi

	pushd ~/liferay-learn > /dev/null

	for zip_dir_name in $(find docs -name \*.zip -type d)
	do
		pushd "${zip_dir_name}"

		local zip_file_name=$(basename "${zip_dir_name}")

		7z a ${zip_file_name} ../${zip_file_name}\

		7z rn ${zip_file_name} ${zip_file_name} ${zip_file_name%.*}

		popd

		local output_dir_name=$(dirname "/public_html/${zip_dir_name}")
		local output_dir_name=$(dirname "${output_dir_name}")
		local output_dir_name=$(dirname "${output_dir_name}")

		mkdir -p "/${output_dir_name}"

		mv "${zip_dir_name}"/"${zip_file_name}" "${output_dir_name}"
	done

	popd > /dev/null
}

function get_reference_docs {
	if [ ! -z "${LIFERAY_LEARN_ETC_CRON_SKIP_POPULATE_REFERENCE_DOCS}" ]
	then
		return
	fi

	#
	# liferay-ce-portal-doc-*.zip
	#

	curl -L https://github.com/liferay/liferay-portal/releases/download/"${LIFERAY_LEARN_PORTAL_GIT_TAG_VALUE}"/"${LIFERAY_LEARN_PORTAL_DOC_FILE_NAME}" > liferay-ce-portal-doc.zip

	7z x liferay-ce-portal-doc.zip

	mkdir -p /public_html/reference/latest/en/dxp

	cp -R liferay-ce-portal-doc-${LIFERAY_LEARN_PORTAL_GIT_TAG_VALUE}/* /public_html/reference/latest/en/dxp

	rmdir liferay-ce-portal-doc-${LIFERAY_LEARN_PORTAL_GIT_TAG_VALUE}

	rm -f liferay-ce-portal-doc.zip

	local apps_markdown_file_name=~/liferay-learn/docs/reference/latest/en/dxp/apps.md

	echo "---" > ${apps_markdown_file_name}
	echo "uuid: ba71e6fa-d76f-42ec-b3bb-c54cebae6156" >> ${apps_markdown_file_name}
	echo "---" >> ${apps_markdown_file_name}
	echo "# Apps" >> ${apps_markdown_file_name}
	echo "" >> ${apps_markdown_file_name}

	for app_dir_name in /public_html/reference/latest/en/dxp/javadocs/modules/apps/*
	do
		echo "## $(basename $app_dir_name)" >> ${apps_markdown_file_name}

		for app_jar_dir_name in ${app_dir_name}/*
		do
			local app_jar_relative_path=$(echo "${app_jar_dir_name}/index.html" | cut -d/ -f4-)

			echo "[${app_jar_dir_name##*/}](${LIFERAY_LEARN_ETC_CRON_LIFERAY_LEARN_RESOURCES_DOMAIN}/reference/${app_jar_relative_path})" >> ${apps_markdown_file_name}
			echo "" >> ${apps_markdown_file_name}
		done
	done

	#
	# portlet-api-3.0.1-javadoc.jar
	#

	curl https://repo1.maven.org/maven2/javax/portlet/portlet-api/3.0.1/portlet-api-3.0.1-javadoc.jar -O

	mkdir -p /public_html/reference/latest/en/dxp/portlet-api

	7z x -aoa -o/public_html/reference/latest/en/portlet-api portlet-api-3.0.1-javadoc.jar

	rm -f portlet-api-3.0.1-javadoc.jar
}

function main {
	clone_repository

	update_examples

	generate_zip_files

	copy_images

	replace_tokens

	get_reference_docs

	prepare_import
}

function prepare_import {
	export JAVA_HOME=/usr/lib/jvm/zulu-11-amd64
	export PATH=${JAVA_HOME}/bin:${PATH}

	java -version

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
	~/liferay-learn/docs/replace_tokens.sh
}

function send_slack_message {
	local slack_message=${1}

	if [ -z "${LIFERAY_LEARN_ETC_CRON_SLACK_ENDPOINT}" ]
	then
		return
	fi

	local log_url="https://console.${LCP_INFRASTRUCTURE_DOMAIN}/projects/${LCP_PROJECT_ID}/services/${LCP_SERVICE_ID}/logs?instanceId=${HOSTNAME}&logServiceId=${LCP_SERVICE_ID}"

	local text="$(date) *${LCP_PROJECT_ID}*->*${LCP_SERVICE_ID}* <${log_url}|${HOSTNAME}> \n>${slack_message}"

	curl \
		-X POST \
		-d "payload={\"channel\": \"${LIFERAY_LEARN_ETC_CRON_SLACK_CHANNEL}\", \"icon_emoji\": \":robot_face:\", \"text\": \"${text}\", \"username\": \"devopsbot\"}" ${LIFERAY_LEARN_ETC_CRON_SLACK_ENDPOINT}
}

function update_examples {
	if [ ! -z "${LIFERAY_LEARN_ETC_CRON_SKIP_UPDATE_EXAMPLES}" ]
	then
		return
	fi

	export JAVA_HOME=/usr/lib/jvm/zulu-8-amd64
	export PATH=${JAVA_HOME}/bin:${PATH}

	source ~/liferay-learn/_common.sh

	java -version

	~/liferay-learn/docs/update_examples.sh prod 2> ~/update_examples.err

	local exit_code=$?

	cat ~/update_examples.err

	send_slack_message "update_examples.sh finished with return code ${exit_code}. There are $(wc -l < ~/update_examples.err) lines in update_examples.err."
}

main "${@}"