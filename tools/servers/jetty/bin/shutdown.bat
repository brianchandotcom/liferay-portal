@echo off

if "" == "%JAVA_HOME%" goto errorJavaHome

set "JAVA_OPTS=-DSTOP.KEY=secret -DSTOP.PORT=8079"

"%JAVA_HOME%/bin/java" %JAVA_OPTS% -jar ../start.jar --stop

goto end

:errorJavaHome
	echo JAVA_HOME not defined.

	goto end

:end