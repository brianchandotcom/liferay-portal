#!/bin/bash

function send_slack_message() {
	SLACK_MESSAGE=$1

	if [ -z "$SLACK_ENDPOINT" ] ; then return 0; fi

	CHANNEL=#is-sites-learn-devops
	if [ "${LCP_PROJECT_ENVIRONMENT}" = "extprd" ]; then
		CHANNEL=#is-sites-learn-devops-prd
	fi

	TIMESTAMP=$(date)
	LOG_URL="https://console.${LCP_INFRASTRUCTURE_DOMAIN}/projects/${LCP_PROJECT_ID}/services/${LCP_SERVICE_ID}/logs?instanceId=${HOSTNAME}&logServiceId=${LCP_SERVICE_ID}"

	SLACK_MESSAGE_TEXT="${TIMESTAMP} *${LCP_PROJECT_ID}*->*${LCP_SERVICE_ID}* <${LOG_URL}|${HOSTNAME}> \n>$SLACK_MESSAGE"

	curl -X POST --data-urlencode "payload={'channel': '${CHANNEL}', 'username': 'devopsbot', 'text': '${SLACK_MESSAGE_TEXT}', 'icon_emoji': ':robot_face:'}" ${SLACK_ENDPOINT}
}

send_slack_message "Import job starting"

echo "Cloning repo"

mkdir -p ~/.ssh

echo "-----BEGIN OPENSSH PRIVATE KEY-----" > ~/.ssh/id_rsa
echo "$LIFERAY_LEARN_GITHUB_DEPLOY_KEY" | fold -w 64 >> ~/.ssh/id_rsa
echo "-----END OPENSSH PRIVATE KEY-----" >> ~/.ssh/id_rsa

chmod 600 ~/.ssh/id_rsa

ssh-keyscan -t rsa github.com >> ~/.ssh/known_hosts

git clone --depth 1 "${LIFERAY_LEARN_GITHUB_REPO}" /opt/liferay-learn

git -C /opt/liferay-learn log

GIT_COMMIT=$(git -C /opt/liferay-learn log -1 --pretty=%B)

send_slack_message "Cloned repo *${LIFERAY_LEARN_GITHUB_REPO}* commit: *${GIT_COMMIT//$'\n'/}*"

cd /opt/liferay-learn/docs || exit

if [ -z "$SKIP_UPDATE_EXAMPLES" ] ; then
	echo "Running update_examples.sh"

	./update_examples.sh prod

	send_slack_message "update_examples.sh finished with return code $?"
fi

echo "Starting java import"

if [ -z "$LIFERAY_OAUTH_CLIENT_ID" ] ; then
	export LIFERAY_OAUTH_CLIENT_ID
	LIFERAY_OAUTH_CLIENT_ID=$(cat /etc/liferay/lxc/ext-init-metadata/learn-dxp-importer.oauth2.headless.server.client.id)
fi

if [ -z "$LIFERAY_OAUTH_CLIENT_SECRET" ] ; then
	export LIFERAY_OAUTH_CLIENT_SECRET
	LIFERAY_OAUTH_CLIENT_SECRET=$(cat /etc/liferay/lxc/ext-init-metadata/learn-dxp-importer.oauth2.headless.server.client.secret)
fi

if [ -z "$LIFERAY_URL" ] ; then
	export LIFERAY_URL
	LIFERAY_URL=https://$(cat /etc/liferay/lxc/dxp-metadata/com.liferay.lxc.dxp.mainDomain)
fi

java -Xmx2048m -agentlib:jdwp=transport=dt_socket,address=*:${DEBUG_PORT:-8001},server=y,suspend=n -jar /app.jar

IMPORT_RC=$?

if [ $IMPORT_RC -ne 0 ]; then
	send_slack_message ":red-alert: Import job finished with return code ${IMPORT_RC}"
else
	send_slack_message ":sunflower: Import job finished with return code ${IMPORT_RC}"
fi

exit 0