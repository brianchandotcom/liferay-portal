FROM adoptopenjdk/openjdk11:jre-11.0.14.1_1

ENV TINI_VERSION v0.19.0
ADD https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini /tini
RUN chmod +x /tini

COPY target/*.jar app.jar

ENTRYPOINT ["/tini", "--", "java", "-jar", "/app.jar"]