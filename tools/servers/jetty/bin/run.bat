@echo off

if "" == "%JAVA_HOME%" goto errorJavaHome

set "JAVA_OPTS=-Xmx1024m -XX:MaxPermSize=256m -Dfile.encoding=UTF8 -Djava.io.tmpdir=../temp -Djava.net.preferIPv4Stack=true -Duser.timezone=GMT"

"%JAVA_HOME%/bin/java" %JAVA_OPTS% -jar ../start.jar

goto end

:errorJavaHome
	echo JAVA_HOME not defined.

	goto end

:end