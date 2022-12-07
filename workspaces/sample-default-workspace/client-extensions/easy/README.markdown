# Object Action Configuration Example

1. Deploy all client extensions:

    `./gradlew deploy`

1. Use blade to start Liferay:

    (Might need to run `blade server init` if not already done.)

    `blade server start`

1. Wait for Liferay to start.

1. Launch the application with the following command from the `client-extensions/easy` directory:

    ```bash
    cd client-extensions/easy
    ./gradlew \
        -Ddxp.domains=localhost:8080 \
        bootRun
    ```