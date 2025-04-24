FROM liferay/jar-runner:latest

COPY --chown=liferay:liferay *.jar /opt/liferay/jar-runner.jar

ENV JAVA_VERSION=zulu21
ENV LIFERAY_JAR_RUNNER_JAVA_OPTS="--add-opens java.base/java.net=ALL-UNNAMED"