# How to test the sample locally

1. Start DXP
1. Deploy `liferay-sample-etc-cron` client extension zip
`./gradlew :client-extensions:liferay-sample-etc-cron:deploy`
1. Login to DXP and go to Control Panel > Configuration > OAuth 2 Administration > Select `Liferay Sample Etc Cron`.
1. Copy the value of client secret.
1. Edit the file `client-extensions/liferay-sample-etc-cron/src/main/resources/application-default.properties`
1. Replace secret value the copied secret `liferay-sample-etc-cron.oauth2.headless.server.client.secret=myfancypassword`
1. Execute the spring boot application `cd client-extensions/liferay-sample-etc-cron && ./gradlew bootRun`