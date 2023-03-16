#!/bin/bash

if [ -z "$LIFERAY_OAUTH_CLIENT_ID" ] ; then
	export LIFERAY_OAUTH_CLIENT_ID
	LIFERAY_OAUTH_CLIENT_ID=$(cat /etc/liferay/lxc/ext-init-metadata/liferay-learn-ddm-importer.oauth2.headless.server.client.id)
fi

if [ -z "$LIFERAY_OAUTH_CLIENT_SECRET" ] ; then
	export LIFERAY_OAUTH_CLIENT_SECRET
	LIFERAY_OAUTH_CLIENT_SECRET=$(cat /etc/liferay/lxc/ext-init-metadata/liferay-learn-ddm-importer.oauth2.headless.server.client.secret)
fi

if [ -z "$LIFERAY_URL" ] ; then
	export LIFERAY_URL
	LIFERAY_URL=https://$(cat /etc/liferay/lxc/dxp-metadata/com.liferay.lxc.dxp.mainDomain)
fi

export JAVA_HOME=/usr/lib/jvm/zulu-11-amd64
export PATH=$JAVA_HOME/bin:$PATH

java -Xmx2048m -jar /home/liferay/liferay-learn-ddm-importer-all.jar