#!/bin/bash

BROKER_HOME=/var/lib/

CONFIG_PATH=$BROKER_HOME/etc

export BROKER_HOME OVERRIDE_PATH CONFIG_PATH

if [[ ${ANONYMOUS_LOGIN,,} == "true" ]]
then
	LOGIN_OPTION="--allow-anonymous"
else
	LOGIN_OPTION="--require-login"
fi

if ! [ -f ./etc/broker.xml ]
then
	IP_ADDRESS=$(ifconfig | grep "inet " | grep -Fv 127.0.0.1 | awk '{print $2}' | cut -b 6-)

	if [[ ${ARTEMIS_CLUSTER_PASSWORD} != "" ]] && [[ ${ARTEMIS_CLUSTER_USER} != "" ]]
	then
		if [[ ${ARTEMIS_CLUSTER_SLAVE} == "true" ]]
		then
			SLAVE_OPTION="--slave"
		else
			SLAVE_OPTION=""
		fi

		CLUSTER_ARGUMENTS="--clustered --cluster-password ${ARTEMIS_CLUSTER_PASSWORD} --cluster-user ${ARTEMIS_CLUSTER_USER} --host ${IP_ADDRESS} --replicated ${SLAVE_OPTION}"
	else
		CLUSTER_ARGUMENTS=""
	fi

	CREATE_ARGUMENTS="--user ${ARTEMIS_USER} --password ${ARTEMIS_PASSWORD} --silent ${LOGIN_OPTION} ${EXTRA_ARGS}"

	echo "/opt/activemq-artemis/bin/artemis create ${CREATE_ARGUMENTS} ${CLUSTER_ARGUMENTS} ."

	/opt/activemq-artemis/bin/artemis create ${CREATE_ARGUMENTS} ${CLUSTER_ARGUMENTS} .

	if [ -d ./etc-override ]
	then
		for file in `ls ./etc-override`
		do
			echo copying file to etc folder: $file
			cp ./etc-override/$file ./etc || :
		done
	fi

	if [[ ${ARTEMIS_CLUSTER_PASSWORD} != "" ]] && [[ ${ARTEMIS_CLUSTER_USER} != "" ]]
	then
		if [[ ${ARTEMIS_CLUSTER_SLAVE} == "true" ]]
		then
			sed -i -e "s|<slave/>|<slave><allow-failback>true</allow-failback></slave>|g" ./etc/broker.xml
		else
			sed -i -e "s|<vote-on-replication-failure|<check-for-live-server>true</check-for-live-server><vote-on-replication-failure|g" ./etc/broker.xml
		fi

		sed -i -e "s|<group-address>|<local-bind-address>${IP_ADDRESS}</local-bind-address><group-address>|g" ./etc/broker.xml
	fi
else
	echo "skipping broker instance creation; instance already exists"
fi

exec ./bin/artemis "$@"