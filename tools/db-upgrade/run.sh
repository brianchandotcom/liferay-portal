#!/bin/sh

if [ ! $JAVA_HOME ]
then
	echo JAVA_HOME not defined.
	exit
fi

for fileName in lib/*.jar
do
	export LIFERAY_CLASSPATH=$LIFERAY_CLASSPATH:$fileName
done

$JAVA_HOME/bin/java -Xmx1024m -Dfile.encoding=UTF8 -Duser.country=US -Duser.language=en -Duser.timezone=GMT -cp $LIFERAY_CLASSPATH com.liferay.portal.tools.DBUpgrader