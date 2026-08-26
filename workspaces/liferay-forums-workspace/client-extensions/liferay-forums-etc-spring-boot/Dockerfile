FROM liferay/jar-runner:latest

USER liferay

COPY --chown=liferay:liferay *.jar /opt/liferay/jar-runner.jar

ENV LIFERAY_JAR_RUNNER_JAVA_OPTS="-XX:MaxRAMPercentage=50.0"