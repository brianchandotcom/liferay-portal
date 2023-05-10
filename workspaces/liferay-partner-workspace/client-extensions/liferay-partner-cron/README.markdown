Use Spring Boot and OAuth (server to server) to read from and write to Liferay in timed intervals.

To see this in action on your local machine:

1. Go to `liferay-partner-workspace` and type `./gradlew startDockerContainer logsDockerContainer` to start Liferay.

1. Go to `liferay-partner-workspace` and type `./gradlew :client-extensions:liferay-partner-cron:deploy`.

1. Login to Liferay and go to Control Panel > Configuration > OAuth 2 Administration. Select `Liferay Partner Cron`.

1. Copy the client secret. Open `liferay-partner-workspace/client-extensions/liferay-partner-cron/src/main/resources/application-default.properties` and replace `liferay-partner-cron.oauth2.headless.server.client.secret=myfancypassword` with the real client secret.

1. Go to `liferay-partner-workspace/client-extensions/liferay-partner-cron` and type `./gradlew bootRun` to start Spring Boot.
