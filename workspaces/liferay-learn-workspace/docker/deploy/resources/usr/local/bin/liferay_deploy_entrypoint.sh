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

	rsync -a --exclude={'.gradle','*/node_modules*/'} /mnt/liferay-learn-workspace "${HOME}"

	cd "$HOME"/liferay-learn-workspace || exit

	./gradlew -Pliferay.workspace.home.dir=/opt/liferay clean deploy

	rsync -a --exclude={'.gradle','*/node_modules*/'} /mnt/liferay-learn "${HOME}"

	cd "$HOME"/liferay-learn || exit

	./generate_docs.sh

	rsync -a "$HOME"/liferay-learn/site/images /public_html

	export LIFERAY_LEARN_ETC_CRON_LIFERAY_SITE_FRIENDLY_URL_PATH=guest

	export LIFERAY_LEARN_ETC_CRON_LIFERAY_URL=http://liferay:8080

	export LIFERAY_LEARN_ETC_CRON_GIT_REPOSITORY_DIR=$HOME/liferay-learn

	LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_ID=$(mysql -u root -h database -s -N -e \
		"SELECT clientId FROM lportal.OAuth2Application WHERE externalReferenceCode=
			'liferay-learn-etc-cron-oauth-application-headless-server'")

	export LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_ID

	LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_SECRET=$(mysql -u root -h database -s -N -e \
		"SELECT clientSecret FROM lportal.OAuth2Application WHERE externalReferenceCode=
			'liferay-learn-etc-cron-oauth-application-headless-server'")

	export LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_SECRET

	cd "$HOME"/liferay-learn-workspace/client-extensions/liferay-learn-etc-cron || exit

	../../gradlew run
}

main "${@}"