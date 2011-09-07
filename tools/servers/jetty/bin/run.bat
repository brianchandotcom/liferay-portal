@echo off

if "" == "%JAVA_HOME%" goto errorJavaHome

set "JAVA_OPTS=-Xmx1024m -XX:MaxPermSize=256m"

"%JAVA_HOME%/bin/java" %JAVA_OPTS% -jar ../start.jar

goto end

:errorJavaHome
	echo JAVA_HOME not defined.

	goto end

:end