#!/bin/bash

cp -R /home/liferay/configs/* ${LIFERAY_HOME}
cp -R /home/liferay/resources/context.xml $LIFERAY_HOME/tomcat/conf
cp -R /home/liferay/resources/log4j ${LIFERAY_HOME}/osgi/log4j
cp -R /home/liferay/resources/portal-log4j-ext.xml ${LIFERAY_HOME}/tomcat/webapps/ROOT/WEB-INF/classes/META-INF
cp -R /home/liferay/resources/rewrite.config ${LIFERAY_HOME}/tomcat/webapps/ROOT/WEB-INF
cp -R /home/liferay/resources/system-ext.properties ${LIFERAY_HOME}/tomcat/webapps/ROOT/WEB-INF/classes