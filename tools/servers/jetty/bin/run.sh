#!/bin/sh

if [ ! $JAVA_HOME ]
then
	echo JAVA_HOME not defined.
	exit
fi

export JAVA_OPTS="-Xmx1024m -XX:MaxPermSize=256m"

$JAVA_HOME/bin/java $JAVA_OPTS -jar ../start.jar
