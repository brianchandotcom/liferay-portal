Use Spring Boot and OAuth (server to server) to read from and write to Liferay in timed intervals.

To see this in action on your local machine:

1. Open `liferay-partner-workspace/gradle.properties` and add `liferay.workspace.home.dir=/home/me/dev/bundles/master`.

2. Go to `liferay-partner-workspace` and type `gw :client-extensions:liferay-partner-cron:deployDev`.

3. Login to Liferay and go to Control Panel > Configuration > OAuth 2 Administration. Select `Liferay Partner Cron`.

4. Copy the client secret. Open `liferay-partner-workspace/client-extensions/liferay-partner-cron/src/main/resources/application-default.properties` and replace `liferay-partner-cron.oauth2.headless.server.client.secret=myfancypassword` with the real client secret.

5. Go to `liferay-partner-workspace/client-extensions/liferay-partner-cron` and type `gw bootRun` to start Spring Boot.
