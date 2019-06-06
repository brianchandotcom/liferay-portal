@echo off

if "" == "%JAVA_HOME%" goto errorJavaHome

set "JAVA_OPTS=-Dfile.encoding=UTF8 -Djava.locale.providers=JRE,COMPAT,CLDR -Djava.net.preferIPv4Stack=true -Duser.timezone=GMT -Xms2560m -Xmx2560m -XX:MaxNewSize=1536m -XX:MaxMetaspaceSize=768m -XX:MetaspaceSize=768m -XX:NewSize=1536m -XX:SurvivorRatio=7"

"%JAVA_HOME%/bin/java" %JAVA_OPTS% -jar ../start.jar

goto end

:errorJavaHome
	echo JAVA_HOME not defined.

	goto end

:end