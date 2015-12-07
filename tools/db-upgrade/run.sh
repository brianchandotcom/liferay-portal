#!/bin/sh

exportJars() {
	for jarFile in ${1}/*.jar
	do
		CLASSPATH="${CLASSPATH}:${jarFile}"
	done
}

showUsage() {
	echo "Usage : $0 --classpath <classpath> [--debug] [--java_home <Java home>] --liferay_home <Liferay home>"
	echo "-cp, --classpath Set the classpath to the Liferay libraries deployed in your application server. Use the comma as a separator."
	echo "-d, --debug Start the JVM in debug mode."
	echo "-jh, --java_home Set the Java home directory or optionally default to the value specified in the environment variable JAVA_HOME."
	echo "-lh,--liferay_home, --liferay_home Set the Liferay home directory."

	exit 1;
}

CLASSPATH=""
DEBUG="false"
DEBUG_OPTS="-agentlib:address=9009,jdwp=transport=dt_socket,server=y,suspend=n"
JAVA_BIN="$(whereis java)"
JAVA_OPTS="-Xmx1024m -XX:MaxPermSize=512m"
STD_IN=0

key=""
prefix=""
value=""

for keyValue in "$@"
do
	case "${prefix}${keyValue}" in
		-cp=*|--classpath=*)	key="-classpath";		value="${keyValue#*=}";;
		-d|--debug)				key="-debug";			value="${keyValue#*=}";;
		-jh=*|--java_home=*)	key="-java_home";		value="${keyValue#*=}";;
		-lh=*|--liferay_home=*)	key="-liferay_home";	value="${keyValue#*=}";;
		*)												value=${keyValue};;
	esac
	case $key in
		-classpath)		CLASSPATH=${value};		prefix=""; key="";;
		-debug)			DEBUG="true";			prefix=""; key="";;
		-java_home)		JAVA_HOME=${value};		prefix=""; key="";;
		-liferay_home)	LIFERAY_HOME=${value};	prefix=""; key="";;
		*)										prefix="${keyValue}=";;
	esac
done

if [ ! ${CLASSPATH} ] || [ ! ${LIFERAY_HOME} ];
then
	showUsage
fi

if [ ${JAVA_HOME} ]
then
	JAVA_BIN=${JAVA_HOME}/bin/java
fi

if [ ${DEBUG} = "true" ]
then
	JAVA_OPTS="$JAVA_OPTS ${DEBUG_OPTS}"
fi

CURRENT_IFS="${IFS}"

IFS="," 
for path in ${CLASSPATH}
do
	exportJars ${path}
done

IFS="${CURRENT_IFS}"

exportJars ${LIFERAY_HOME}/osgi/core

CLASSPATH=${CLASSPATH}:${LIFERAY_HOME}

${JAVA_BIN} ${JAVA_OPTS} -cp ${CLASSPATH} -Dfile.encoding=UTF8 -Duser.country=US -Duser.language=en -Duser.timezone=GMT com.liferay.portal.tools.DBUpgrader