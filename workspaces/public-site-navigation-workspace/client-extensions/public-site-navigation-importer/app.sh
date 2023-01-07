#!/bin/bash

if [ -z "$LIFERAY_OAUTH_CLIENT_ID" ] ; then
	export LIFERAY_OAUTH_CLIENT_ID
	LIFERAY_OAUTH_CLIENT_ID=$(cat /etc/liferay/lxc/ext-init-metadata/public-site-navigation-importer.oauth2.headless.server.client.id)
fi

if [ -z "$LIFERAY_OAUTH_CLIENT_SECRET" ] ; then
	export LIFERAY_OAUTH_CLIENT_SECRET
	LIFERAY_OAUTH_CLIENT_SECRET=$(cat /etc/liferay/lxc/ext-init-metadata/public-site-navigation-importer.oauth2.headless.server.client.secret)
fi

if [ -z "$LIFERAY_URL" ] ; then
	export LIFERAY_URL
	LIFERAY_URL=https://$(cat /etc/liferay/lxc/dxp-metadata/com.liferay.lxc.dxp.mainDomain)
fi

java -Xmx2048m -agentlib:jdwp=transport=dt_socket,address=*:${DEBUG_PORT:-8001},server=y,suspend=n -jar /app.jar