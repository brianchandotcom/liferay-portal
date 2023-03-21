# How to execute Cron Job locally

1. Go to root of workspace and run `./gradlew :client-extensions:liferay-sample-oauth-application-headless-server:deploy`

1. Once DXP registers the client-extension, go to `Control Panel > OAuth2 Administration`

1. Select the `liferay-sample-oauth-application-headless-server` OAuth2 Application

1. Copy the `client_secret` value to clipboard

1. Go to `client-extensions/liferay-sample-oauth-application-headless-server` directory and run
   `gradle -Pliferay-sample-oauth-application-headless-server.oauth2.headless.server.client.secret=<paste_value_of_client_secret> bootRun`