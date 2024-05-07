#!/bin/bash

function main {
	export JAVA_HOME=/usr/lib/jvm/${JAVA_VERSION}
	export PATH=/usr/lib/jvm/${JAVA_VERSION}/bin/:${PATH}

	if [ -e /opt/liferay/caroot/rootCA.pem ]
	then
		export CAROOT=/opt/liferay/caroot
		export TRUST_STORES=java

		mkcert -install
	fi

	mysql -u root -h database -s -N -e "UPDATE lportal.Group_ SET externalReferenceCode='LIFERAY_LEARN'
		WHERE groupKey = 'Guest'"

	cd /mnt/liferay-learn-workspace || exit

	./gradlew --gradle-user-home="$HOME"/.gradle -Pliferay.workspace.home.dir=/opt/liferay clean deploy

	cd /mnt/liferay-learn || exit

	./generate_docs.sh

	rsync -a -p /mnt/liferay-learn/site/images /public_html

	export LIFERAY_LEARN_ETC_CRON_LIFERAY_SITE_FRIENDLY_URL_PATH=guest
	export LIFERAY_LEARN_ETC_CRON_LIFERAY_URL=http://liferay:8080
	export LIFERAY_LEARN_ETC_CRON_GIT_REPOSITORY_DIR=/mnt/liferay-learn

	LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_ID=$(mysql -u root -h database -s -N -e \
		"SELECT clientId FROM lportal.OAuth2Application WHERE externalReferenceCode=
			'liferay-learn-etc-cron-oauth-application-headless-server'")

	export LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_ID

	LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_SECRET=$(mysql -u root -h database -s -N -e \
		"SELECT clientSecret FROM lportal.OAuth2Application WHERE externalReferenceCode=
			'liferay-learn-etc-cron-oauth-application-headless-server'")

	export LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_SECRET

	cd /mnt/liferay-learn-workspace/client-extensions/liferay-learn-etc-cron || exit

	../../gradlew --gradle-user-home="$HOME"/.gradle run
}

main "${@}"