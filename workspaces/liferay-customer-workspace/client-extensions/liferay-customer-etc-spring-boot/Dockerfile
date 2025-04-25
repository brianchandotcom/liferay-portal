FROM liferay/jar-runner:latest

COPY --chown=liferay:liferay *.jar /opt/liferay/jar-runner.jar

ENV JAVA_VERSION=zulu21
ENV LIFERAY_JAR_RUNNER_JAVA_OPTS="\
	--add-opens java.base/java.lang=ALL-UNNAMED \
	--add-opens java.base/java.lang.invoke=ALL-UNNAMED \
	--add-opens java.base/java.lang.reflect=ALL-UNNAMED \
	--add-opens java.base/java.lang.net=ALL-UNNAMED \
	--add-opens java.base/java.net=ALL-UNNAMED \
	--add-opens java.base/sun.net.www.protocol.http=ALL-UNNAMED \
	--add-opens java.base/sun.net.www.protocol.https=ALL-UNNAMED \
	--add-opens java.base/sun.util.calendar=ALL-UNNAMED \
	--add-opens jdk.zipfs/jdk.nio.zipfs=ALL-UNNAMED"