#!/bin/sh

if [ ! $JAVA_HOME ]
then
	echo JAVA_HOME not defined.
	exit
fi

export JAVA_OPTS="-DSTOP.KEY=secret -DSTOP.PORT=8079"

$JAVA_HOME/bin/java $JAVA_OPTS -jar ../start.jar --stop