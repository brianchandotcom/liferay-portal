#!/bin/sh

if [ ! $JAVA_HOME ]
then
	echo JAVA_HOME not defined.
	exit
fi

export USER_MEM_ARGS="-Xmx1024m -XX:PermSize=256m"
export MW_HOME=$1
export WL_HOME=${MW_HOME}/wlserver

source ${WL_HOME}/common/bin/commEnv.sh

export CLASSPATH=${FMWCONFIG_CLASSPATH}

${JAVA_HOME}/bin/java weblogic.WLST create-weblogic-domain.py ${WL_HOME} $2 $3
