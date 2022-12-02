# Object Action Configuration Example

1. Deploy all client extensions:

    `./gradlew deploy`

1. Use blade to start Liferay:

    (Might need to run `blade server init` if not already done.)

    `blade server start`

1. Wait for Liferay to start.

1. Run the java app in the `client-extensions/able-oauth-application-user-agent/extra` directory:

    ```bash
    cd client-extensions/able-oauth-application-user-agent/extra
    ./gradlew run
    ```

1. Copy the generated file `<workspace>/configs/osgi/configs/*.config` to the `osgi/configs` directory of Liferay

1. In the Liferay logs find and copy the `clientId` of the `able-oauth-application-user-agent` client extension.

    e.g.

    ```bash
    OAuth2Application {externalReferenceCode: able-object-action-oauth-application, clientId: id-c2224747-79ae-e877-1bb8-802a1efda4ef}
    ```

1. Use the Client ID in the following command executed from the `client-extensions/able-object-action/extra`
directory:

    ```bash
    cd client-extensions/able-object-action/extra
    ./gradlew \
        -Ddxp.domains=localhost \
        -Doauth.client.id=<CLIENT_ID> \
        -Doauth.jwks.uri=http://localhost:8080/o/oauth2/jwks \
        bootRun
    ```