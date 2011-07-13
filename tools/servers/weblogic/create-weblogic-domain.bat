@echo off

if "" == "%JAVA_HOME%" goto errorJavaHome

set "USER_MEM_ARGS=-Xmx1024m -XX:PermSize=256m"
set "MW_HOME=%1"
set "WL_HOME=%MW_HOME%/wlserver"

call %WL_HOME%/common/bin/commEnv.cmd

set "CLASSPATH=%FMWCONFIG_CLASSPATH%"

"%JAVA_HOME%/bin/java" weblogic.WLST create-weblogic-domain.py %WL_HOME% %2 %3

goto end

:errorJavaHome
    echo JAVA_HOME not defined.

    goto end

:end
