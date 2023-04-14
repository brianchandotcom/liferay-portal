#!/bin/bash

function clone_repo {
	echo "Cloning repo"

	mkdir -p ~/.ssh

	echo "-----BEGIN OPENSSH PRIVATE KEY-----" > ~/.ssh/id_rsa
	echo "${LIFERAY_LEARN_ETC_CRON_GITHUB_DEPLOY_KEY}" | fold -w 64 >> ~/.ssh/id_rsa
	echo "-----END OPENSSH PRIVATE KEY-----" >> ~/.ssh/id_rsa

	chmod 600 ~/.ssh/id_rsa

	ssh-keyscan -t rsa github.com >> ~/.ssh/known_hosts

	local repo_folder=~/liferay-learn

	git clone -b ${LIFERAY_LEARN_ETC_CRON_GITHUB_BRANCH} --depth 1 --single-branch "${LIFERAY_LEARN_ETC_CRON_GITHUB_REPO}" ${repo_folder}

	git -C ${repo_folder} log

	local git_commit=$(git -C ${repo_folder} log -1 --pretty="%B %H %aN")

	send_slack_message "Cloned repo *${LIFERAY_LEARN_ETC_CRON_GITHUB_REPO}* commit: *${git_commit//$\"\n\"/}*"
}

function copy_image_files {
	echo "Copying image files"

	rsync --include=\"images/*\" --include=\"*/\" --exclude=\"*\" --prune-empty-dirs -r ${repo_folder}/docs/ /public_html/images
}

function generate_zip_files {
	if [ -z "${LIFERAY_LEARN_ETC_CRON_SKIP_GENERATE_ZIPS}" ]
	then
		echo "Generating zip files"

		cd ${repo_folder}/docs || exit

		for zip_dir_name in $(find * -name \*.zip -type d)
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
	fi
}

function main {
	send_slack_message "Import job starting"

	clone_repo

	run_update_examples

	generate_zip_files

	populate_reference_docs

	substitute_tokens

	copy_image_files

	run_java_import

	exit 0
}

function populate_reference_docs {
	if [ -z "${LIFERAY_LEARN_ETC_CRON_SKIP_REFERENCE_DOCS}" ]
	then
		echo "Populating reference docs"

		curl -L https://github.com/liferay/liferay-portal/releases/download/"${LIFERAY_LEARN_PORTAL_GIT_TAG_VALUE}"/"${LIFERAY_LEARN_PORTAL_DOC_FILE_NAME}" > liferay-ce-portal-doc.zip

		7z x liferay-ce-portal-doc.zip

		mkdir -p /public_html/reference/latest/en/dxp

		cp -r liferay-ce-portal-doc-${LIFERAY_LEARN_PORTAL_GIT_TAG_VALUE}/* /public_html/reference/latest/en/dxp

		local apps_markdown_file=${repo_folder}/docs/reference/latest/en/dxp/apps.md

		echo "---" > ${apps_markdown_file}
		echo "uuid: ba71e6fa-d76f-42ec-b3bb-c54cebae6156" >> ${apps_markdown_file}
		echo "---" >> ${apps_markdown_file}
		echo "# Apps" >> ${apps_markdown_file}

		echo "" >> ${apps_markdown_file}

		for app_dir_name in /public_html/reference/latest/en/dxp/javadocs/modules/apps/*
		do
			echo "## $(basename $app_dir_name)" >> ${apps_markdown_file}

			for app_jar_dir_name in ${app_dir_name}/*
			do
				local app_jar_relative_path=$(echo "${app_jar_dir_name}/index.html" | cut -d/ -f4-)

				echo "[${app_jar_dir_name##*/}](${LIFERAY_LEARN_ETC_CRON_LIFERAY_LEARN_RESOURCES_DOMAIN}/reference/${app_jar_relative_path})" >> ${apps_markdown_file}
				echo "" >> ${apps_markdown_file}
			done
		done

		#
		# portlet-api-3.0.1-javadoc.jar
		#

		curl https://repo1.maven.org/maven2/javax/portlet/portlet-api/3.0.1/portlet-api-3.0.1-javadoc.jar -O

		mkdir -p /public_html/reference/latest/en/dxp/portlet-api

		7z x -aoa -o/public_html/reference/latest/en/portlet-api portlet-api-3.0.1-javadoc.jar

		rm -f portlet-api-3.0.1-javadoc.jar
	fi
}

function run_update_examples {
	cd ${repo_folder}/docs || exit

	export JAVA_HOME=/usr/lib/jvm/zulu-8-amd64
	export PATH=${JAVA_HOME}/bin:${PATH}

	source ${repo_folder}/_common.sh

	java -version

	if [ -z "${LIFERAY_LEARN_ETC_CRON_SKIP_UPDATE_EXAMPLES}" ]
	then
		echo "Running update_examples.sh"

		local update_examples_log_file=~/update_examples.log

		./update_examples.sh prod 2> ${update_examples_log_file}

		local update_examples_rc=$?

		echo "update_examples.sh error log:"
		cat ${update_examples_log_file}

		local error_count=$(wc -l < ${update_examples_log_file})

		send_slack_message "update_examples.sh finished with return code ${update_examples_rc}. ${error_count} entries in error log file."
	fi
}

function run_java_import {
	echo "Starting java import"

	export JAVA_HOME=/usr/lib/jvm/zulu-11-amd64
	export PATH=${JAVA_HOME}/bin:${PATH}

	java -version

	if [ -z "${LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_ID}" ]
	then
		export LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_ID
		LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_ID=$(cat /etc/liferay/lxc/ext-init-metadata/liferay-learn-etc-cron.oauth2.headless.server.client.id)
	fi

	if [ -z "${LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_SECRET}" ]
	then
		export LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_SECRET
		LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_SECRET=$(cat /etc/liferay/lxc/ext-init-metadata/liferay-learn-etc-cron.oauth2.headless.server.client.secret)
	fi

	if [ -z "${LIFERAY_LEARN_ETC_CRON_LIFERAY_URL}" ]
	then
		export LIFERAY_LEARN_ETC_CRON_LIFERAY_URL
		LIFERAY_LEARN_ETC_CRON_LIFERAY_URL=https://$(cat /etc/liferay/lxc/dxp-metadata/com.liferay.lxc.dxp.mainDomain)
	fi

	java -Xmx2048m -jar /home/liferay/liferay-learn-etc-cron-all.jar

	local import_rc=$?

	if [ ${import_rc} -ne 0 
	then
		send_slack_message ":red-alert: Import job finished with return code ${import_rc}"
	else
		send_slack_message ":sunflower: Import job finished with return code ${import_rc}"
	fi
}

function send_slack_message() {
	local slack_message=${1}

	echo "${slack_message}"

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

function substitute_tokens {
	echo "Substituting tokens"

	for md_file_name in $(find ${repo_folder}/docs -name "*.md" -type f)
	do
		sed -i "s/${LIFERAY_LEARN_COMMERCE_DOCKER_IMAGE_TOKEN}/${LIFERAY_LEARN_COMMERCE_DOCKER_IMAGE_VALUE}/g" "${md_file_name}"
		sed -i "s/${LIFERAY_LEARN_COMMERCE_GIT_TAG_TOKEN}/${LIFERAY_LEARN_COMMERCE_GIT_TAG_VALUE}/g" "${md_file_name}"
		sed -i "s/${LIFERAY_LEARN_DXP_DOCKER_IMAGE_TOKEN}/${LIFERAY_LEARN_DXP_DOCKER_IMAGE_VALUE}/g" "${md_file_name}"
		sed -i "s/${LIFERAY_LEARN_PORTAL_DOCKER_IMAGE_TOKEN}/${LIFERAY_LEARN_PORTAL_DOCKER_IMAGE_VALUE}/g" "${md_file_name}"
		sed -i "s/${LIFERAY_LEARN_PORTAL_GIT_TAG_TOKEN}/${LIFERAY_LEARN_PORTAL_GIT_TAG_VALUE}/g" "${md_file_name}"
		sed -i "s/${LIFERAY_LEARN_PORTAL_WORKSPACE_TOKEN}/${LIFERAY_LEARN_PORTAL_WORKSPACE_TOKEN_VALUE}/g" "${md_file_name}"
		sed -i "s/\(${LIFERAY_LEARN_YOUTUBE_URL_TOKEN}\=\)\(\https:\/\/www.youtube.com\/embed\/.*\)/${LIFERAY_LEARN_YOUTUBE_BEGIN_HTML}\2${LIFERAY_LEARN_YOUTUBE_END_HTML}/" "${md_file_name}"
	done
}

main "${@}"