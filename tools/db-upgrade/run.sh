LIFERAY_HOME=$(cd ../../;pwd)

DEVELOPMENT_LIBRARIES=${LIFERAY_HOME}/lib/development
PORTAL_LIBRARIES=${LIFERAY_HOME}/lib/portal
GLOBAL_LIBRARIES=${LIFERAY_HOME}/lib/global

exportJarsInFolder() {
for jarFile in $(ls -1 $1/*.jar)
do
#echo "Adding $jarFile to the classpath"
LIFERAY_CLASSPATH=$LIFERAY_CLASSPATH:$jarFile
done
}

if [ ! $JAVA_HOME ]
then
echo JAVA_HOME not defined.
exit
fi

export LIFERAY_CLASSPATH=${LIFERAY_HOME}/portal-impl/classes/:${LIFERAY_HOME}/portal-service/classes/:${LIFERAY_HOME}/util-java/util-java.jar:"lib/"

exportJarsInFolder $DEVELOPMENT_LIBRARIES
exportJarsInFolder $PORTAL_LIBRARIES
exportJarsInFolder $GLOBAL_LIBRARIES
exportJarsInFolder "lib/"

if [ "$1" = "debug" ]
then
DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9000"
fi


$JAVA_HOME/bin/java -Xmx1024m -XX:MaxPermSize=512M $DEBUG_OPTS -Dfile.encoding=UTF8 -Duser.country=US -Duser.language=en -Duser.timezone=GMT -cp $LIFERAY_CLASSPATH com.liferay.portal.tools.DBUpgrader