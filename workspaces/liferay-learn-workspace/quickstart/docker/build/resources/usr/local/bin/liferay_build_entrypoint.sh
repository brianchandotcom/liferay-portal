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

	cd /mnt/liferay-learn-workspace || exit

	./gradlew --gradle-user-home="$HOME"/.gradle clean createDockerfile
}

main "${@}"