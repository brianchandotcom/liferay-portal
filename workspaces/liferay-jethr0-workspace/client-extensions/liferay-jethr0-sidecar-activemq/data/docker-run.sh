#!/bin/bash
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.

# This is the entry point for the docker images.
# This file is executed when "docker container create" or "docker run" is called.

set -e

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

	if [[ ${ARTEMIS_CLUSTER_PASSWORD} != "" ]] && [[ ${ARTEMIS_CLUSTER_USER} != "" ]]
	then
		if [[ ${ARTEMIS_CLUSTER_SLAVE} == "true" ]]
		then
			SLAVE_OPTION="--slave"
		else
			SLAVE_OPTION=""
		fi

		IP_ADDRESS=$(ifconfig | grep "inet " | grep -Fv 127.0.0.1 | awk '{print $2}' | cut -b 6-)

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
	fi
else
	echo "skipping broker instance creation; instance already exists"
fi

exec ./bin/artemis "$@"