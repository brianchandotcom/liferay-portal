#!/bin/bash

echo "Starting java import"

export JAVA_HOME=/usr/lib/jvm/zulu-11-amd64
export PATH=$JAVA_HOME/bin:$PATH

java -Xmx2048m -jar /home/liferay/liferay-learn-cron-all.jar

exit 0