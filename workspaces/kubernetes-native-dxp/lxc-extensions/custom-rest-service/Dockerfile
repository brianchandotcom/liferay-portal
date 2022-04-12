# maven build
FROM maven:3.8.5-jdk-11-slim AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/

RUN mvn package

# run spring-boot app
FROM adoptopenjdk/openjdk11:jre-11.0.14.1_1

ENV TINI_VERSION v0.19.0
ADD https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini /tini
RUN chmod +x /tini

COPY --from=MAVEN_BUILD /build/target/*.jar app.jar

ENTRYPOINT ["/tini", "--", "java", "-jar", "/app.jar"]