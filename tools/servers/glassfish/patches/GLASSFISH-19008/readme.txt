JIRA:

	http://issues.liferay.com/browse/LPS-28486
	http://issues.liferay.com/browse/LPS-28809
	http://java.net/jira/browse/GLASSFISH-19008

Source:

	http://repo1.maven.org/maven2/org/glassfish/main/web/web-core/3.1.2/web-core-3.1.2-sources.jar

Fix:

	ApplicationDispatcher.java [#1036 - #1055]

	/jdk6/bin/javac -g -cp web-core.jar;javax.servlet-api.jar ApplicationDispatcher.java
