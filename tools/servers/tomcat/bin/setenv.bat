if exist "%CATALINA_HOME%/jre@java.version@/win" (
	if not "%JAVA_HOME%" == "" (
		set JAVA_HOME=
	)

	set "JRE_HOME=%CATALINA_HOME%/jre@java.version@/win"
)

set "CATALINA_OPTS=%CATALINA_OPTS% -Dfile.encoding=UTF8 -Djava.net.preferIPv4Stack=true -Dorg.apache.catalina.loader.WebappClassLoader.ENABLE_CLEAR_REFERENCES=false -Duser.timezone=GMT -XX:NewSize=256m -XX:MaxNewSize=256m -XX:SurvivorRatio=7 -Xmx1280m -Xms1280m -XX:MetaspaceSize=384m -XX:MaxMetaspaceSize=384m"