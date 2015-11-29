#!/bin/sh

exportJarsInFolder() {
	for jarFile in $1/*.jar
	do
		CLASSPATH="$CLASSPATH:$jarFile"
	done
}

showUsage () {
	echo "Usage : $0 --liferay_home <liferay.home> --classpath <portal classpath> [--java_home <path>] [--debug]"
	echo "-lh,--liferay_home, --liferay_home Path to the folder liferay-portal"
	echo "-cp, --classpath Path to the Liferay libraries deployed in your app server. Use the comma as the separator"
	echo "-d, --debug Start the VM in debug mode"
	echo "-jh, --java_home The environment variable JAVA_HOME should be defined (Optional)"

	exit 1;
}

STD_IN=0

prefix=""
key=""
value=""

CLASSPATH=""
DEBUG="false"
DEBUG_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9009"
JAVA_BIN="$(whereis java)"
JAVA_OPTS="-Xmx1024m -XX:MaxPermSize=512m"

for keyValue in "$@"
do
	case "${prefix}${keyValue}" in
		-lh=*|--liferay_home=*)	key="-liferay_home";	value="${keyValue#*=}";;
		-cp=*|--classpath=*)	key="-classpath";		value="${keyValue#*=}";;
		-d|--debug)				key="-debug";			value="${keyValue#*=}";;
		-jh=*|--java_home=*)	key="-java_home";		value="${keyValue#*=}";;
		*)												value=$keyValue;;
	esac
	case $key in
		-liferay_home)	LIFERAY_HOME=${value};	prefix=""; key="";;
		-classpath)		CLASSPATH=${value};		prefix=""; key="";;
		-debug)			DEBUG="true";			prefix=""; key="";;
		-java_home)		JAVA_HOME=${value};		prefix=""; key="";;
		*)										prefix="${keyValue}=";;
	esac
done

if [ ! $LIFERAY_HOME ] || [ ! $CLASSPATH ];
then
	showUsage
fi

if [ $JAVA_HOME ]
then
	JAVA_BIN=${JAVA_HOME}/bin/java
fi

if [ $DEBUG = "true" ]
then
	JAVA_OPTS="$JAVA_OPTS $DEBUG_OPTS"
fi


CURRENT_IFS="$IFS"
IFS="," 
for path in $CLASSPATH
do
  exportJarsInFolder $path
done

IFS="$CURRENT_IFS"

exportJarsInFolder ${LIFERAY_HOME}/osgi/core/

CLASSPATH=$CLASSPATH:$LIFERAY_HOME

$JAVA_BIN $JAVA_OPTS -Dfile.encoding=UTF8 -Duser.country=US -Duser.language=en -Duser.timezone=GMT -cp $CLASSPATH com.liferay.portal.tools.DBUpgrader