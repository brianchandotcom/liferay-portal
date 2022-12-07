FROM adoptopenjdk/openjdk11:jre-11.0.14.1_1

ENV DEBUG_PORT=""

RUN apt-get update && \
	apt-get install -y tini && \
	rm -rf /var/lib/apt/lists/*

COPY *.jar app.jar

RUN echo "java -Xmx256m -agentlib:jdwp=transport=dt_socket,address=*:${DEBUG_PORT:-8001},server=y,suspend=n -jar /app.jar" > /app.sh && chmod +x /app.sh

ENTRYPOINT ["/usr/bin/tini", "--", "/app.sh"]