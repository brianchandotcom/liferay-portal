@echo off

if "" == "%JAVA_HOME%" goto errorJavaHome

set "JAVA_OPTS=-DSTOP.KEY=secret -DSTOP.PORT=8079 -Dfile.encoding=UTF8 -Djava.net.preferIPv4Stack=true -Duser.timezone=GMT -Xmx1024m -XX:MaxPermSize=256m"

"%JAVA_HOME%/bin/java" %JAVA_OPTS% -jar ../start.jar

goto end

:errorJavaHome
	echo JAVA_HOME not defined.

	goto end

:end